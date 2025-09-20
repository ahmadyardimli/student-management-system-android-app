package com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins;
import android.os.Bundle;
import com.example.studentmanagementsystemandroidapp.R;

public class AdminPanelActivity extends BaseAdminActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        setupNavigationAndActionBar("Super Admin", this, "Admins", "Users", "User Details");
    }
}
