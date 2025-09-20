package com.example.studentmanagementsystemandroidapp.managers.actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActionBarManager {
    private AppCompatActivity activity;

    public ActionBarManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setUpActionBarTitle(String title) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
