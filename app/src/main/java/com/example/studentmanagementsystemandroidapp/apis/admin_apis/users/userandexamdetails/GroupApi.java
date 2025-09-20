package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Group;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.GroupRequest;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GroupApi {
    @GET("admin/user_exam_details/groups")
    Call<List<Group>> getAllGroups();

    @POST("admin/user_exam_details/groups/create-group")
    Call<Group> createGroup(@Body GroupRequest groupRequest);

    @PUT("admin/user_exam_details/groups/update-group/{groupId}")
    Call<Group> updateGroup(@Path("groupId") int groupId, @Body GroupRequest groupRequest);

    @DELETE("admin/user_exam_details/groups/{groupId}")
    Call<Void> deleteGroup(@Path("groupId") int groupId);
}
