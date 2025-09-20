package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.models.users.Student;
import com.example.studentmanagementsystemandroidapp.requests.users.FullStudentRequest;

import java.util.List;

import retrofit2.Call;

public class StudentApiImpl implements UserAndExamDetailsCommonApi<Student, FullStudentRequest> {
    private final StudentApi studentApi;

    public StudentApiImpl(StudentApi studentApi) {
        this.studentApi = studentApi;
    }

    @Override
    public Call<List<Student>> getAllItems() {
        return studentApi.getAllStudents();
    }

    @Override
    public Call<Student> createItem(FullStudentRequest request) {
        return studentApi.registerStudent(request);
    }

    @Override
    public Call<Student> updateItem(int itemId, FullStudentRequest request) {
        return studentApi.updateFullStudent(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return studentApi.deleteStudent(itemId);
    }

    @Override
    public Call<Student> getItemById(int itemId) {
        return studentApi.getStudentById(itemId);
    }

    public Call<List<Student>> filterStudents(
            Integer studentTypeId,
            Integer categoryId,
            Integer foreignLanguageId,
            Integer bolmeId,
            Integer groupId,
            Integer altGroupId,
            Integer communicationStatusId,
            Integer classNumberId,
            Integer classLetterId,
            Integer userStatusId,
            Integer userTypeId
    ) {
        return studentApi.filterStudents(
                studentTypeId,
                categoryId,
                foreignLanguageId,
                bolmeId,
                groupId,
                altGroupId,
                communicationStatusId,
                classNumberId,
                classLetterId,
                userStatusId,
                userTypeId
        );
    }

}

