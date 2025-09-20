package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students;

import com.example.studentmanagementsystemandroidapp.models.users.StudentCommunicationSenderStatus;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentCommunicationSenderStatusApi {
    @GET("admin/users/students/student-communication-sender-statuses")
    Call<List<StudentCommunicationSenderStatus>> getAllStudentCommunicationSenderStatuses();
}
