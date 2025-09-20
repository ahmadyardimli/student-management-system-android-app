package com.example.studentmanagementsystemandroidapp.activities.user_activities.student_activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.models.users.Student;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProfileActivity extends BaseStudentActivity {
    private TextView tvTitle;
    private TextView tvFullName;
    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvClass;
    private TextView tvStudentCode;
    private TextView tvStatus;

    private LoadingOverlayUtils loadingOverlayUtils;
    // used for full-screen overlay
    private ViewGroup rootContainer;
    // The card container that should be hidden while loading
    private ViewGroup contentContainer;

    // Placeholders
    private static final String OPTIONAL_PLACEHOLDER = "Does not exist"; // for optional field (email)
    private static final String REQUIRED_ERROR       = "Failed to load"; // for required field missing

    // Tracks whether any required field ended up missing after a successful fetch
    private boolean hadMissingRequired;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        setupNavigationAndActionBar(
                "Student profile",
                this,
                MENU_STUDENT_PAGE, MENU_PROFILE, MENU_EXAMS, MENU_CHAT
        );

        rootContainer    = (ViewGroup) findViewById(android.R.id.content);
        contentContainer = findViewById(R.id.contentContainer);
        loadingOverlayUtils = new LoadingOverlayUtils(this);

        tvTitle       = findViewById(R.id.tvTitle);
        tvFullName    = findViewById(R.id.tvFullName);
        tvUsername    = findViewById(R.id.tvUsername);
        tvEmail       = findViewById(R.id.tvEmail);
        tvClass       = findViewById(R.id.tvClass);
        tvStudentCode = findViewById(R.id.tvStudentCode);
        tvStatus      = findViewById(R.id.tvStatus);

        if (tvTitle != null)       ViewStyler.setTextSizeByScreenHeight(tvTitle, this, 0.030);
        if (tvFullName != null)    ViewStyler.setTextSizeByScreenHeight(tvFullName, this, 0.022);
        if (tvUsername != null)    ViewStyler.setTextSizeByScreenHeight(tvUsername, this, 0.022);
        if (tvEmail != null)       ViewStyler.setTextSizeByScreenHeight(tvEmail, this, 0.022);
        if (tvClass != null)       ViewStyler.setTextSizeByScreenHeight(tvClass, this, 0.022);
        if (tvStudentCode != null) ViewStyler.setTextSizeByScreenHeight(tvStudentCode, this, 0.022);
        if (tvStatus != null)      ViewStyler.setTextSizeByScreenHeight(tvStatus, this, 0.022);

        // Show only a full-screen spinner; hide the card while loading
        startLoading();

        // Fetch data and then bind
        fetchCurrentStudent(new Callback<Student>() {
            @Override public void onResponse(Call<Student> call, Response<Student> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    showFailedPlaceholders();
                    stopLoading(); // reveal the card with "Failed to load" texts
                    Toast.makeText(StudentProfileActivity.this, R.string.generic_load_failed, Toast.LENGTH_SHORT).show();
                    return;
                }
                bindStudent(response.body());
                stopLoading(); // reveal the card with real data
            }

            @Override public void onFailure(Call<Student> call, Throwable t) {
                showFailedPlaceholders();
                stopLoading(); // reveal the card with "Failed to load" texts
                Toast.makeText(StudentProfileActivity.this, R.string.generic_network_error, Toast.LENGTH_SHORT).show();
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

    private void bindStudent(Student student) {
        hadMissingRequired = false;

        // Show the student’s full name. If that isn’t available, use the username. If neither is available, show “Failed to load.”
        String first = isBlank(student.getName()) ? "" : student.getName().trim();
        String last  = isBlank(student.getSurname()) ? "" : student.getSurname().trim();
        String full  = (first + " " + last).trim();

        if (isBlank(full) && student.getUser() != null) {
            full = student.getUser().getUsername();
        }
        full = required(full);

        // Username (required)
        String username = (student.getUser() != null)
                ? required(student.getUser().getUsername())
                : required(null);

        // Email (optional)
        String email = (student.getUser() != null)
                ? optional(student.getUser().getEmail())
                : OPTIONAL_PLACEHOLDER;

        // Class (required): class number must be present and > 0; letter is optional
        String classText;
        if (student.getClassNumber() == null) {
            classText = requiredIf(false, "");
        } else {
            int numVal = student.getClassNumber().getNumberValue();
            String letter = (student.getClassLetter() != null && !isBlank(student.getClassLetter().getLetterValue()))
                    ? student.getClassLetter().getLetterValue().trim()
                    : "";
            classText = requiredIf(numVal > 0, String.valueOf(numVal) + letter);
        }

        // Student code (required)
        String studentCode = required(student.getStudentCode());

        // Status (required)
        String status = (student.getUser() != null && student.getUser().getStatus() != null)
                ? required(student.getUser().getStatus().getStatusText())
                : required(null);

        setSafeText(tvFullName,"Full name: " + full);
        setSafeText(tvUsername,"Username: "+ username);
        setSafeText(tvEmail,"Email: " + email);
        setSafeText(tvClass,"Class: " + classText);
        setSafeText(tvStudentCode,"Student code: " + studentCode);
        setSafeText(tvStatus,"Status: " + status);

        if (hadMissingRequired) {
            Toast.makeText(this, "Some profile fields failed to load.", Toast.LENGTH_SHORT).show();
        }
    }

    // If we can’t fetch the profile, show Failed to load in every field on the card.
    private void showFailedPlaceholders() {
        setSafeText(tvFullName,    "Full name: "    + REQUIRED_ERROR);
        setSafeText(tvUsername,    "Username: "     + REQUIRED_ERROR);
        setSafeText(tvEmail,       "Email: "        + REQUIRED_ERROR);
        setSafeText(tvClass,       "Class: "        + REQUIRED_ERROR);
        setSafeText(tvStudentCode, "Student code: " + REQUIRED_ERROR);
        setSafeText(tvStatus,      "Status: "       + REQUIRED_ERROR);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // optional fields
    private String optional(String s) {
        return isBlank(s) ? OPTIONAL_PLACEHOLDER : s.trim();
    }

    // For required fields. Marks error if blank.
    private String required(String s) {
        if (isBlank(s)) {
            hadMissingRequired = true;
            return REQUIRED_ERROR;
        }
        return s.trim();
    }

    // For computed required values; pass true when valid.
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
