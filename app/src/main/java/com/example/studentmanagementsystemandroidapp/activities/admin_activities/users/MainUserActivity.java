package com.example.studentmanagementsystemandroidapp.activities.admin_activities.users;

import android.os.Bundle;
import android.widget.Button;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.BaseAdminActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.students.StudentsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.teachers.TeachersActivity;
import com.example.studentmanagementsystemandroidapp.utils.NavigationUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

public class MainUserActivity extends BaseAdminActivity {
    private Button btnTeachers;
    private Button btnStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        // Set up navigation and action bar
        setupNavigationAndActionBar("Users", this, "Admins", "Users", "User Details");

        btnTeachers = findViewById(R.id.btnTeachers);
        btnStudents = findViewById(R.id.btnStudents);

        ViewStyler.setButtonSize(btnTeachers,this, 0.6,0.07,  0.3);
        ViewStyler.setButtonSize(btnStudents, this, 0.6,0.07,  0.3);

        btnTeachers.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, TeachersActivity.class));
        btnStudents.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, StudentsActivity.class));
    }
}