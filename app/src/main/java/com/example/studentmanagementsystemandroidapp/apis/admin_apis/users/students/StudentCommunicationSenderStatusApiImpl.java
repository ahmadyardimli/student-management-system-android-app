package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.StatusApi;
import com.example.studentmanagementsystemandroidapp.models.users.StudentCommunicationSenderStatus;

import java.util.List;

import retrofit2.Call;

public class StudentCommunicationSenderStatusApiImpl implements StatusApi<StudentCommunicationSenderStatus> {
    private final StudentCommunicationSenderStatusApi api;

    public StudentCommunicationSenderStatusApiImpl(StudentCommunicationSenderStatusApi api) {
        this.api = api;
    }

    @Override
    public Call<List<StudentCommunicationSenderStatus>> getAllStatuses() {
        return api.getAllStudentCommunicationSenderStatuses(); // no token
    }
}
