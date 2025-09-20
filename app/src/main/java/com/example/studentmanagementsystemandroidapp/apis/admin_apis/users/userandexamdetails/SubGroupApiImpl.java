package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.SubGroup;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubGroupRequest;

import java.util.List;

import retrofit2.Call;

public class SubGroupApiImpl implements UserAndExamDetailsCommonApi<SubGroup, SubGroupRequest> {
    private final SubGroupApi subGroupApi;

    public SubGroupApiImpl(SubGroupApi subGroupApi) {
        this.subGroupApi = subGroupApi;
    }

    @Override
    public Call<List<SubGroup>> getAllItems() {
        return subGroupApi.getAllSubGroups();
    }

    @Override
    public Call<SubGroup> createItem(SubGroupRequest request) {
        return subGroupApi.createSubGroup(request);
    }

    @Override
    public Call<SubGroup> updateItem(int itemId, SubGroupRequest request) {
        return subGroupApi.updateSubGroup(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return subGroupApi.deleteSubGroup(itemId);
    }

    @Override
    public Call<SubGroup> getItemById(int itemId) {
        return null;
    }
}
