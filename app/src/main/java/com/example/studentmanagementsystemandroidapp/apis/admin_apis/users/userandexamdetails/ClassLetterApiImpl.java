package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassLetter;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassLetterRequest;

import java.util.List;

import retrofit2.Call;
public class ClassLetterApiImpl implements UserAndExamDetailsCommonApi<ClassLetter, ClassLetterRequest> {

    private final ClassLetterApi classLetterApi;

    public ClassLetterApiImpl(ClassLetterApi classLetterApi) {
        this.classLetterApi = classLetterApi;
    }

    @Override
    public Call<List<ClassLetter>> getAllItems() {
        return classLetterApi.getAllClassLetters();
    }

    @Override
    public Call<ClassLetter> createItem(ClassLetterRequest request) {
        return classLetterApi.createClassLetter(request);
    }

    @Override
    public Call<ClassLetter> updateItem(int itemId, ClassLetterRequest request) {
        return classLetterApi.updateClassLetter(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return classLetterApi.deleteClassLetter(itemId);
    }

    @Override
    public Call<ClassLetter> getItemById(int itemId) {
        // If you need to fetch a single ClassLetter by ID:
        // return classLetterApi.getClassLetterById(itemId);
        // (But if the endpoint does not exist, you can leave it as null or create it in your backend.)
        return null;
    }
}
