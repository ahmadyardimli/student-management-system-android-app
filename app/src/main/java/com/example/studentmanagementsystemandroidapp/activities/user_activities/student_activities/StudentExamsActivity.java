package com.example.studentmanagementsystemandroidapp.activities.user_activities.student_activities;
import android.os.Bundle;
import android.widget.TextView;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

public class StudentExamsActivity extends BaseStudentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_exams);

        setupNavigationAndActionBar(
                "Exams",
                this,
                MENU_STUDENT_PAGE, MENU_PROFILE, MENU_EXAMS, MENU_CHAT
        );

        TextView tv = findViewById(R.id.tvMessage);
        if (tv != null) {
            tv.setText("Exams…");
            ViewStyler.setTextSizeByScreenHeight(tv, this, 0.03f);
        }
    }
}
