package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Section;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SectionRequest;

import java.util.List;

import retrofit2.Call;

public class SectionApiImpl implements UserAndExamDetailsCommonApi<Section, SectionRequest> {
    private final SectionApi sectionApi;

    public SectionApiImpl(SectionApi sectionApi) {
        this.sectionApi = sectionApi;
    }

    @Override
    public Call<List<Section>> getAllItems() {
        return sectionApi.getAllSections();
    }

    @Override
    public Call<Section> createItem(SectionRequest request) {
        return sectionApi.createSection(request);
    }

    @Override
    public Call<Section> updateItem(int itemId, SectionRequest request) {
        return sectionApi.updateSection(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return sectionApi.deleteSection(itemId);
    }

    @Override
    public Call<Section> getItemById(int itemId) {
        return null;
    }
}
