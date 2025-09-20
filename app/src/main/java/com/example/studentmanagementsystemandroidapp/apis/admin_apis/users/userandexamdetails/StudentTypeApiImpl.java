package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.StudentType;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.StudentTypeRequest;

import java.util.List;

import retrofit2.Call;

public class StudentTypeApiImpl implements UserAndExamDetailsCommonApi<StudentType, StudentTypeRequest> {
    private final StudentTypeApi studentTypeApi;

    public StudentTypeApiImpl(StudentTypeApi studentTypeApi) {
        this.studentTypeApi = studentTypeApi;
    }

    @Override
    public Call<List<StudentType>> getAllItems() {
        return studentTypeApi.getAllStudentTypes();
    }

    @Override
    public Call<StudentType> createItem(StudentTypeRequest request) {
        return studentTypeApi.createStudentType(request);
    }

    @Override
    public Call<StudentType> updateItem(int itemId, StudentTypeRequest request) {
        return studentTypeApi.updateStudentType(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return studentTypeApi.deleteStudentType(itemId);
    }

    @Override
    public Call<StudentType> getItemById(int itemId) {
        return null;
    }
}
