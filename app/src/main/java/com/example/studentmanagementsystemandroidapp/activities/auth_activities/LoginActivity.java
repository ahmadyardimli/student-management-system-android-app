package com.example.studentmanagementsystemandroidapp.activities.auth_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.UserActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.AdminPanelActivity;
import com.example.studentmanagementsystemandroidapp.activities.user_activities.student_activities.StudentPanelActivity;
import com.example.studentmanagementsystemandroidapp.managers.auth.AuthManager;
import com.example.studentmanagementsystemandroidapp.responses.auth.LoginResponse;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ImageView eyeIcon;
    private View bottomTintUsername;
    private View bottomTintPassword;
    private View errorMessageUsername;
    private View errorMessagePassword;
    private boolean usernameErrorVisible;
    private boolean passwordErrorVisible;
    private boolean isPasswordVisible = false;

    // Loading overlay + in-flight guard
    private LoadingOverlayUtils loadingOverlayUtils;
    private boolean isLoginInProgress = false;
    public static final String EXTRA_SHOW_SESSION_TOAST = "SHOW_SESSION_TOAST";
    public static final String EXTRA_SHOW_SIGNED_OUT_TOAST = "SHOW_SIGNED_OUT_TOAST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setFocusChangeListeners();
        setOnClickListeners();

        loadingOverlayUtils = new LoadingOverlayUtils(this);
    }

    private void initViews() {
        usernameEditText = findViewById(R.id.usernameFieldLayout).findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordFieldLayout).findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        eyeIcon = findViewById(R.id.passwordFieldLayout).findViewById(R.id.eyeIcon);
        bottomTintUsername = findViewById(R.id.usernameFieldLayout).findViewById(R.id.bottomTint);
        bottomTintPassword = findViewById(R.id.passwordFieldLayout).findViewById(R.id.bottomTint);
        errorMessageUsername = findViewById(R.id.errorMessageUsername);
        errorMessagePassword = findViewById(R.id.errorMessagePassword);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("usernameErrorVisible", usernameErrorVisible);
        outState.putBoolean("passwordErrorVisible", passwordErrorVisible);
        outState.putInt("passwordCursorPosition", passwordEditText.getSelectionStart());

        if (passwordErrorVisible){
            TextView errorMessageTextViewPassword = errorMessagePassword.findViewById(R.id.errorMessageTextView);
            outState.putString("passwordErrorMessage", errorMessageTextViewPassword.getText().toString());
        }
        if (usernameErrorVisible){
            TextView errorMessageTextViewUsername = errorMessageUsername.findViewById(R.id.errorMessageTextView);
            outState.putString("usernameErrorMessage", errorMessageTextViewUsername.getText().toString());
        }

        outState.putBoolean("isPasswordVisible", isPasswordVisible);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            usernameErrorVisible = savedInstanceState.getBoolean("usernameErrorVisible");
            passwordErrorVisible = savedInstanceState.getBoolean("passwordErrorVisible");
            isPasswordVisible = savedInstanceState.getBoolean("isPasswordVisible");

            if (usernameErrorVisible) {
                String usernameErrorMessage = savedInstanceState.getString("usernameErrorMessage");
                showErrorMessage(errorMessageUsername, usernameErrorMessage);
            }

            if (passwordErrorVisible) {
                String passwordErrorMessage = savedInstanceState.getString("passwordErrorMessage");
                showErrorMessage(errorMessagePassword, passwordErrorMessage);
            }

            int cursorPosition = savedInstanceState.getInt("passwordCursorPosition");
            passwordEditText.setSelection(cursorPosition);

            updateEyeIconVisibility();
        }
    }

    private void updateEyeIconVisibility() {
        int cursorPosition = passwordEditText.getSelectionStart();
        if (isPasswordVisible) {
            passwordEditText.setTransformationMethod(null);
            eyeIcon.setImageResource(R.drawable.eye_icon_active);
        } else {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            eyeIcon.setImageResource(R.drawable.eye_icon_disabled);
        }
        passwordEditText.setSelection(cursorPosition);
    }

    private void validateUsername() {
        String username = usernameEditText.getText().toString();
        if (username.isEmpty()) {
            showErrorMessage(errorMessageUsername, getString(R.string.enter_username_error));
            usernameErrorVisible = true;
        } else {
            hideErrorMessage(errorMessageUsername);
            usernameErrorVisible = false;
        }
    }

    private void validatePassword() {
        String password = passwordEditText.getText().toString();
        if (password.isEmpty()) {
            showErrorMessage(errorMessagePassword,  getString(R.string.enter_password_error));
            passwordErrorVisible = true;
        } else {
            hideErrorMessage(errorMessagePassword);
            passwordErrorVisible = false;
        }
    }

    private void setFocusChangeListeners() {
        setFocusChangeListener(usernameEditText, bottomTintUsername, bottomTintPassword, R.color.sms_yellow, R.color.sms_blue_darker);
        setFocusChangeListener(passwordEditText, bottomTintPassword, bottomTintUsername, R.color.sms_yellow, R.color.sms_blue_darker);
    }

    private void setOnClickListeners() {
        eyeIcon.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            updateEyeIconVisibility();
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty() && usernameEditText.hasFocus()) {
                    showErrorMessage(errorMessageUsername,  getString(R.string.enter_username_error));
                    usernameErrorVisible = true;
                } else {
                    hideErrorMessage(errorMessageUsername);
                    usernameErrorVisible = false;
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty() && passwordEditText.hasFocus()) {
                    showErrorMessage(errorMessagePassword, getString(R.string.enter_password_error));
                    passwordErrorVisible = true;
                } else if (s.toString().isEmpty() && !passwordEditText.hasFocus() && passwordErrorVisible){
                    showErrorMessage(errorMessagePassword, getString(R.string.enter_password_error));
                    passwordErrorVisible = true;
                } else {
                    hideErrorMessage(errorMessagePassword);
                    passwordErrorVisible = false;
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        View rootView = findViewById(android.R.id.content);
        rootView.setOnClickListener(v -> hideKeyboard());

        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void navigateToNextActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    private void showErrorMessage(View errorView, String message) {
        TextView errorMessageTextView = errorView.findViewById(R.id.errorMessageTextView);
        errorMessageTextView.setText(message);
        errorView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage(View errorView) {
        errorView.setVisibility(View.GONE);
    }

    private void attemptLogin() {
        if (isLoginInProgress) return; // guard against double taps

        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        validateUsername();
        validatePassword();

        if (usernameErrorVisible || passwordErrorVisible) return;

        // show full-screen loading overlay and block touches
        loadingOverlayUtils.showActivityOverlay(this, true);
        isLoginInProgress = true;
        loginButton.setEnabled(false);
        hideKeyboard();

        AuthManager authManager = new AuthManager(LoginActivity.this);
        authManager.login(username, password, new AuthManager.AuthCallback() {
            @Override
            public void onLoginSuccess(String role, String userType) {
                // hide overlay *before* navigating to avoid window leaks
                loadingOverlayUtils.hideActivityOverlay();
                isLoginInProgress = false;
                loginButton.setEnabled(true);

                String r = role == null ? "" : role.trim();

                if ("ROLE_ADMIN".equals(r) || r.endsWith("ADMIN")) {
                    Intent i = new Intent(LoginActivity.this, AdminPanelActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    return;
                }

                if ("ROLE_USER".equals(r) || r.endsWith("USER")) {
                    String ut = userType == null ? "" : userType.trim();

                    if ("Teacher".equalsIgnoreCase(ut)) {
                        Intent i = new Intent(
                                LoginActivity.this,
                                com.example.studentmanagementsystemandroidapp.activities.user_activities.teacher_activites.TeacherPanelActivity.class
                        );
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        return;
                    } else if ("Student".equalsIgnoreCase(ut)) {
                        Intent i = new Intent(
                                LoginActivity.this,
                                StudentPanelActivity.class
                        );
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        return;
                    }

                    Intent i = new Intent(LoginActivity.this, UserActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    return;
                }

                Intent i = new Intent(LoginActivity.this, UserActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

            @Override
            public void onLoginFailure(Call<LoginResponse> call, Throwable t) {
                loadingOverlayUtils.hideActivityOverlay();
                isLoginInProgress = false;
                loginButton.setEnabled(true);

                if (t.getMessage() != null) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.generic_network_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setFocusChangeListener(final EditText editText, final View tintView1, final View tintView2, final int color1, final int color2) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                tintView1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, color1));
                tintView2.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, color2));
            } else {
                tintView1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, color2));
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra(EXTRA_SHOW_SESSION_TOAST, false)) {
            Toast.makeText(this, getString(R.string.session_expired_message), Toast.LENGTH_LONG).show();
            getIntent().removeExtra(EXTRA_SHOW_SESSION_TOAST);
        }
        if (getIntent().getBooleanExtra(EXTRA_SHOW_SIGNED_OUT_TOAST, false)) {
            Toast.makeText(this, getString(R.string.signed_out_message), Toast.LENGTH_LONG).show();
            getIntent().removeExtra(EXTRA_SHOW_SIGNED_OUT_TOAST);
        }
    }

    @Override
    protected void onDestroy() {
        if (loadingOverlayUtils != null) {
            loadingOverlayUtils.hideActivityOverlay();
            loadingOverlayUtils.hideLayoutOverlay();
        }
        super.onDestroy();
    }
}