package com.example.studentmanagementsystemandroidapp.requests.users;

public class FullTeacherRequest {
    private UserRequest userRequest;
    private TeacherRequest teacherRequest;

    public FullTeacherRequest() {
    }

    public FullTeacherRequest(UserRequest userRequest, TeacherRequest teacherRequest) {
        this.userRequest = userRequest;
        this.teacherRequest = teacherRequest;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public TeacherRequest getTeacherRequest() {
        return teacherRequest;
    }

    public void setTeacherRequest(TeacherRequest teacherRequest) {
        this.teacherRequest = teacherRequest;
    }
}
