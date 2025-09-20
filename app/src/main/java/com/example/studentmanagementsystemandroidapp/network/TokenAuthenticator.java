package com.example.studentmanagementsystemandroidapp.network;

import android.content.Context;
import androidx.annotation.Nullable;

import com.example.studentmanagementsystemandroidapp.apis.auth_apis.AuthApi;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;
import com.example.studentmanagementsystemandroidapp.managers.session.SessionManager;
import com.example.studentmanagementsystemandroidapp.requests.auth.RefreshRequest;
import com.example.studentmanagementsystemandroidapp.responses.auth.LoginResponse;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAuthenticator implements Authenticator {
    private final TokenStore store;
    private final AuthApi refreshApi;
    private final Object lock = new Object();
    private final Context appCtx;

    public TokenAuthenticator(TokenStore store, String baseUrl, Context ctx) {
        this.store = store;
        this.appCtx = ctx.getApplicationContext();

        // Plain client to avoid recursion loops during refresh
        OkHttpClient plain = new OkHttpClient.Builder().build();

        this.refreshApi = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(plain)
                .build()
                .create(AuthApi.class);
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        // Avoid infinite loops
        if (responseCount(response) >= 2) return null;

        // Never try to refresh for /auth endpoints
        if (isAuthEndpoint(response.request())) return null;

        // If I have already ended the session, I do nothing (it prevents duplicate toasts)
        if (SessionManager.isSessionEnded()) return null;

        synchronized (lock) {
            // If another thread already refreshed, retry with the latest Access Token
            String currentAccessToken = store.getAccessToken();
            String headerAccessToken = response.request().header("Authorization");
            if (currentAccessToken != null && !currentAccessToken.equals(headerAccessToken)) {
                return response.request().newBuilder()
                        .header("Authorization", currentAccessToken)
                        .build();
            }

            // We need a refresh token to proceed
            String refreshToken = store.getRefreshToken();
            if (refreshToken == null || refreshToken.isEmpty()) {
                endSessionAndNotifyOnce();
                return null;
            }

            // Try calling /auth/refresh
            retrofit2.Response<LoginResponse> refreshResponse;
            try {
                refreshResponse = refreshApi.refresh(new RefreshRequest(refreshToken)).execute();
            } catch (IOException io) {
                // Network problem while refreshing — let original 401
                return null;
            }

            if (!refreshResponse.isSuccessful() || refreshResponse.body() == null) {
                // Whatever backend says, I want 1 consistent message.
                endSessionAndNotifyOnce();
                return null;
            }

            // Successful refresh: save rotated tokens and retry with new access token
            LoginResponse loginResponse = refreshResponse.body();
            String newAccessToken = loginResponse.getAccessToken();
            String newRefreshToken = loginResponse.getRefreshToken();

            try { store.saveTokens(newAccessToken, newRefreshToken); } catch (Exception ignored) {}

            return response.request().newBuilder()
                    .header("Authorization", newAccessToken)
                    .build();
        }
    }

    private void endSessionAndNotifyOnce() {
        try { store.clear(); } catch (Throwable ignored) {}
        //  always the same message, not backend text
        SessionManager.notifySessionExpiredOnce(appCtx, null);
    }

    private boolean isAuthEndpoint(Request request) {
        String path = request.url().encodedPath();
        return path.startsWith("/auth/");
    }

    private int responseCount(Response response) {
        int count = 1;
        while ((response = response.priorResponse()) != null) count++;
        return count;
    }
}