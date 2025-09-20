package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.models.users.User;
import com.example.studentmanagementsystemandroidapp.requests.users.UserRequest;

import java.util.List;

import retrofit2.Call;

public class UserApiImpl implements UserAndExamDetailsCommonApi<User, UserRequest> {
    private final UserApi userApi;

    public UserApiImpl(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public Call<List<User>> getAllItems() {
        return userApi.getAllUsers();
    }

    @Override
    public Call<User> createItem(UserRequest request) {
        return userApi.createUser(request);
    }

    @Override
    public Call<User> updateItem(int itemId, UserRequest request) {
        return userApi.updateUser(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return userApi.deleteUser(itemId);
    }

    @Override
    public Call<User> getItemById(int itemId) {
        return null;
    }
}
