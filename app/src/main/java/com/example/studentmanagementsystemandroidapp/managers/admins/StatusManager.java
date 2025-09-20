package com.example.studentmanagementsystemandroidapp.managers.admins;

import android.content.Context;
import android.util.Log;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.StatusApi;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusManager<T> {
    private final StatusApi<T> statusApi;
    private final Context context;

    public StatusManager(Context context, StatusApi<T> statusApi) {
        this.context = context.getApplicationContext();
        this.statusApi = statusApi;
    }

    public void getAllStatuses(Callback<List<T>> callback) {
        Call<List<T>> call = statusApi.getAllStatuses();
        call.enqueue(new Callback<List<T>>() {
            @Override
            public void onResponse(Call<List<T>> call, Response<List<T>> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<T>> call, Throwable t) {
                Log.e("StatusManager", "Failed to get items: " + (t != null ? t.getMessage() : "unknown"));
                handleFailure(t, callback);
            }
        });
    }

    private void handleFailure(Throwable t, Callback<?> callback) {
        String errorMessage = (t instanceof IOException)
                ? "Network problem. Please check your internet connection and try again shortly."
                : (t != null && t.getMessage() != null && !t.getMessage().isEmpty()
                ? t.getMessage()
                : "An unknown error occurred.");
        Log.e("StatusManager", "Operation failed: " + errorMessage);
        callback.onFailure(null, new Throwable(errorMessage));
    }
}
