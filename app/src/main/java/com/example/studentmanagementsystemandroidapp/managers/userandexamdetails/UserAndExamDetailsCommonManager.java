package com.example.studentmanagementsystemandroidapp.managers.userandexamdetails;

import android.content.Context;
import android.util.Log;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAndExamDetailsCommonManager<Model, Req> {
    private final UserAndExamDetailsCommonApi<Model, Req> api;
    private Context context;

    public UserAndExamDetailsCommonManager(UserAndExamDetailsCommonApi<Model, Req> api, Context context) {
        Log.d("constructor", api.toString());
        this.api = api;
        this.context = context;
    }

    public void getAllItems(Context context, Callback<List<Model>> callback) {
        Call<List<Model>> call = api.getAllItems();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.e("CommonManager", "Failed to get items: " + t.getMessage());
                handleFailure(t, callback);
            }
        });
    }

    public void createItem(Context context, Req request, Callback<Model> callback) {
        Call<Model> call = api.createItem(request);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                handleFailure(t, callback);
            }
        });
    }

    public void updateItem(int itemId, Req request, Callback<Model> callback) {
        Call<Model> call = api.updateItem(itemId, request);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                callback.onResponse(call, response);
//                if (response.isSuccessful()) {
//                    callback.onResponse(call, response);
//                } else {
//                    handleErrorResponse(response, callback);
//                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                handleFailure(t, callback);
            }
        });
    }

    public void deleteItem(int itemId, Callback<Void> callback) {
        Call<Void> call = api.deleteItem(itemId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onResponse(call, response);
                    handleErrorResponse(response, callback);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                handleFailure(t, callback);
            }
        });
    }

    public void getItemById(int itemId, Callback<Model> callback) {
        Call<Model> call = api.getItemById(itemId);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    handleErrorResponse(response, callback);
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                handleFailure(t, callback);
            }
        });
    }


    private void handleErrorResponse(Response<?> response, Callback<?> callback) {
        try {
            String errorBody = response.errorBody().string();
            callback.onFailure(null, new Throwable(errorBody));
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFailure(null, new Throwable("Operation failed"));
        }
    }

    private void handleFailure(Throwable t, Callback<?> callback) {
        String errorMessage;
        if (t instanceof IOException) {
            errorMessage = "Network problem. Please check your internet connection and try again shortly.";
        } else {
            errorMessage = t.getMessage();
        }

        Log.e("CommonManager", "Operation failed: " + errorMessage);
        callback.onFailure(null, new Throwable(errorMessage));
    }
}
