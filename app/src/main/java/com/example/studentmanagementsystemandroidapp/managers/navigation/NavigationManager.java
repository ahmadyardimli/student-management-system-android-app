package com.example.studentmanagementsystemandroidapp.managers.navigation;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.utils.ScreenUtils;
import com.google.android.material.navigation.NavigationView;

public class NavigationManager {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ConstraintLayout navHeaderBackground;
    private ImageView profileIcon;

    // header fields
    private TextView tvUserName;     // full name
    private TextView tvUserUsername; // username
    private TextView tvUserEmail;    // email

    public NavigationManager(Activity activity){
        this.drawer = activity.findViewById(R.id.drawer_layout);
        this.navigationView = activity.findViewById(R.id.nav_view);
        if (navigationView == null) {
            Log.e("NavigationManager", "NavigationView is null");
            return;
        }

        View headerView = navigationView.getHeaderView(0);
        if (headerView == null) {
            Log.e("NavigationManager", "HeaderView is null (ensure app:headerLayout on NavigationView)");
            return;
        }

        this.navHeaderBackground = headerView.findViewById(R.id.nav_header);
        this.profileIcon = headerView.findViewById(R.id.profile_image);

        // text fields (IDs must exist in nav_header.xml)
        this.tvUserName     = headerView.findViewById(R.id.user_name);
        this.tvUserUsername = headerView.findViewById(R.id.user_username);
        this.tvUserEmail    = headerView.findViewById(R.id.user_email);

        setupDrawerToggle(activity);
    }

    public void initNavigationViewWidth(float percent) {
        if (navigationView == null) return;
        int navViewWidth = ScreenUtils.calculateWidthWithPercentage(navigationView.getContext(), percent);
        ViewGroup.LayoutParams params = navigationView.getLayoutParams();
        params.width = navViewWidth;
        navigationView.setLayoutParams(params);
    }

    public void initNavigationViewHeaderHeight(float percent) {
        if (navigationView == null || navHeaderBackground == null || profileIcon == null) return;

        int navHeaderBackgroundHeight = ScreenUtils
                .calculateHeightWithPercentage(navigationView.getContext(), percent);
        ViewGroup.LayoutParams params = navHeaderBackground.getLayoutParams();
        params.height = navHeaderBackgroundHeight;
        navHeaderBackground.setLayoutParams(params);

        int profileImageWidth = ScreenUtils.calculateWidthWithPercentage(navigationView.getContext(), 0.2);
        int profileImageHeight = ScreenUtils.calculateHeightWithPercentage(navigationView.getContext(), 0.2);

        ViewGroup.LayoutParams profileImageParams = profileIcon.getLayoutParams();
        profileImageParams.width = profileImageWidth;
        profileImageParams.height = profileImageHeight;
        profileIcon.setLayoutParams(profileImageParams);
    }

    public ActionBarDrawerToggle getNavigationToggle(){
        return toggle;
    }

    private void setupDrawerToggle(Activity activity) {
        toggle = new ActionBarDrawerToggle(
                activity,
                drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    public void setHeaderUserInfo(String fullName, String username, String email) {
        if (tvUserName != null) {
            tvUserName.setText(labeled("Full name", fullName));
        }
        if (tvUserUsername != null) {
            tvUserUsername.setText(labeled("Username", username));
        }
        if (tvUserEmail != null) {
            tvUserEmail.setText(labeled("Email", email));
        }
    }

    private String labeled(String label, String value) {
        String v = (value == null) ? "" : value.trim();
        if (v.isEmpty()) v = "does not exist";
        return label + ": " + v;
    }

}