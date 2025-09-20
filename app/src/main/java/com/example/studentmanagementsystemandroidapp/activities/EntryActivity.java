package com.example.studentmanagementsystemandroidapp.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagementsystemandroidapp.activities.auth_activities.LoginActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.AdminPanelActivity;
import com.example.studentmanagementsystemandroidapp.activities.user_activities.student_activities.StudentPanelActivity;
import com.example.studentmanagementsystemandroidapp.activities.user_activities.teacher_activites.TeacherPanelActivity;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;

public class EntryActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TokenStore store = new TokenStore(this);
        String refresh = store.getRefreshToken();
        Intent next;

        if (refresh == null || refresh.isEmpty()) {
            next = new Intent(this, LoginActivity.class);
        } else {
            String role = store.getRole();
            String userType = store.getUserType();
            if ("ROLE_ADMIN".equals(role) || (role != null && role.endsWith("ADMIN"))) {
                next = new Intent(this, AdminPanelActivity.class);
            } else if ("ROLE_USER".equals(role) || (role != null && role.endsWith("USER"))) {
                if ("Teacher".equalsIgnoreCase(userType)) {
                    next = new Intent(this, TeacherPanelActivity.class);
                } else if ("Student".equalsIgnoreCase(userType)) {
                    next = new Intent(this, StudentPanelActivity.class);
                } else {
                    next = new Intent(this, UserActivity.class);
                }
            } else {
                next = new Intent(this, LoginActivity.class);
            }
        }

        next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(next);
        finish();
    }
}
