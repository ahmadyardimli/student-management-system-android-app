package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassLetter;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassLetterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClassLetterApi {

    @GET("admin/user_exam_details/class-letters")
    Call<List<ClassLetter>> getAllClassLetters();

    @POST("admin/user_exam_details/class-letters/create-class-letter")
    Call<ClassLetter> createClassLetter(@Body ClassLetterRequest request);

    @PUT("admin/user_exam_details/class-letters/update-class-letter/{classLetterId}")
    Call<ClassLetter> updateClassLetter(
            @Path("classLetterId") int classLetterId,
            @Body ClassLetterRequest request
    );

    @DELETE("admin/user_exam_details/class-letters/{classLetterId}")
    Call<Void> deleteClassLetter(@Path("classLetterId") int classLetterId);
}
