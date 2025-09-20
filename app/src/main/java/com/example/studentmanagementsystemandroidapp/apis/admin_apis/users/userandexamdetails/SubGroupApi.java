package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.SubGroup;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubGroupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SubGroupApi {
    @GET("admin/user_exam_details/sub-groups")
    Call<List<SubGroup>> getAllSubGroups();

    @POST("admin/user_exam_details/sub-groups/create-sub-group")
    Call<SubGroup> createSubGroup(@Body SubGroupRequest subGroupRequest);

    @PUT("admin/user_exam_details/sub-groups/update-sub-group/{subGroupId}")
    Call<SubGroup> updateSubGroup(@Path("subGroupId") int subGroupId, @Body SubGroupRequest subGroupRequest);

    @DELETE("admin/user_exam_details/sub-groups/{subGroupId}")
    Call<Void> deleteSubGroup(@Path("subGroupId") int subGroupId);
}
