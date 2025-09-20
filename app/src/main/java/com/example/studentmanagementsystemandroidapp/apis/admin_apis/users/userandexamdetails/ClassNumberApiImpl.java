package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassNumber;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassNumberRequest;

import java.util.List;

import retrofit2.Call;
public class ClassNumberApiImpl implements UserAndExamDetailsCommonApi<ClassNumber, ClassNumberRequest> {

    private final ClassNumberApi classNumberApi;

    public ClassNumberApiImpl(ClassNumberApi classNumberApi) {
        this.classNumberApi = classNumberApi;
    }

    @Override
    public Call<List<ClassNumber>> getAllItems() {
        return classNumberApi.getAllClassNumbers();
    }

    @Override
    public Call<ClassNumber> createItem(ClassNumberRequest request) {
        return classNumberApi.createClassNumber(request);
    }

    @Override
    public Call<ClassNumber> updateItem(int itemId, ClassNumberRequest request) {
        return classNumberApi.updateClassNumber(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return classNumberApi.deleteClassNumber(itemId);
    }

    @Override
    public Call<ClassNumber> getItemById(int itemId) {
        return null;
    }
}
