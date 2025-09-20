package com.example.studentmanagementsystemandroidapp.apis.user_apis.student;

import com.example.studentmanagementsystemandroidapp.models.users.Student;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentSelfApi {
    @GET("user/students/me")
    Call<Student> me();
}