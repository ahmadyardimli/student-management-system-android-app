package com.example.studentmanagementsystemandroidapp.data;

import android.content.Context;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.StatusApi;
import com.example.studentmanagementsystemandroidapp.interfaces.data.DataFetchCallback;
import com.example.studentmanagementsystemandroidapp.interfaces.users.Status;
import com.example.studentmanagementsystemandroidapp.managers.admins.StatusManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusDataFetcher<T extends Status> {

    private final Context context;

    public StatusDataFetcher(Context context) {
        this.context = context.getApplicationContext();
    }

    public void getAllStatusesFromDatabase(StatusApi<T> statusApi, DataFetchCallback<T> callback) {
        StatusManager<T> statusManager = new StatusManager<>(context, statusApi);
        statusManager.getAllStatuses(new Callback<List<T>>() {
            @Override
            public void onResponse(Call<List<T>> call, Response<List<T>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onDataFetched(response.body());
                } else {
                    callback.onDataFetchFailed(new Exception("Failed to fetch status data: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<T>> call, Throwable t) {
                callback.onDataFetchFailed(t);
            }
        });
    }
}
