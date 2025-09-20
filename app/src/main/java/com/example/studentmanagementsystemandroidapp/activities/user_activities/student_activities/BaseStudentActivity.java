package com.example.studentmanagementsystemandroidapp.activities.user_activities.student_activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.studentmanagementsystemandroidapp.activities.auth_activities.LoginActivity;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;
import com.example.studentmanagementsystemandroidapp.managers.actionbar.ActionBarManager;
import com.example.studentmanagementsystemandroidapp.managers.auth.LogoutManager;
import com.example.studentmanagementsystemandroidapp.managers.navigation.NavMenuItemClickListener;
import com.example.studentmanagementsystemandroidapp.managers.navigation.NavMenuManager;
import com.example.studentmanagementsystemandroidapp.managers.navigation.NavigationManager;
import com.example.studentmanagementsystemandroidapp.managers.session.SessionManager;
import com.example.studentmanagementsystemandroidapp.managers.users.students.StudentSelfManager;
import com.example.studentmanagementsystemandroidapp.models.users.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseStudentActivity extends AppCompatActivity implements NavMenuItemClickListener {
    protected static final String MENU_STUDENT_PAGE = "Main student page";
    protected static final String MENU_PROFILE = "My profile";
    protected static final String MENU_EXAMS = "Exams";
    protected static final String MENU_CHAT = "Chat";
    private ActionBarManager actionBarManager;
    private NavigationManager navigationManager;
    private NavMenuManager navMenuManager;

    private final BroadcastReceiver sessionReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            if (SessionManager.ACTION_SESSION_EXPIRED.equals(intent.getAction())) {
                // Clear local auth + in-memory cache if you want (optional)
                try { new TokenStore(getApplicationContext()).clear(); } catch (Throwable ignored) {}
                StudentSelfManager.clearCache();

                Intent login = new Intent(BaseStudentActivity.this, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                login.putExtra(LoginActivity.EXTRA_SHOW_SESSION_TOAST, true); // <- tell LoginActivity to show the message
                startActivity(login);
            }
        }
    };

    // We can call this method in onCreate of child activities after setContentView
    protected void setupNavigationAndActionBar(String title, Activity activity, String... menuItems) {
        navigationManager = new NavigationManager(activity);
        navMenuManager    = new NavMenuManager(activity);
        actionBarManager  = new ActionBarManager(this);

        navMenuManager.setOnMenuItemClickListener(this);

        navigationManager.initNavigationViewWidth(0.8f);
        navigationManager.initNavigationViewHeaderHeight(0.2f);

        actionBarManager.setUpActionBarTitle(title);

        // Adding menu items in the requested order (Main student page first)
        for (String menuItemTitle : menuItems) {
            Class<?> target = getMenuActivityClass(menuItemTitle);
            navMenuManager.addMenuItem(menuItemTitle, target);
        }

        navMenuManager.clearDefaultMenuItemTitles();
        loadStudentHeaderInfo();
        navMenuManager.addActionItem("Log out", () -> LogoutManager.logout(BaseStudentActivity.this));
    }

    private Class<?> getMenuActivityClass(String menuItemTitle) {
        switch (menuItemTitle) {
            case MENU_STUDENT_PAGE: return StudentPanelActivity.class;
            case MENU_PROFILE:      return StudentProfileActivity.class;
            case MENU_EXAMS:        return StudentExamsActivity.class;
            case MENU_CHAT:         return StudentChatActivity.class;
            default:                return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationManager != null
                && navigationManager.getNavigationToggle().onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    private void navigateToNextActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    @Override
    public void onMenuItemClick(String title) {
        switch (title) {
            case MENU_STUDENT_PAGE: navigateToNextActivity(StudentPanelActivity.class);
                break;
            case MENU_PROFILE: navigateToNextActivity(StudentProfileActivity.class); break;
            case MENU_EXAMS: navigateToNextActivity(StudentExamsActivity.class); break;
            case MENU_CHAT: navigateToNextActivity(StudentChatActivity.class); break;
            default: break;
        }
    }

    @Override protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(sessionReceiver, new IntentFilter(SessionManager.ACTION_SESSION_EXPIRED));
    }

    @Override protected void onStop() {
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(sessionReceiver);
        super.onStop();
    }

    // first we catch, then refresh if needed
    private void loadStudentHeaderInfo() {
        if (navigationManager == null) return;

        StudentSelfManager mgr = new StudentSelfManager(this);

        // this makes catche first for instant header
        Student cached = mgr.peekCache();
        if (cached != null) {
            applyStudentToHeader(cached);
        } else {
            // fallback username from token store
            String cachedUsername = mgr.getCachedUsername();
            if (cachedUsername != null && !cachedUsername.isEmpty()) {
                navigationManager.setHeaderUserInfo("failed", cachedUsername, "failed");
            }
        }

        // ensure up-to-date
        mgr.loadCurrentStudent(new Callback<Student>() {
            @Override public void onResponse(Call<Student> call, Response<Student> response) {
                if (!response.isSuccessful() || response.body() == null) return;
                applyStudentToHeader(response.body());
            }
            @Override public void onFailure(Call<Student> call, Throwable t) {  }
        });
    }

    private void applyStudentToHeader(Student s) {
        String first = s.getName() == null ? "" : s.getName().trim();
        String last = s.getSurname() == null ? "" : s.getSurname().trim();
        String full = (first + " " + last).trim();

        if (full.isEmpty() && s.getUser() != null && s.getUser().getUsername() != null) {
            full = s.getUser().getUsername();
        }

        String username = (s.getUser() != null && s.getUser().getUsername() != null)
                ? s.getUser().getUsername() : "";
        String email = (s.getUser() != null && s.getUser().getEmail() != null)
                ? s.getUser().getEmail() : "";

        navigationManager.setHeaderUserInfo(full, username, email);
    }

    // This is reusable method for pages that need current student data
    protected void fetchCurrentStudent(Callback<Student> cb) {
        new StudentSelfManager(this).loadCurrentStudent(cb);
    }
}
