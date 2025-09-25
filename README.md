# Student Management System — Android App

A modern Android client for managing students, teachers, and school metadata (categories, sections, classes, subjects, etc.). The app supports three primary roles:

* **Admin** – manage users and “user & exam details” (foreign languages, sections, categories, groups, sub-groups, user types, student types, class numbers/letters, subjects).
* **Teacher** – view profile, subjects, panels for student info/evaluation (stubs ready).
* **Student** – view profile, chat/exams panels (stubs ready).

The UI is built around a **DrawerLayout + NavigationView** pattern with a shared header, lean layouts, and dynamic sizing utilities so a single layout scales across devices (with a few size-qualified overrides where helpful).

---

## Table of contents

* [Tech stack](#tech-stack)
* [Project structure](#project-structure)
* [Features](#features)
* [Networking & Auth](#networking--auth)
* [🔐 Authentication on Android (access + rotating refresh)](#-authentication-on-android-access--rotating-refresh)
* [UI & Theming](#ui--theming)
* [Build & Run](#build--run)
* [Configuration](#configuration)
* [Notable components](#notable-components)
* [Layouts & Responsiveness](#layouts--responsiveness)
* [Testing & Debugging](#testing--debugging)
* [License](#license)
* [Screenshots](#screenshots)

---

## Tech stack

**Language:** Java 17
**SDK:** `compileSdk=35`, `targetSdk=35`, `minSdk=25`
**Gradle:** Android Gradle Plugin **8.6.0**, Gradle **8.7**

**Libraries**

* Retrofit **2.9.0** + Gson converter
* OkHttp (BOM **4.12.0**)
* Material Components (**1.12.0**) & AppCompat (**1.6.1**)
* ConstraintLayout (**2.1.4**), Navigation (**2.4.1**)
* JWT: `jjwt-api/impl/orgjson` **0.12.3**
* AndroidX Security Crypto (`EncryptedSharedPreferences`) **1.1.0-alpha06**
* AES Crypt (scottyab) **0.0.1** (extra at-rest encryption in specific utilities)
* Flexbox Layout **3.0.0** (filters bar)
* CircleImageView **3.1.0**

---

## Project structure

```
app/src/main/java/com/example/studentmanagementsystemandroidapp
│
├─ activities/
│  ├─ auth_activities/ (LoginActivity, EntryActivity launcher)
│  ├─ admin_activities/
│  │  ├─ admins/ (AdminPanelActivity, BaseAdminActivity)
│  │  └─ userandexamdetails/
│  │      (UserAndExamDetailsActivity + *Activity per entity:
│  │       ForeignLanguages, Sections, Categories, Groups, SubGroups,
│  │       UserTypes, StudentTypes, ClassNumbers, ClassLetters, Subjects)
│  └─ user_activities/
│     ├─ student_activities/ (BaseStudentActivity, Panel/Profile/Chat/Exams)
│     └─ teacher_activites/  (BaseTeacherActivity, Panel/Profile/Chat/StudentInfo/Evaluation)
│
├─ apis/ … (AuthApi, StudentApi, TeacherSelfApi, etc.)
├─ auth/ (TokenStore)
├─ managers/ (AuthManager, LogoutManager, SessionManager, StatusManager, *SelfManager)
├─ models/ (admins, users, userandexamdetails)
├─ network/ (RetrofitInstance, AuthInterceptor, TokenAuthenticator)
├─ requests/ & responses/ (LoginRequest/Response, RefreshRequest, entity requests)
└─ utils/ (ViewStyler, ScreenUtils, SpinnerUtils, ValidationUtils, LoadingOverlayUtils, etc.)
```

**Resources**

* `layout/` — single-source layouts with a few **size-qualified** variants (e.g., `sw720dp-port` / `land`)
* `drawable/` — gradients, selectors, borders, custom checkbox, icons, button backgrounds
* `values/` — `colors.xml`, `dimens.xml` (+land), `styles.xml`, `themes.xml`, `strings.xml`
* `menu/`, `mipmap/` — app menus & launcher icons
* `navigation/` — if/when fragments are used

---

## Features

* **Secure authentication**

  * JWT login with **access + refresh** tokens
  * Automatic token refresh on `401` via `TokenAuthenticator`
  * Central `SessionManager` with a single “session expired” toast + redirect logic
* **Role-based surfaces**

  * **Admin**: management views for all reference data + users (students/teachers)
  * **Student**: profile + panels for chat/exams (screens scaffolded)
  * **Teacher**: profile + panels for student info/evaluation (screens scaffolded)
* **Rich form & list UX**

  * Dynamic form building via reusable request layouts
  * Custom spinners with an **“Add…”** inline special item
  * Multi-select subjects dialog with scrollable content and sticky footer
  * Filter bar built with **FlexboxLayout**
* **Responsive UI**

  * Shared base layouts + **runtime sizing** utilities (`ViewStyler`, `ScreenUtils`)
  * Optional size-qualified resources for tablets/landscape
* **UX polish**

  * Styled error messages, loading overlays (activity-wide or container-only)
  * Drawer header with avatar + user info
  * Consistent brand colors & button selectors

---

## Networking & Auth

* **Base URL** lives in `RetrofitInstance.BASE_URL`

  * Example (device on LAN): `http://192.168.x.x:8080/`
  * Emulator host: `http://10.0.2.2:8080/`
* **Cleartext enabled for dev** (`android:usesCleartextTraffic="true"`). Prefer **HTTPS** in production.
* **Timeouts**: 300s call/connect/read/write (dev-friendly; tune for prod)
* `AuthInterceptor` adds `Authorization` if an access token exists.
* `TokenAuthenticator` runs only on **401**, calls `/auth/refresh`, saves rotated tokens, retries the original request **once**. Skips `/auth/*` to avoid recursion.

---

## 🔐 Authentication on Android (access + rotating refresh)

This app uses a **short-lived access token (AT)** and a **rotating refresh token (RT)**. The backend stores refresh tokens **hashed** and issues a **new pair** on each refresh. The Android client is built to be resilient, race-safe, and user-friendly.

### Key classes (client)

* **`TokenStore`** — secure persistence using `EncryptedSharedPreferences` (AES-GCM). *Migrates* from a legacy plaintext file on first run and then deletes it.
* **`AuthInterceptor`** — adds `Authorization` to every request if an AT exists.
* **`TokenAuthenticator`** — OkHttp `Authenticator` that runs **only on 401**: calls `/auth/refresh`, saves the new pair, and **retries once** with the new AT. Skips `/auth/*` to avoid loops. Thread-safe via a `lock`.
* **`SessionManager`** — emits a **single** “session expired” broadcast per cascade (debounced), so the user doesn’t get spammed if several calls fail at once.
* **`RetrofitInstance`** — wires the interceptor + authenticator; exposes `reset()` to recreate the client after login/logout.
* **`EntryActivity`** — decides the start destination by checking the presence of a **refresh token** and the stored role/userType.
* **`LogoutManager`** — clears tokens + caches, resets Retrofit, and navigates to Login with a friendly toast.

### Request lifecycle

```mermaid
sequenceDiagram
    participant UI as Any screen/manager
    participant R as Retrofit/OkHttp
    participant I as AuthInterceptor
    participant A as TokenAuthenticator
    participant API as Backend
    participant TS as TokenStore
    participant SM as SessionManager

    UI->>R: Call protected endpoint
    I->>R: Attach Authorization: Bearer <AT> (if present)
    R->>API: Request (e.g., GET /admin/…)
    API-->>R: 200 OK OR 401 Unauthorized

    alt 401 Unauthorized
        opt Request is /auth/*
            A-->>R: Skip (no auth)  ➜ give up
        end

        A->>A: synchronized(lock) // prevent refresh storms
        alt Another thread already refreshed
            A->>TS: Read latest AT
            A->>R: Retry original request (once) with latest AT
            API-->>R: 200 OK
        else Need to refresh
            A->>API: POST /auth/refresh { refreshToken }
            API-->>A: 200 { new AT, new RT } OR 4xx/5xx
            alt Refresh success
                A->>TS: saveTokens(newAT, newRT)
                A->>R: Retry original request (once) with new AT
                API-->>R: 200 OK
            else Refresh failed/expired
                A->>TS: clear()
                A->>SM: notifySessionExpiredOnce()
                A-->>R: Give up (no retry)
            end
        end

        note over R: Hard cap: if responseCount ≥ 2 → stop to avoid loops
    end

    R-->>UI: Deliver final response
```

### Rotation & race safety

* Refresh **rotates** the RT: old RT becomes invalid immediately.
* If another thread **already refreshed**, the authenticator simply **retries with the newer AT** (no extra refresh call).
* A hard cap (`responseCount >= 2`) prevents infinite retry loops.

### What happens on failure

* If refresh fails (expired RT, network error, malformed token, etc.):

  * `TokenStore.clear()` → removes all persisted auth.
  * `SessionManager.notifySessionExpiredOnce()` → the app shows **one** “Session expired. Please log in again.” toast and returns to `LoginActivity`.
  * Managers/screens can listen to the broadcast if they need to close themselves gracefully.

### Storage details

* `TokenStore` writes AT/RT/role/userId/username/userType to **`AuthPrefs.secure`** (encrypted).
* On first run after upgrade, it **migrates** values from the old plaintext file (`AuthPrefs`) and then **deletes** it.
* Defensive clear: if anything goes wrong with encrypted prefs, the store deletes the file to avoid inconsistent state.

### Login & boot

* **Login** (`AuthManager.login`) → on success:

  * Save `{ AT, RT, subjectId, role, userType }`
  * `SessionManager.resetFlag()` (re-enable future session-expired toasts)
  * `RetrofitInstance.reset(context)` to rebuild OkHttp with fresh tokens
  * Route to Admin/Teacher/Student panel based on role/userType
* **App launch** (`EntryActivity`) → if an RT exists, skip login and route by role; otherwise go to `LoginActivity`.

### Logout

Use `LogoutManager.logout(activity)`:

* Clears tokens and profile caches (`StudentSelfManager`, `TeacherSelfManager`)
* Resets session debounce + Retrofit
* Navigates to Login with a “You’ve been signed out.” toast

### Testing the flow (quick manual script)

1. Log in and hit a protected endpoint → expect **200**.
2. Let the **access token expire** (short TTL) and call again → a **single** refresh, then **200** on retry.
3. Let the **refresh token expire** (longer TTL) and call again → redirected to **Login** with **one** session-expired toast.
4. Fire **multiple requests** right after AT expiry → only **one** refresh should happen; others retry with the **new AT**.
5. Calls to **`/auth/*`** never trigger the authenticator (no recursion).

### Configuration & gotchas

* **Header format**: backend returns the AT **already prefixed** with `"Bearer "`. The app stores & reuses it as-is. If this changes, update `AuthInterceptor`.
* **BASE\_URL**

  * Emulator → `http://10.0.2.2:8080/`
  * Device on same Wi-Fi → `http://<your-laptop-LAN-IP>:8080/`
* **Cleartext**: allowed for dev. Use **HTTPS** in production and remove `usesCleartextTraffic`.
* **Timeouts**: 300s call/connect/read/write (dev-friendly).
* **Debounce**: if you see multiple toasts on expiry, ensure `SessionManager.resetFlag()` is called after successful login (already done in `AuthManager`).

### Relevant files

* `network/AuthInterceptor.java`
* `network/TokenAuthenticator.java`
* `network/RetrofitInstance.java`
* `auth/TokenStore.java`
* `managers/session/SessionManager.java`
* `activities/auth_activities/LoginActivity.java`
* `activities/EntryActivity.java`
* `managers/auth/LogoutManager.java`
* `apis/auth_apis/AuthApi.java`

---

## UI & Theming

* **Theme:** `Theme.StudentManagementSystemAndroidApp` (Material3, NoActionBar)
* **Action bar look:** `CustomActionBarTheme` on most activities
* **Brand palette** (`colors.xml`)

  * `sms_blue` `#072b66`, `sms_blue_darker` `#01245c`, `sms_blue_pressed` `#0a3a8a`
  * `sms_yellow` `#e6c805`, gradient (`sms_yellow_start` → `sms_yellow_end`)
* **Shared styles**

  * `AppButtonStyle`, `CategoryButtonStyle` (selector-backed)
  * `ErrorMessageTextStyle`, `NavHeaderTextStyle`, `DigitEditText`
* **Notable drawables**

  * Button selectors, gradients, borders, custom checkbox

---

## Build & Run

1. **Open** the project in Android Studio (JDK 17).
2. **Backend URL**: set `RetrofitInstance.BASE_URL` to your server.

   * Emulator: `http://10.0.2.2:8080/`
   * Device on LAN: `http://<your-laptop-LAN-IP>:8080/`
3. **Run** the `app` configuration on device/emulator.

**Requirements**

* Android Studio (AGP 8.6+), JDK 17
* Android SDK 35
* Backend reachable from device/emulator

---

## Configuration

* **Cleartext (HTTP)**: enabled for local dev (`usesCleartextTraffic="true"`). Use **HTTPS** in production.
* **Tokens**: handled by `TokenStore` (EncryptedSharedPreferences).
* **ProGuard/R8**: default + `proguard-rules.pro` scaffold (add Retrofit/OkHttp/Gson/JJWT rules if you enable minify).

---

## Notable components

### Models & requests

* `models/users`: `User`, `Student`, `Teacher`, `UserStatus`, `StudentCommunicationSenderStatus`, `TeacherCommunicationSenderStatus`
* `models/userandexamdetails`: `Category`, `Section`, `Group`, `SubGroup`, `StudentType`, `ClassNumber`, `ClassLetter`, `Subject`, `ForeignLanguage`, `UserType`
* `requests/*`: `UserRequest`, `StudentRequest`, `FullStudentRequest`, etc.
* `responses/auth`: `LoginResponse`, `ApiError`

### Utilities (selected)

* **View & sizing:** `ViewStyler`, `ScreenUtils`, `ButtonUtils`
* **Forms & spinners:** `SpinnerUtils`, `SubjectsDisplayUtils`
* **Validation:** `ValidationUtils` (name/surname, single space/letter, mobile, 7-digit student code), `EmailValidator`
* **UX:** `LoadingOverlayUtils`, `KeyboardUtils`, `NavigationUtils`
* **Sorting:** `ClassNumberAndLetterSorter`, `SortUtils`

---

## Layouts & Responsiveness

* **Single base layout** + runtime sizing (percentages of **screen width/height**) for:

  * Buttons (`ViewStyler.setButtonSize`)
  * Text (`ViewStyler.setTextSizeByScreenHeight`)
  * Spinners and inputs
* **Examples**

  * `common_entity_show_add_layout.xml` — split view (existing items vs. add form), dynamic content in `FrameLayout`
  * `layout_student_filter.xml` — **FlexboxLayout** with many filter spinners + persistent “Hide Filter View” toggle and reset button
  * `layout_view_and_update_user_item.xml` — 3-column row (label / value / update)
  * `multiselect_dropdown_item.xml` & `dialog_update_subject.xml` — subject multi-select UX
* Size-qualified variants (`sw720dp`, `land`) for better tablet/landscape ergonomics where needed.

---

## Testing & Debugging

* **Auth**

  * Confirm `Authorization: Bearer <jwt>` is present on protected endpoints.
  * Simulate AT expiry → ensure a **single** refresh and retry succeed.
  * Simulate RT expiry → single session-expired toast + redirect to Login.
* **Networking**

  * Emulator + local backend → use `10.0.2.2`.
  * Verify device/emulator can reach the host (same Wi-Fi/subnet).
* **UI**

  * Toggle validation to see `custom_error_message.xml` styling.
  * Check loading overlays (activity-wide vs. in-layout).

---

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/ahmadyardimli/student-management-system-android-app/blob/main/LICENSE)
This project is licensed under the **MIT License** — see the [LICENSE](https://github.com/ahmadyardimli/student-management-system-android-app/blob/main/LICENSE) file for details.

---

## Screenshots

<p align="center">
<img src="docs/screenshots/5364020472162940492.jpg" alt="5364020472162940492" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940493.jpg" alt="5364020472162940493" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940494.jpg" alt="5364020472162940494" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940495.jpg" alt="5364020472162940495" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940496.jpg" alt="5364020472162940496" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940497.jpg" alt="5364020472162940497" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940498.jpg" alt="5364020472162940498" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940499.jpg" alt="5364020472162940499" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940500.jpg" alt="5364020472162940500" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940501.jpg" alt="5364020472162940501" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940502.jpg" alt="5364020472162940502" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940503.jpg" alt="5364020472162940503" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940504.jpg" alt="5364020472162940504" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940505.jpg" alt="5364020472162940505" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940506.jpg" alt="5364020472162940506" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940507.jpg" alt="5364020472162940507" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940508.jpg" alt="5364020472162940508" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940509.jpg" alt="5364020472162940509" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940510.jpg" alt="5364020472162940510" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940511.jpg" alt="5364020472162940511" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940512.jpg" alt="5364020472162940512" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940513.jpg" alt="5364020472162940513" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940514.jpg" alt="5364020472162940514" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940515.jpg" alt="5364020472162940515" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940516.jpg" alt="5364020472162940516" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940517.jpg" alt="5364020472162940517" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940518.jpg" alt="5364020472162940518" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940519.jpg" alt="5364020472162940519" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940520.jpg" alt="5364020472162940520" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940521.jpg" alt="5364020472162940521" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940522.jpg" alt="5364020472162940522" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940523.jpg" alt="5364020472162940523" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940524.jpg" alt="5364020472162940524" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940525.jpg" alt="5364020472162940525" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940526.jpg" alt="5364020472162940526" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940527.jpg" alt="5364020472162940527" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940528.jpg" alt="5364020472162940528" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940529.jpg" alt="5364020472162940529" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940530.jpg" alt="5364020472162940530" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940531.jpg" alt="5364020472162940531" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940532.jpg" alt="5364020472162940532" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940533.jpg" alt="5364020472162940533" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940534.jpg" alt="5364020472162940534" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940535.jpg" alt="5364020472162940535" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940536.jpg" alt="5364020472162940536" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940537.jpg" alt="5364020472162940537" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940538.jpg" alt="5364020472162940538" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940539.jpg" alt="5364020472162940539" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940540.jpg" alt="5364020472162940540" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940541.jpg" alt="5364020472162940541" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940542.jpg" alt="5364020472162940542" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940543.jpg" alt="5364020472162940543" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940544.jpg" alt="5364020472162940544" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940545.jpg" alt="5364020472162940545" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940546.jpg" alt="5364020472162940546" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940547.jpg" alt="5364020472162940547" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940548.jpg" alt="5364020472162940548" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940549.jpg" alt="5364020472162940549" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940550.jpg" alt="5364020472162940550" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940551.jpg" alt="5364020472162940551" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940552.jpg" alt="5364020472162940552" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940553.jpg" alt="5364020472162940553" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940554.jpg" alt="5364020472162940554" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940555.jpg" alt="5364020472162940555" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940556.jpg" alt="5364020472162940556" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940558.jpg" alt="5364020472162940558" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940559.jpg" alt="5364020472162940559" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940560.jpg" alt="5364020472162940560" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940561.jpg" alt="5364020472162940561" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940562.jpg" alt="5364020472162940562" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940563.jpg" alt="5364020472162940563" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940564.jpg" alt="5364020472162940564" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940565.jpg" alt="5364020472162940565" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940566.jpg" alt="5364020472162940566" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940567.jpg" alt="5364020472162940567" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940568.jpg" alt="5364020472162940568" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940569.jpg" alt="5364020472162940569" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940570.jpg" alt="5364020472162940570" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940571.jpg" alt="5364020472162940571" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940572.jpg" alt="5364020472162940572" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940573.jpg" alt="5364020472162940573" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940574.jpg" alt="5364020472162940574" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940575.jpg" alt="5364020472162940575" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940576.jpg" alt="5364020472162940576" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940577.jpg" alt="5364020472162940577" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940578.jpg" alt="5364020472162940578" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940579.jpg" alt="5364020472162940579" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940580.jpg" alt="5364020472162940580" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940581.jpg" alt="5364020472162940581" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940582.jpg" alt="5364020472162940582" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940583.jpg" alt="5364020472162940583" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940584.jpg" alt="5364020472162940584" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940585.jpg" alt="5364020472162940585" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940586.jpg" alt="5364020472162940586" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940587.jpg" alt="5364020472162940587" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940588.jpg" alt="5364020472162940588" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940589.jpg" alt="5364020472162940589" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940590.jpg" alt="5364020472162940590" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940591.jpg" alt="5364020472162940591" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940592.jpg" alt="5364020472162940592" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940593.jpg" alt="5364020472162940593" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940594.jpg" alt="5364020472162940594" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940595.jpg" alt="5364020472162940595" width="260" style="margin:6px;" />
<img src="docs/screenshots/5364020472162940596.jpg" alt="5364020472162940596" width="260" style="margin:6px;" />
</p>




