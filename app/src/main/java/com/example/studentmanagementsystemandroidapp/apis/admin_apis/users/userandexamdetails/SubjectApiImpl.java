package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Subject;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubjectRequest;

import java.util.List;

import retrofit2.Call;

public class SubjectApiImpl implements UserAndExamDetailsCommonApi<Subject, SubjectRequest> {
    private final SubjectApi subjectApi;

    public SubjectApiImpl(SubjectApi subjectApi) {
        this.subjectApi = subjectApi;
    }

    @Override
    public Call<List<Subject>> getAllItems() {
        return subjectApi.getAllSubjects();
    }

    @Override
    public Call<Subject> createItem(SubjectRequest request) {
        return subjectApi.createSubject(request);
    }

    @Override
    public Call<Subject> updateItem(int itemId, SubjectRequest request) {
        return subjectApi.updateSubject(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return subjectApi.deleteSubject(itemId);
    }

    @Override
    public Call<Subject> getItemById(int itemId) {
        return null;
    }
}
