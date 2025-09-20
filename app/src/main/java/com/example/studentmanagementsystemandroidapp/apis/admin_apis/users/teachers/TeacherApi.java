package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers;

import com.example.studentmanagementsystemandroidapp.models.users.Teacher;
import com.example.studentmanagementsystemandroidapp.requests.users.FullTeacherRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.TeacherRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TeacherApi {
    @GET("admin/users/teachers")
    Call<List<Teacher>> getAllTeachers();

    @POST("admin/users/teachers/register-full-teacher")
    Call<Teacher> registerTeacher(@Body FullTeacherRequest registerFullTeacherRequest);

    @PUT("admin/users/teachers/update-teacher/{teacherId}")
    Call<Teacher> updateTeacher(@Path("teacherId") int teacherId, @Body TeacherRequest teacherRequest);

    @DELETE("admin/users/teachers/{teacherId}")
    Call<Void> deleteTeacher(@Path("teacherId") int teacherId);

    @GET("admin/users/teachers/{teacherId}")
    Call<Teacher> getTeacherById(@Path("teacherId") int teacherId);

    @PUT("admin/users/teachers/update-full-teacher/{teacherId}")
    Call<Teacher> updateFullTeacher(
            @Path("teacherId") int teacherId,
            @Body FullTeacherRequest request
    );
}
