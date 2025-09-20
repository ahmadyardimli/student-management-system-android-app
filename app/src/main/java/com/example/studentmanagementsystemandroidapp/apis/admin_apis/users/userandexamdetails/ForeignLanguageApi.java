package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ForeignLanguage;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ForeignLanguageRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ForeignLanguageApi {
    @GET("admin/user_exam_details/foreign-languages")
    Call<List<ForeignLanguage>> getAllForeignLanguages();

    @POST("admin/user_exam_details/foreign-languages/create-foreign-language")
    Call<ForeignLanguage> createForeignLanguage(@Body ForeignLanguageRequest foreignLanguageRequest);

    @PUT("admin/user_exam_details/foreign-languages/update-foreign-language/{foreignLanguageId}")
    Call<ForeignLanguage> updateForeignLanguage(@Path("foreignLanguageId") int foreignLanguageId, @Body ForeignLanguageRequest foreignLanguageRequest);

    @DELETE("admin/user_exam_details/foreign-languages/{foreignLanguageId}")
    Call<Void> deleteForeignLanguage(@Path("foreignLanguageId") int foreignLanguageId);
}

