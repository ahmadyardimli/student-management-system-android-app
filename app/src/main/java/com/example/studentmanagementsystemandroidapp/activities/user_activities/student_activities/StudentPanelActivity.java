package com.example.studentmanagementsystemandroidapp.activities.user_activities.student_activities;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.managers.users.students.StudentSelfManager;
import com.example.studentmanagementsystemandroidapp.models.users.Student;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentPanelActivity extends BaseStudentActivity {
    private TextView tvMessage;
    private LoadingOverlayUtils loadingOverlayUtils;
    private ViewGroup contentRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);

        setupNavigationAndActionBar("Student", this,
                MENU_STUDENT_PAGE, MENU_PROFILE, MENU_EXAMS, MENU_CHAT);

        contentRoot = findViewById(R.id.content_root);
        loadingOverlayUtils = new LoadingOverlayUtils(this);

        tvMessage = findViewById(R.id.tvMessage);
        ViewStyler.setTextSizeByScreenHeight(tvMessage, this, 0.03f);

        loadingOverlayUtils.showLayoutOverlay(contentRoot);

        fetchCurrentStudent(new Callback<Student>() {
            @Override public void onResponse(Call<Student> call, Response<Student> response) {
                loadingOverlayUtils.hideLayoutOverlay();

                if (!response.isSuccessful() || response.body() == null) {
                    showLoginFallbackMessage();
                    return;
                }
                showWelcome(response.body());
            }

            @Override public void onFailure(Call<Student> call, Throwable t) {
                loadingOverlayUtils.hideLayoutOverlay();
                showLoginFallbackMessage();
                Toast.makeText(StudentPanelActivity.this,
                        getString(R.string.generic_network_error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWelcome(Student s) {
        String first = s.getName() == null ? "" : s.getName().trim();
        String last = s.getSurname() == null ? "" : s.getSurname().trim();
        String full = (first + " " + last).trim();

        if (full.isEmpty() && s.getUser() != null && s.getUser().getUsername() != null) {
            full = s.getUser().getUsername().trim();
        }
        if (full.isEmpty()) full = "Unknown";

        tvMessage.setText(full + " logged in securely");
    }

    // this is my fallback method when profile fetch fails: show cached username if have it, otherwise "Unknown".
    private void showLoginFallbackMessage() {
        StudentSelfManager mgr = new StudentSelfManager(this);
        String cachedUsername = mgr.getCachedUsername();
        String display = (cachedUsername != null && !cachedUsername.trim().isEmpty())
                ? cachedUsername.trim()
                : "Unknown";

        tvMessage.setText(display + " logged in securely — but we couldn’t load your profile.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingOverlayUtils != null) {
            loadingOverlayUtils.hideLayoutOverlay();
        }
    }
}
