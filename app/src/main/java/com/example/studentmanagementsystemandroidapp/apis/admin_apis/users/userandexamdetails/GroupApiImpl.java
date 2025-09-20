package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Group;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.GroupRequest;

import java.util.List;

import retrofit2.Call;

public class GroupApiImpl implements UserAndExamDetailsCommonApi<Group, GroupRequest> {
    private final GroupApi groupApi;

    public GroupApiImpl(GroupApi groupApi) {
        this.groupApi = groupApi;
    }

    @Override
    public Call<List<Group>> getAllItems() {
        return groupApi.getAllGroups();
    }

    @Override
    public Call<Group> createItem(GroupRequest request) {
        return groupApi.createGroup(request);
    }

    @Override
    public Call<Group> updateItem(int itemId, GroupRequest request) {
        return groupApi.updateGroup(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return groupApi.deleteGroup(itemId);
    }

    @Override
    public Call<Group> getItemById(int itemId) {
        return null;
    }
}
