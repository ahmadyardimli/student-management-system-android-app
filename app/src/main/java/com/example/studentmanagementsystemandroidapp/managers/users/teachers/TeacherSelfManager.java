package com.example.studentmanagementsystemandroidapp.managers.users.teachers;

import android.content.Context;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import com.example.studentmanagementsystemandroidapp.apis.user_apis.teacher.TeacherSelfApi;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;
import com.example.studentmanagementsystemandroidapp.models.users.Teacher;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherSelfManager {
    private final TeacherSelfApi api;
    private final TokenStore store;

    // Simple process-wide cache
    private static @Nullable Teacher cached;
    private static long lastFetchedMs = 0L;
    private static final long CACHE_TTL_MS = 60_000; // 1 minute, tweak as you like

    public TeacherSelfManager(Context ctx) {
        this.api = RetrofitInstance.getRetrofitInstance(ctx).create(TeacherSelfApi.class);
        this.store = new TokenStore(ctx.getApplicationContext());
    }

    public String getCachedUsername() { return store.getUsername(); }
    public int getCurrentUserId()     { return store.getUserId(); }

    public @Nullable Teacher peekCache() { return cached; }

    public static void clearCache() {
        cached = null;
        lastFetchedMs = 0L;
    }

    // Load, using cache if fresh; always invokes cb on the main thread via Retrofit.
    @MainThread
    public void loadCurrentTeacher(Callback<Teacher> cb) {
        long now = System.currentTimeMillis();
        if (cached != null && (now - lastFetchedMs) < CACHE_TTL_MS) {
            // Serve immediately from memory
            cb.onResponse(null, Response.success(cached));
            return;
        }

        api.me().enqueue(new Callback<Teacher>() {
            @Override public void onResponse(Call<Teacher> call, Response<Teacher> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    cached = resp.body();
                    lastFetchedMs = System.currentTimeMillis();
                }
                cb.onResponse(call, resp);
            }
            @Override public void onFailure(Call<Teacher> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }
}
