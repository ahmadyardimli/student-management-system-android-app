package com.example.studentmanagementsystemandroidapp.activities.user_activities.teacher_activites;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.managers.users.students.StudentSelfManager; // used to read cached username from token
import com.example.studentmanagementsystemandroidapp.models.users.Teacher;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherPanelActivity extends BaseTeacherActivity {
    private TextView tvMessage;
    private LoadingOverlayUtils loadingOverlayUtils;
    private ViewGroup contentRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_panel);

        // Navigation + header
        setupNavigationAndActionBar("Teacher", this,
                MENU_TEACHER_PAGE, MENU_PROFILE, MENU_STUDENT_INFO, MENU_STUDENT_EVAL, MENU_CHAT);

        // Content root (overlay won’t cover the drawer)
        contentRoot = findViewById(R.id.content_root);
        loadingOverlayUtils = new LoadingOverlayUtils(this);

        tvMessage = findViewById(R.id.tvMessage);
        ViewStyler.setTextSizeByScreenHeight(tvMessage, this, 0.03f);

        // Show content-only loading overlay
        loadingOverlayUtils.showLayoutOverlay(contentRoot);

        // Fetch current teacher (cache-first inside BaseTeacherActivity’s manager)
        fetchCurrentTeacher(new Callback<Teacher>() {
            @Override public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                loadingOverlayUtils.hideLayoutOverlay();

                if (!response.isSuccessful() || response.body() == null) {
                    showLoginFallbackMessage();
                    return;
                }
                showWelcome(response.body());
            }

            @Override public void onFailure(Call<Teacher> call, Throwable t) {
                loadingOverlayUtils.hideLayoutOverlay();
                showLoginFallbackMessage();
                Toast.makeText(TeacherPanelActivity.this,
                        getString(R.string.generic_network_error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWelcome(Teacher t) {
        String first = t.getName() == null ? "" : t.getName().trim();
        String last  = t.getSurname() == null ? "" : t.getSurname().trim();
        String full  = (first + " " + last).trim();

        if (full.isEmpty() && t.getUser() != null && t.getUser().getUsername() != null) {
            full = t.getUser().getUsername().trim();
        }
        if (full.isEmpty()) full = "Unknown";

        tvMessage.setText(full + " logged in securely");
    }

    // when profile fetch fails: show cached username if we have it, otherwise "Unknown".
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