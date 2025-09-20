package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons;

import java.util.List;

import retrofit2.Call;

public interface StatusApi<T> {
    Call<List<T>> getAllStatuses();
}

