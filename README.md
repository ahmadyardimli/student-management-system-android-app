# student-management-system-android-app

Production-minded Android client (Java **17**) for Student Management — **Retrofit/OkHttp**, **JWT (access + rotating refresh)**, secure token storage via **EncryptedSharedPreferences**, Material design, and responsive layouts.

---

> ### Docs live on the `development` branch
>
> I keep detailed, evolving documentation on the **development** branch while keeping `main` clean and stable.
>
> • 👉 **Full README:** [Android README (development)](https://github.com/ahmadyardimli/student-management-system-android-app/blob/development/README.md)
> 
> • **Browse branch:** [development branch](https://github.com/ahmadyardimli/student-management-system-android-app/tree/development)
> 
> • **Screens gallery:** [`docs/SCREENSHOTS.md` (development)](https://github.com/ahmadyardimli/student-management-system-android-app/blob/development/docs/SCREENSHOTS.md)

---

## Highlights

* **Secure auth**: short-lived **access token** + **rotating refresh token** (resilient refresh with OkHttp `Authenticator`)
* **Safe storage**: tokens in **EncryptedSharedPreferences** with legacy-migration & defensive clears
* **Clean networking**: Retrofit 2.9, OkHttp 4.12 BOM, Gson converter, single `RetrofitInstance`
* **Role-aware UI**: Admin / Teacher / Student entry points via `EntryActivity` (role + userType)
* **Responsive layouts**: base layouts + dynamic sizing, with a few size-qualified overrides
* **Polish**: loading overlays, error styling, custom spinners, multi-select dialogs

---

## Quick start (local)

1. **Open** the project in Android Studio (JDK **17**).
2. **Point to your backend**: edit `network/RetrofitInstance.java` → `BASE_URL`

   * Emulator: `http://10.0.2.2:8080/`
   * Device on same Wi-Fi: `http://<your-laptop-LAN-IP>:8080/`
3. **Run** the `app` configuration on an emulator or device.

> Cleartext (`usesCleartextTraffic="true"`) is enabled for dev. For production, switch to **HTTPS** and remove cleartext.

---

## Production snapshot

* **Auth flow**: `AuthInterceptor` adds `Authorization: Bearer <access>`; on **401**, `TokenAuthenticator` calls `/auth/refresh`, **rotates tokens**, and retries **once**. On failure, a **single** session-expired event is emitted and the user is routed to Login.
* **Storage**: `TokenStore` (EncryptedSharedPreferences) keeps `{ accessToken, refreshToken, subjectId, role, userType }`.

Full details, diagrams, and edge cases are documented on the **development** README (link above).

---

## Branching

I develop on **`development`** and merge to **`main`** for stable releases.

```bash
git switch development
git switch -c feature/some-change
git push -u origin feature/some-change  # PR: feature → development → main
```

---

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.
