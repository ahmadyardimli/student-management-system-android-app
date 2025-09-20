package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Section;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SectionRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SectionApi {
    @GET("admin/user_exam_details/sections")
    Call<List<Section>> getAllSections();

    @POST("admin/user_exam_details/sections/create-section")
    Call<Section> createSection(@Body SectionRequest sectionRequest);

    @PUT("admin/user_exam_details/sections/update-section/{sectionId}")
    Call<Section> updateSection(@Path("sectionId") int sectionId, @Body SectionRequest sectionRequest);

    @DELETE("admin/user_exam_details/sections/{sectionId}")
    Call<Void> deleteSection(@Path("sectionId") int sectionId);
}
