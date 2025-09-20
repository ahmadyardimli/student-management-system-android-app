package com.example.studentmanagementsystemandroidapp.activities.user_activities.student_activities;

import android.os.Bundle;
import android.widget.TextView;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

public class StudentChatActivity extends BaseStudentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat);

        setupNavigationAndActionBar(
                "Chat",
                this,
                MENU_STUDENT_PAGE, MENU_PROFILE, MENU_EXAMS, MENU_CHAT
        );

        TextView tvMessage = findViewById(R.id.tvMessage);
        ViewStyler.setTextSizeByScreenHeight(tvMessage, this, 0.03f);
    }
}
