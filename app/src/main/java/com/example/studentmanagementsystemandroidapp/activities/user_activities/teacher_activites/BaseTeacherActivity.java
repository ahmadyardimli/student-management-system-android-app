package com.example.studentmanagementsystemandroidapp.activities.user_activities.teacher_activites;

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
import com.example.studentmanagementsystemandroidapp.managers.users.teachers.TeacherSelfManager;
import com.example.studentmanagementsystemandroidapp.models.users.Teacher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseTeacherActivity extends AppCompatActivity implements NavMenuItemClickListener {
    protected static final String MENU_TEACHER_PAGE = "Main teacher page";
    protected static final String MENU_PROFILE = "My profile";
    protected static final String MENU_STUDENT_INFO = "Student info";
    protected static final String MENU_STUDENT_EVAL = "Student evaluation";
    protected static final String MENU_CHAT = "Chat";
    private ActionBarManager actionBarManager;
    private NavigationManager navigationManager;
    private NavMenuManager navMenuManager;

    private final BroadcastReceiver sessionReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            if (SessionManager.ACTION_SESSION_EXPIRED.equals(intent.getAction())) {
                try { new TokenStore(getApplicationContext()).clear(); } catch (Throwable ignored) {}
                TeacherSelfManager.clearCache();

                Intent login = new Intent(BaseTeacherActivity.this, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                login.putExtra(LoginActivity.EXTRA_SHOW_SESSION_TOAST, true);
                startActivity(login);
            }
        }
    };


    // Call this in onCreate of child activities
    protected void setupNavigationAndActionBar(String title, Activity activity, String... menuItems) {
        navigationManager = new NavigationManager(activity);
        navMenuManager = new NavMenuManager(activity);
        actionBarManager = new ActionBarManager(this);

        navMenuManager.setOnMenuItemClickListener(this);

        navigationManager.initNavigationViewWidth(0.8f);
        navigationManager.initNavigationViewHeaderHeight(0.2f);

        actionBarManager.setUpActionBarTitle(title);

        for (String menuItemTitle : menuItems) {
            Class<?> target = getMenuActivityClass(menuItemTitle);
            navMenuManager.addMenuItem(menuItemTitle, target);
        }

        navMenuManager.clearDefaultMenuItemTitles();
        loadTeacherHeaderInfo();
        navMenuManager.addActionItem("Log out", () -> LogoutManager.logout(BaseTeacherActivity.this));
    }

    private Class<?> getMenuActivityClass(String menuItemTitle) {
        switch (menuItemTitle) {
            case MENU_TEACHER_PAGE: return TeacherPanelActivity.class;
            case MENU_STUDENT_INFO:    return TeacherStudentInfoActivity.class;
            case MENU_STUDENT_EVAL:    return TeacherStudentEvaluationActivity.class;
            case MENU_CHAT:            return TeacherChatActivity.class;
            case MENU_PROFILE:         return TeacherProfileActivity.class;
            default:                   return null;
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
            case MENU_TEACHER_PAGE: navigateToNextActivity(TeacherPanelActivity.class); break;
            case MENU_STUDENT_INFO:    navigateToNextActivity(TeacherStudentInfoActivity.class); break;
            case MENU_STUDENT_EVAL:    navigateToNextActivity(TeacherStudentEvaluationActivity.class); break;
            case MENU_CHAT:            navigateToNextActivity(TeacherChatActivity.class); break;
            case MENU_PROFILE:         navigateToNextActivity(TeacherProfileActivity.class); break;
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
    private void loadTeacherHeaderInfo() {
        if (navigationManager == null) return;

        TeacherSelfManager mgr = new TeacherSelfManager(this);

        // fill from cache if available
        Teacher cached = mgr.peekCache();
        if (cached != null) {
            applyTeacherToHeader(cached);
        } else {
            // fallback to stored username
            String cachedUsername = mgr.getCachedUsername();
            if (cachedUsername != null && !cachedUsername.isEmpty()) {
                navigationManager.setHeaderUserInfo("failed", cachedUsername, "failed");
            }
        }

        // ensure up-to-date
        mgr.loadCurrentTeacher(new Callback<Teacher>() {
            @Override public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if (!response.isSuccessful() || response.body() == null) return;
                applyTeacherToHeader(response.body());
            }
            @Override public void onFailure(Call<Teacher> call, Throwable t) {  }
        });
    }

    private void applyTeacherToHeader(Teacher t) {
        String first = t.getName() == null ? "" : t.getName().trim();
        String last = t.getSurname() == null ? "" : t.getSurname().trim();
        String full = (first + " " + last).trim();

        if (full.isEmpty() && t.getUser() != null && t.getUser().getUsername() != null) {
            full = t.getUser().getUsername();
        }

        String username = (t.getUser() != null && t.getUser().getUsername() != null)
                ? t.getUser().getUsername() : "failed";
        String email = (t.getUser() != null && t.getUser().getEmail() != null)
                ? t.getUser().getEmail() : "does not exist";

        navigationManager.setHeaderUserInfo(full, username, email);
    }

    // Reusable method for pages that need current teacher data
    protected void fetchCurrentTeacher(Callback<Teacher> cb) {
        new TeacherSelfManager(this).loadCurrentTeacher(cb);
    }
}