package com.example.studentmanagementsystemandroidapp.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import com.example.studentmanagementsystemandroidapp.interfaces.data.DataFetchCallback;
import com.example.studentmanagementsystemandroidapp.managers.userandexamdetails.UserAndExamDetailsCommonManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAndExamDetailsDataFetcher <Model extends RecyclerViewItemPositionable, Req>{
    private Context context;
    private UserAndExamDetailsCommonManager<Model, Req> itemManager;
    private UserAndExamDetailsCommonApi<Model, Req> api;

    public UserAndExamDetailsDataFetcher(Context context, UserAndExamDetailsCommonApi<Model, Req> api) {
        this.context = context;
        this.api = api;
    }

    public void getAllDataFromDatabase(DataFetchCallback<Model> callback) {
        // Initialize the manager using the obtained API
        itemManager = new UserAndExamDetailsCommonManager<Model, Req>(api, context);

        itemManager.getAllItems(context, new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                List<Model> items = response.body();
                if (response.isSuccessful()) {
                    callback.onDataFetched(items);
                } else {
                    callback.onDataFetched(null);
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.d("Data Fetcher", "Inside ON FAILURE");
                callback.onDataFetchFailed(t);
            }
        });
    }

    public void getDataResponseFromDatabase(DataFetchCallback<Model> callback) {
        itemManager = new UserAndExamDetailsCommonManager<Model, Req>(api, context);

        itemManager.getAllItems(context, new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
               if (response.isSuccessful())
                   callback.onDataFetched(response.body());
               else
                   callback.onUnsuccessfulResponseFetched(response);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.d("Data Fetcher", "Inside ON FAILURE");
                callback.onDataFetchFailed(t);
            }
        });
    }

    public void getItemById(int itemId, DataFetchCallback<Model> callback) {
        itemManager = new UserAndExamDetailsCommonManager<>(api, context);

        itemManager.getItemById(itemId, new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    Model item = response.body();
                    callback.onSingleItemFetched(item);
                } else {
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e("Data Fetcher", "Error reading error body");
                        e.printStackTrace();
                    }
                    callback.onDataFetchFailed(new Exception("Failed to fetch item data"));
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("Data Fetcher", "Failed to fetch item data: " + t.getMessage());
                callback.onDataFetchFailed(t);
            }
        });
    }
}
