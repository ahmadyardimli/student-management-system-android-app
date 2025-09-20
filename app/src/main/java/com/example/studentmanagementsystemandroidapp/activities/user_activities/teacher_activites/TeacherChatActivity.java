package com.example.studentmanagementsystemandroidapp.activities.user_activities.teacher_activites;

import android.os.Bundle;
import android.widget.TextView;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

public class TeacherChatActivity extends BaseTeacherActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_chat);

        setupNavigationAndActionBar(
                "Chat",
                this,
                MENU_TEACHER_PAGE, MENU_PROFILE, MENU_STUDENT_INFO, MENU_STUDENT_EVAL, MENU_CHAT
        );

        TextView tvMessage = findViewById(R.id.tvMessage);
        // Dynamic text size
        ViewStyler.setTextSizeByScreenHeight(tvMessage, this, 0.03f);
    }
}
