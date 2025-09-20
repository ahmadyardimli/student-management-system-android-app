package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.StatusApi;
import com.example.studentmanagementsystemandroidapp.models.users.TeacherCommunicationSenderStatus;
import java.util.List;
import retrofit2.Call;

public class TeacherCommunicationSenderStatusApiImpl
        implements StatusApi<TeacherCommunicationSenderStatus> {

    private final TeacherCommunicationSenderStatusApi api;

    public TeacherCommunicationSenderStatusApiImpl(TeacherCommunicationSenderStatusApi api) {
        this.api = api;
    }

    @Override
    public Call<List<TeacherCommunicationSenderStatus>> getAllStatuses() {
        return api.getAllTeacherCommunicationSenderStatuses(); // interceptor adds Authorization
    }
}
