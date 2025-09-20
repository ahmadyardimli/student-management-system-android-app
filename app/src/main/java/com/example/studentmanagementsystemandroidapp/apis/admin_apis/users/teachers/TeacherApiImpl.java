package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.models.users.Teacher;
import com.example.studentmanagementsystemandroidapp.requests.users.FullTeacherRequest;

import java.util.List;

import retrofit2.Call;

public class TeacherApiImpl implements UserAndExamDetailsCommonApi<Teacher, FullTeacherRequest> {
    private final TeacherApi teacherApi;

    public TeacherApiImpl(TeacherApi teacherApi) {
        this.teacherApi = teacherApi;
    }

    @Override
    public Call<List<Teacher>> getAllItems() {
        return teacherApi.getAllTeachers();
    }

    @Override
    public Call<Teacher> createItem(FullTeacherRequest request) {
        return teacherApi.registerTeacher(request);
    }

    @Override
    public Call<Teacher> updateItem(int itemId, FullTeacherRequest request) {
        return teacherApi.updateFullTeacher(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return teacherApi.deleteTeacher(itemId);
    }

    @Override
    public Call<Teacher> getItemById(int itemId) {
        return teacherApi.getTeacherById(itemId);
    }
}
