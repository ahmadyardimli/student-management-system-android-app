package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons;

import com.example.studentmanagementsystemandroidapp.models.users.UserStatus;

import java.util.List;

import retrofit2.Call;

public class UserStatusApiImpl implements StatusApi<UserStatus> {
    private final UserStatusApi api;

    public UserStatusApiImpl(UserStatusApi api) {
        this.api = api;
    }

    @Override
    public Call<List<UserStatus>> getAllStatuses() {
        return api.getAllUserStatuses(); // no token
    }
}
