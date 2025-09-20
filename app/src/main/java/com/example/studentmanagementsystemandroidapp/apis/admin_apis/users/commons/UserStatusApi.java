package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons;

import com.example.studentmanagementsystemandroidapp.models.users.UserStatus;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;
public interface UserStatusApi {
    @GET("admin/users/user-statuses")
    Call<List<UserStatus>> getAllUserStatuses();
}