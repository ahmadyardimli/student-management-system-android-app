package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.StudentType;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.StudentTypeRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentTypeApi {
    @GET("admin/user_exam_details/student-types")
    Call<List<StudentType>> getAllStudentTypes();

    @POST("admin/user_exam_details/student-types/create-studentType")
    Call<StudentType> createStudentType(@Body StudentTypeRequest studentTypeRequest);

    @PUT("admin/user_exam_details/student-types/update-studentType/{studentTypeId}")
    Call<StudentType> updateStudentType(@Path("studentTypeId") int studentTypeId, @Body StudentTypeRequest studentTypeRequest);

    @DELETE("admin/user_exam_details/student-types/{studentTypeId}")
    Call<Void> deleteStudentType(@Path("studentTypeId") int studentTypeId);
}
