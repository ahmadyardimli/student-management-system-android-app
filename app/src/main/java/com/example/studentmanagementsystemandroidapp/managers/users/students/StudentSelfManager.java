package com.example.studentmanagementsystemandroidapp.managers.users.students;

import android.content.Context;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import com.example.studentmanagementsystemandroidapp.apis.user_apis.student.StudentSelfApi;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;
import com.example.studentmanagementsystemandroidapp.models.users.Student;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentSelfManager {
    private final StudentSelfApi api;
    private final TokenStore store;

    // Simple process-wide cache
    private static @Nullable Student cached;
    private static long lastFetchedMs = 0L;
    private static final long CACHE_TTL_MS = 60_000; // 1 minute

    public StudentSelfManager(Context ctx) {
        this.api = RetrofitInstance.getRetrofitInstance(ctx).create(StudentSelfApi.class);
        this.store = new TokenStore(ctx.getApplicationContext());
    }

    public String getCachedUsername() { return store.getUsername(); }
    public int getCurrentUserId()     { return store.getUserId(); }

    // Optional sync peek (no network).
    public @Nullable Student peekCache() { return cached; }

    // Clear cache (call on logout or when you know profile changed).
    public static void clearCache() {
        cached = null;
        lastFetchedMs = 0L;
    }

    // Load, using cache if fresh; always invokes cb on the main thread via Retrofit.
    @MainThread
    public void loadCurrentStudent(Callback<Student> cb) {
        long now = System.currentTimeMillis();
        if (cached != null && (now - lastFetchedMs) < CACHE_TTL_MS) {
            cb.onResponse(null, Response.success(cached));
            return;
        }

        api.me().enqueue(new Callback<Student>() {
            @Override public void onResponse(Call<Student> call, Response<Student> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    cached = resp.body();
                    lastFetchedMs = System.currentTimeMillis();
                }
                cb.onResponse(call, resp);
            }
            @Override public void onFailure(Call<Student> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }
}

