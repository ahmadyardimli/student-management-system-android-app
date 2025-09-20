package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons;

import com.example.studentmanagementsystemandroidapp.models.users.User;
import com.example.studentmanagementsystemandroidapp.requests.users.UserRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("admin/users")
    Call<List<User>> getAllUsers();

    @POST("admin/users/register-user")
    Call<User> createUser(@Body UserRequest userRequest);

    @PUT("admin/users/update-user/{userId}")
    Call<User> updateUser(@Path("userId") int userId, @Body UserRequest userRequest);

    @DELETE("admin/users/{userId}")
    Call<Void> deleteUser(@Path("userId") int userId);
}
