package com.example.studentmanagementsystemandroidapp.managers.auth;

import android.content.Context;
import android.util.Log;

import com.example.studentmanagementsystemandroidapp.apis.auth_apis.AuthApi;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;
import com.example.studentmanagementsystemandroidapp.managers.session.SessionManager;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.auth.LoginRequest;
import com.example.studentmanagementsystemandroidapp.responses.auth.ApiError;
import com.example.studentmanagementsystemandroidapp.responses.auth.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthManager {
    private final AuthApi authApi;
    private final TokenStore tokenStore;
    private final Context appContext;
    private static final Gson GSON = new Gson();

    public AuthManager(Context context) {
        this.appContext = context.getApplicationContext();
        this.authApi = RetrofitInstance.getRetrofitInstance(this.appContext).create(AuthApi.class);
        this.tokenStore = new TokenStore(this.appContext);
    }

    public void login(String username, String password, AuthCallback callback) {
        Call<LoginResponse> call = authApi.login(new LoginRequest(username, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse body = response.body();

                    Log.d("AuthManager", "Login response ↓");
                    Log.d("AuthManager", "userId: " + body.getSubjectId());
                    Log.d("AuthManager", "role: " + String.valueOf(body.getRole()));
                    Log.d("AuthManager", "userType: " + String.valueOf(body.getUserType()));
                    Log.d("AuthManager", "message: " + String.valueOf(body.getMessage()));

                    String accessToken = body.getAccessToken();
                    String refreshToken = body.getRefreshToken();
                    int userId = body.getSubjectId();
                    String role = body.getRole();
                    String userType = body.getUserType();

                    try {
                        tokenStore.saveOnLogin(accessToken, refreshToken, userId, username, role, userType);
                        // reset single-fire flag now that we have a fresh session
                        SessionManager.resetFlag();
                        RetrofitInstance.reset(appContext);
                    } catch (Exception e) {
                        callback.onLoginFailure(null, new Throwable("Login attempt failed"));
                        return;
                    }
                    callback.onLoginSuccess(role, userType);
                } else {
                    handleErrorResponse(response, callback);
                }
            }

            @Override public void onFailure(Call<LoginResponse> call, Throwable t) {
                handleFailure(t, callback);
            }
        });
    }

    private void handleFailure(Throwable t, AuthCallback callback){
        String errorMessage = (t instanceof IOException)
                ? "Network problem. Please check your internet connection and try again shortly."
                : (t.getMessage() == null || t.getMessage().isEmpty() ? "Login attempt failed" : t.getMessage());
        callback.onLoginFailure(null, new Throwable(errorMessage));
    }

    private void handleErrorResponse(Response<LoginResponse> response, AuthCallback callback) {
        String message = "Login attempt failed";
        try {
            String raw = null;
            if (response.errorBody() != null) {
                try {
                    raw = response.errorBody().string(); // can only read once
                } finally {
                    response.errorBody().close();        // be tidy
                }
            }

            if (raw != null && !raw.trim().isEmpty()) {
                try {
                    ApiError apiError = GSON.fromJson(raw, ApiError.class);
                    if (apiError != null && apiError.getMessage() != null && !apiError.getMessage().trim().isEmpty()) {
                        message = apiError.getMessage().trim();
                    } else {
                        message = raw.trim();
                    }
                } catch (JsonSyntaxException ignore) {
                    message = raw.trim();
                }
            } else if (response.message() != null && !response.message().trim().isEmpty()) {
                message = response.message().trim();
            }

            if (response.code() == 401 && (message.equals("Login attempt failed") || message.isEmpty())) {
                message = "Incorrect username or password.";
            }
        } catch (Exception ignore) {
            // keep default message
        }
        callback.onLoginFailure(null, new Throwable(message));
    }

    public interface AuthCallback {
        void onLoginSuccess(String role, String userType); // ← add userType
        void onLoginFailure(Call<LoginResponse> call, Throwable t);
    }
}
