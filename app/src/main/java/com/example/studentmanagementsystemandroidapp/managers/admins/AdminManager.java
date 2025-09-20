package com.example.studentmanagementsystemandroidapp.managers.admins;

import android.content.Context;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.admins.AdminApi;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;
import com.example.studentmanagementsystemandroidapp.models.admins.Admin;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;

public class AdminManager {
    private final AdminApi adminApi;
    private final TokenStore tokenStore;

    public AdminManager(Context ctx) {
        Context appCtx = ctx.getApplicationContext();
        this.adminApi = RetrofitInstance.getRetrofitInstance(appCtx).create(AdminApi.class);
        this.tokenStore = new TokenStore(appCtx);
    }

    public void loadCurrentAdmin(Callback<Admin> callback) {
        int id = tokenStore.getUserId();
        Call<Admin> call = adminApi.getById(id);
        call.enqueue(callback);
    }

    public String getCachedUsername() {
        return tokenStore.getUsername();
    }
}