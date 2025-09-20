package com.example.studentmanagementsystemandroidapp.apis.admin_apis.admins;

import com.example.studentmanagementsystemandroidapp.models.admins.Admin;
import com.example.studentmanagementsystemandroidapp.requests.admins.AdminRequest;
import com.example.studentmanagementsystemandroidapp.responses.auth.LoginResponse; // Only if your register returns Auth-like response

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminApi {
    @GET("admin/admins/{adminId}")
    Call<Admin> getById(@Path("adminId") int adminId);
    @GET("admin/admins")
    Call<List<Admin>> getAll();
    @POST("admin/admins/register-admin")
    Call<LoginResponse> register(@Body AdminRequest request);
    @PUT("admin/admins/update-admin/{adminId}")
    Call<Admin> update(@Path("adminId") int adminId, @Body AdminRequest request);
    @DELETE("admin/admins/{adminId}")
    Call<Void> delete(@Path("adminId") int adminId);
}
