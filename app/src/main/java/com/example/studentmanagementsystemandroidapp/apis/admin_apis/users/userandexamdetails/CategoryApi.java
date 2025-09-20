package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Category;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.CategoryRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryApi {
    @GET("admin/user_exam_details/categories")
    Call<List<Category>> getAllCategories();

    @POST("admin/user_exam_details/categories/create-category")
    Call<Category> createCategory(@Body CategoryRequest categoryRequest);

    @PUT("admin/user_exam_details/categories/update-category/{categoryId}")
    Call<Category> updateCategory(@Path("studentCategoryId") int studentCategoryId, @Body CategoryRequest categoryRequest);

    @DELETE("admin/user_exam_details/categories/{categoryId}")
    Call<Void> deleteCategory(@Path("categoryId") int categoryId);
}