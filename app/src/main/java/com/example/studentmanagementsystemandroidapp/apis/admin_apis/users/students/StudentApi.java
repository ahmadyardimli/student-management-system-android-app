package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students;

import com.example.studentmanagementsystemandroidapp.models.users.Student;
import com.example.studentmanagementsystemandroidapp.requests.users.FullStudentRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.StudentRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudentApi {
    @GET("admin/users/students")
    Call<List<Student>> getAllStudents();

    @PUT("admin/users/students/update-student/{studentId}")
    Call<Student> updateStudent(@Path("studentId") int studentId, @Body StudentRequest studentRequest);

    @PUT("admin/users/students/update-full-student/{studentId}")
    Call<Student> updateFullStudent(
            @Path("studentId") int studentId,
            @Body FullStudentRequest request
    );

    @DELETE("admin/users/students/{studentId}")
    Call<Void> deleteStudent(@Path("studentId") int studentId);

    @GET("admin/users/students/{studentId}")
    Call<Student> getStudentById(@Path("studentId") int studentId);

    // To register student as one with user
    @POST("admin/users/students/register-full-student")
    Call<Student> registerStudent(@Body FullStudentRequest registerFullStudentRequest);

    @GET("admin/users/students/filter")
    Call<List<Student>> filterStudents(
            @Query("studentTypeId") Integer studentTypeId,
            @Query("categoryId") Integer categoryId,
            @Query("foreignLanguageId") Integer foreignLanguageId,
            @Query("bolmeId") Integer bolmeId,
            @Query("groupId") Integer groupId,
            @Query("altGroupId") Integer altGroupId,
            @Query("communicationStatusId") Integer communicationStatusId,
            @Query("classNumberId") Integer classNumberId,
            @Query("classLetterId") Integer classLetterId,
            @Query("userStatusId") Integer userStatusId,
            @Query("userTypeId") Integer userTypeId
    );
}
