package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ForeignLanguage;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ForeignLanguageRequest;

import java.util.List;

import retrofit2.Call;

public class ForeignLanguageApiImpl implements UserAndExamDetailsCommonApi<ForeignLanguage, ForeignLanguageRequest> {
    private final ForeignLanguageApi foreignLanguageApi;

    public ForeignLanguageApiImpl(ForeignLanguageApi foreignLanguageApi) {
        this.foreignLanguageApi = foreignLanguageApi;
    }

    @Override
    public Call<List<ForeignLanguage>> getAllItems() {
        return foreignLanguageApi.getAllForeignLanguages();
    }

    @Override
    public Call<ForeignLanguage> createItem(ForeignLanguageRequest request) {
        return foreignLanguageApi.createForeignLanguage(request);
    }

    @Override
    public Call<ForeignLanguage> updateItem(int itemId, ForeignLanguageRequest request) {
        return foreignLanguageApi.updateForeignLanguage(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return foreignLanguageApi.deleteForeignLanguage(itemId);
    }

    @Override
    public Call<ForeignLanguage> getItemById(int itemId) {
        return null;
    }
}

