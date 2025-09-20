package com.example.studentmanagementsystemandroidapp.activities.user_activities.teacher_activites;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.models.users.Teacher;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherProfileActivity extends BaseTeacherActivity {
    private TextView tvTitle;
    private TextView tvFullName;
    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvSubjects;
    private TextView tvStatus;

    private LoadingOverlayUtils loadingOverlayUtils;
    private ViewGroup rootContainer;
    private ViewGroup contentContainer;

    // Placeholders
    private static final String OPTIONAL_PLACEHOLDER = "Does not exist"; // optional field (email)
    private static final String REQUIRED_ERROR       = "Failed to load"; // required field missing

    // Tracks whether any required field ended up missing after a successful fetch
    private boolean hadMissingRequired;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        setupNavigationAndActionBar(
                "Teacher profile",
                this,
                MENU_TEACHER_PAGE, MENU_PROFILE, MENU_STUDENT_INFO, MENU_STUDENT_EVAL, MENU_CHAT
        );

        // Full-screen overlay root & content card
        rootContainer    = (ViewGroup) findViewById(android.R.id.content);
        contentContainer = findViewById(R.id.contentContainer);
        loadingOverlayUtils = new LoadingOverlayUtils(this);

        // Bind views
        tvTitle    = findViewById(R.id.tvTitle);
        tvFullName = findViewById(R.id.tvFullName);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail    = findViewById(R.id.tvEmail);
        tvSubjects = findViewById(R.id.tvSubjects);
        tvStatus   = findViewById(R.id.tvStatus);

        // Dynamic sizing
        if (tvTitle != null)    ViewStyler.setTextSizeByScreenHeight(tvTitle, this, 0.030);
        if (tvFullName != null) ViewStyler.setTextSizeByScreenHeight(tvFullName, this, 0.022);
        if (tvUsername != null) ViewStyler.setTextSizeByScreenHeight(tvUsername, this, 0.022);
        if (tvEmail != null)    ViewStyler.setTextSizeByScreenHeight(tvEmail, this, 0.022);
        if (tvSubjects != null) ViewStyler.setTextSizeByScreenHeight(tvSubjects, this, 0.022);
        if (tvStatus != null)   ViewStyler.setTextSizeByScreenHeight(tvStatus, this, 0.022);

        // Show only a full-screen spinner; hide the card while loading
        startLoading();

        // Fetch and bind
        fetchCurrentTeacher(new Callback<Teacher>() {
            @Override public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    showFailedPlaceholders();
                    stopLoading(); // reveal the card with "Failed to load" texts
                    Toast.makeText(TeacherProfileActivity.this, R.string.generic_load_failed, Toast.LENGTH_SHORT).show();
                    return;
                }
                bindTeacher(response.body());
                stopLoading(); // reveal the card with data
            }

            @Override public void onFailure(Call<Teacher> call, Throwable t) {
                showFailedPlaceholders();
                stopLoading(); // reveal the card with "Failed to load" texts
                Toast.makeText(TeacherProfileActivity.this, R.string.generic_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startLoading() {
        if (contentContainer != null) contentContainer.setVisibility(View.GONE);
        if (rootContainer != null) loadingOverlayUtils.showLayoutOverlay(rootContainer); // full-screen overlay
    }

    private void stopLoading() {
        loadingOverlayUtils.hideLayoutOverlay();
        if (contentContainer != null) contentContainer.setVisibility(View.VISIBLE);
    }

    private void bindTeacher(Teacher t) {
        hadMissingRequired = false;

        // Full name (required) → fallback to username (required)
        String first = isBlank(t.getName()) ? "" : t.getName().trim();
        String last  = isBlank(t.getSurname()) ? "" : t.getSurname().trim();
        String full  = (first + " " + last).trim();
        if (isBlank(full) && t.getUser() != null) {
            full = t.getUser().getUsername();
        }
        full = required(full);

        // Username (required)
        String username = (t.getUser() != null)
                ? required(t.getUser().getUsername())
                : required(null);

        // Email (optional)
        String email = (t.getUser() != null)
                ? optional(t.getUser().getEmail())
                : OPTIONAL_PLACEHOLDER;

        // Subjects (required): prefer names; fall back to count
        String subjectsText;
        if (t.getSubjectNames() != null && !t.getSubjectNames().isEmpty()) {
            subjectsText = android.text.TextUtils.join(", ", t.getSubjectNames());
        } else if (t.getSubject_ids() != null && !t.getSubject_ids().isEmpty()) {
            subjectsText = t.getSubject_ids().size() + " subject(s)";
        } else {
            subjectsText = requiredIf(false, "");
        }

        // Status (required)
        String status = (t.getUser() != null && t.getUser().getStatus() != null)
                ? required(t.getUser().getStatus().getStatusText())
                : required(null);

        setSafeText(tvFullName, "Full name: " + full);
        setSafeText(tvUsername, "Username: " + username);
        setSafeText(tvEmail,    "Email: " + email);
        setSafeText(tvSubjects, "Subjects: " + subjectsText);
        setSafeText(tvStatus,   "Status: " + status);

        if (hadMissingRequired) {
            Toast.makeText(this, "Some profile fields failed to load.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFailedPlaceholders() {
        setSafeText(tvFullName, "Full name: " + REQUIRED_ERROR);
        setSafeText(tvUsername, "Username: " + REQUIRED_ERROR);
        setSafeText(tvEmail,    "Email: " + REQUIRED_ERROR);
        setSafeText(tvSubjects, "Subjects: " + REQUIRED_ERROR);
        setSafeText(tvStatus,   "Status: " + REQUIRED_ERROR);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // for optional fields
    private String optional(String s) {
        return isBlank(s) ? OPTIONAL_PLACEHOLDER : s.trim();
    }

    // for required fields
    private String required(String s) {
        if (isBlank(s)) {
            hadMissingRequired = true;
            return REQUIRED_ERROR;
        }
        return s.trim();
    }

    // For computed required values; pass true when valid
    private String requiredIf(boolean ok, String valueWhenOk) {
        if (!ok) {
            hadMissingRequired = true;
            return REQUIRED_ERROR;
        }
        return valueWhenOk;
    }

    private void setSafeText(TextView tv, String text) {
        if (tv != null) tv.setText(text);
    }
}