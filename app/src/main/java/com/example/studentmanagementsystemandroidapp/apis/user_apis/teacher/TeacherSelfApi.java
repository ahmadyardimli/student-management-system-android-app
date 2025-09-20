package com.example.studentmanagementsystemandroidapp.apis.user_apis.teacher;

import com.example.studentmanagementsystemandroidapp.models.users.Teacher;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TeacherSelfApi {
    @GET("user/teachers/me")
    Call<Teacher> me();
}
