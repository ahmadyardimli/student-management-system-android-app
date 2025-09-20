package com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.auth_activities.LoginActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.UserAndExamDetailsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.MainUserActivity;
import com.example.studentmanagementsystemandroidapp.managers.actionbar.ActionBarManager;
import com.example.studentmanagementsystemandroidapp.managers.admins.AdminManager;
import com.example.studentmanagementsystemandroidapp.managers.auth.LogoutManager;
import com.example.studentmanagementsystemandroidapp.managers.navigation.NavMenuItemClickListener;
import com.example.studentmanagementsystemandroidapp.managers.navigation.NavMenuManager;
import com.example.studentmanagementsystemandroidapp.managers.navigation.NavigationManager;
import com.example.studentmanagementsystemandroidapp.managers.session.SessionManager;
import com.example.studentmanagementsystemandroidapp.models.admins.Admin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseAdminActivity extends AppCompatActivity implements NavMenuItemClickListener {
    private ActionBarManager actionBarManager;
    private NavigationManager navigationManager;
    private NavMenuManager navMenuManager;

    private final BroadcastReceiver sessionReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            if (SessionManager.ACTION_SESSION_EXPIRED.equals(intent.getAction())) {
                Intent login = new Intent(BaseAdminActivity.this, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                login.putExtra(LoginActivity.EXTRA_SHOW_SESSION_TOAST, true);
                startActivity(login);
            }
        }
    };


    protected void setupNavigationAndActionBar(String actionBarTitle, Activity activity, String... menuItems) {
        // Initialize NavigationView for child activity
        navigationManager = new NavigationManager(activity);
        navMenuManager = new NavMenuManager(activity);
        actionBarManager = new ActionBarManager(this);

        // Set the listener for menu item clicks for child activity
        navMenuManager.setOnMenuItemClickListener(this);

        // Initialize Navigation Drawer and its components
        navigationManager.initNavigationViewWidth(0.8f);
        navigationManager.initNavigationViewHeaderHeight(0.2f);

        // Set up action bar
        actionBarManager.setUpActionBarTitle(actionBarTitle);

        for (String menuItemTitle : menuItems){
            Class<?> menuItemActivity = getMenuActivityClass(menuItemTitle);
            if (menuItemActivity != null){
                Log.d("BASE's MENU ITEM ACTIVITY ", menuItemActivity.toString());
            }
            navMenuManager.addMenuItem(menuItemTitle, menuItemActivity);
        }

        navMenuManager.clearDefaultMenuItemTitles();
        // fill the nav header with real admin info
        loadAdminHeaderInfo();
        navMenuManager.addActionItem("Log out", () -> LogoutManager.logout(BaseAdminActivity.this));
    }

    private Class<?> getMenuActivityClass(String menuItemTitle) {
        switch (menuItemTitle) {
            case "Admins":
                return AdminPanelActivity.class;
            case "Users":
                return MainUserActivity.class;
            case "User Details":
                return UserAndExamDetailsActivity.class;
            default:
                return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navigationManager != null && navigationManager.getNavigationToggle().onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void navigateToNextActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public void onMenuItemClick(String title) {
        switch (title) {
            case "Admins":
                navigateToNextActivity(AdminPanelActivity.class);
                break;
            case "Users":
                navigateToNextActivity(MainUserActivity.class);
                break;
            case "User Details":
                navigateToNextActivity(UserAndExamDetailsActivity.class);
                break;
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

    private void loadAdminHeaderInfo() {
        if (navigationManager == null) return;

        AdminManager adminManager = new AdminManager(this);

        // placeholder cached username from token
        String cachedUsername = adminManager.getCachedUsername();
        if (cachedUsername != null && !cachedUsername.isEmpty()) {
            navigationManager.setHeaderUserInfo("failed", cachedUsername, "failed");
        }

        // Fetch current admin by id from backend and update header
        adminManager.loadCurrentAdmin(new Callback<Admin>() {
            @Override public void onResponse(Call<Admin> call, Response<Admin> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("BaseAdminActivity", "Failed to load admin: " + response.code());
                    return;
                }
                Admin admin = response.body();

                String firstName = admin.getFirstName() == null ? "" : admin.getFirstName().trim();
                String lastName = admin.getLastName() == null ? "" : admin.getLastName().trim();
                String fullName = (firstName + " " + lastName).trim();
                if (fullName.isEmpty()) fullName = admin.getUsername();

                navigationManager.setHeaderUserInfo(fullName, admin.getUsername(), admin.getEmail());
            }

            @Override public void onFailure(Call<Admin> call, Throwable t) {
                Log.e("BaseAdminActivity", "Admin load error: " + (t.getMessage() == null ? "unknown" : t.getMessage()));
            }
        });
    }
}