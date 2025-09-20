package com.example.studentmanagementsystemandroidapp.activities.admin_activities;

import android.os.Bundle;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.BaseAdminActivity;

public class AnotherActivity extends BaseAdminActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        setupNavigationAndActionBar("Another", this, "Admins", "Users", "User Details");
    }
}