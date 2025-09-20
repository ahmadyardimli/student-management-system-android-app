package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.UserType;
import com.example.studentmanagementsystemandroidapp.requests.users.UserTypeRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserTypeApi {
    @GET("admin/users/user-types")
    Call<List<UserType>> getAllUserTypes();

    @POST("admin/users/user-types/create-userType")
    Call<UserType> createUserType(@Body UserTypeRequest userTypeRequest);

    @PUT("admin/users/user-types/update-userType/{userTypeId}")
    Call<UserType> updateUserType(@Path("userTypeId") int userTypeId, @Body UserTypeRequest userTypeRequest);

    @DELETE("admin/users/user-types/{userTypeId}")
    Call<Void> deleteUserType(@Path("userTypeId") int userTypeId);
}
