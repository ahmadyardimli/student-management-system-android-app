/****************************************************************************/
/**************************** 3) ClassNumberApi ******************************/
/****************************************************************************/
package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassNumber;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassNumberRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClassNumberApi {

    @GET("admin/user_exam_details/class-numbers")
    Call<List<ClassNumber>> getAllClassNumbers();

    @POST("admin/user_exam_details/class-numbers/create-class-number")
    Call<ClassNumber> createClassNumber(@Body ClassNumberRequest request);

    @PUT("admin/user_exam_details/class-numbers/update-class-number/{classNumberId}")
    Call<ClassNumber> updateClassNumber(
            @Path("classNumberId") int classNumberId,
            @Body ClassNumberRequest request
    );

    @DELETE("admin/user_exam_details/class-numbers/{classNumberId}")
    Call<Void> deleteClassNumber(@Path("classNumberId") int classNumberId);
}
