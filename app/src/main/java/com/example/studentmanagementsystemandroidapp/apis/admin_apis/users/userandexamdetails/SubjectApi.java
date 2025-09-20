package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Subject;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubjectRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SubjectApi {
    @GET("admin/user_exam_details/subjects")
    Call<List<Subject>> getAllSubjects();

    @POST("admin/user_exam_details/subjects/create-subject")
    Call<Subject> createSubject(@Body SubjectRequest subjectRequest);

    @PUT("admin/user_exam_details/subjects/update-subject/{subjectId}")
    Call<Subject> updateSubject(@Path("subjectId") int subjectId, @Body SubjectRequest subjectRequest);

    @DELETE("admin/user_exam_details/subjects/{subjectId}")
    Call<Void> deleteSubject(@Path("subjectId") int subjectId);
}
