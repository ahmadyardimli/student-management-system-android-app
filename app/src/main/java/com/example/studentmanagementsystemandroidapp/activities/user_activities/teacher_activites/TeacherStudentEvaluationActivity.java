package com.example.studentmanagementsystemandroidapp.activities.user_activities.teacher_activites;

import android.os.Bundle;
import android.widget.TextView;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

public class TeacherStudentEvaluationActivity extends BaseTeacherActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_evaluation);

        setupNavigationAndActionBar(
                "Student evaluation",
                this,
                MENU_TEACHER_PAGE, MENU_PROFILE, MENU_STUDENT_INFO, MENU_STUDENT_EVAL, MENU_CHAT
        );

        TextView tv = findViewById(R.id.tvMessage);
        if (tv != null) {
            tv.setText("Student evaluation...");
            ViewStyler.setTextSizeByScreenHeight(tv, this, 0.03f);
        }
    }
}
