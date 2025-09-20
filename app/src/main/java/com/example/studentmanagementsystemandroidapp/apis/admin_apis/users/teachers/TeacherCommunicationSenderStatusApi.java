package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers;

import com.example.studentmanagementsystemandroidapp.models.users.TeacherCommunicationSenderStatus;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TeacherCommunicationSenderStatusApi {
    @GET("admin/users/teachers/teacher-communication-sender-statuses")
    Call<List<TeacherCommunicationSenderStatus>> getAllTeacherCommunicationSenderStatuses(); // no header param
}
