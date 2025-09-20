package com.example.studentmanagementsystemandroidapp.apis.auth_apis;

import com.example.studentmanagementsystemandroidapp.requests.auth.LoginRequest;
import com.example.studentmanagementsystemandroidapp.requests.auth.RefreshRequest;
import com.example.studentmanagementsystemandroidapp.responses.auth.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/refresh")
    Call<LoginResponse> refresh(@Body RefreshRequest refreshRequest);
}
