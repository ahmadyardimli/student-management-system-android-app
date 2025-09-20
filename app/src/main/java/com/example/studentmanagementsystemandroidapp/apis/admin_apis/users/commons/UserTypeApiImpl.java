package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.UserType;
import com.example.studentmanagementsystemandroidapp.requests.users.UserTypeRequest;

import java.util.List;

import retrofit2.Call;

public class UserTypeApiImpl implements UserAndExamDetailsCommonApi<UserType, UserTypeRequest> {
    private final UserTypeApi userTypeApi;

    public UserTypeApiImpl(UserTypeApi userTypeApi) {
        this.userTypeApi = userTypeApi;
    }

    @Override
    public Call<List<UserType>> getAllItems() {
        return userTypeApi.getAllUserTypes();
    }

    @Override
    public Call<UserType> createItem(UserTypeRequest request) {
        return userTypeApi.createUserType(request);
    }

    @Override
    public Call<UserType> updateItem(int itemId, UserTypeRequest request) {
        return userTypeApi.updateUserType(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return userTypeApi.deleteUserType(itemId);
    }

    @Override
    public Call<UserType> getItemById(int itemId) {
        return null;
    }
}
