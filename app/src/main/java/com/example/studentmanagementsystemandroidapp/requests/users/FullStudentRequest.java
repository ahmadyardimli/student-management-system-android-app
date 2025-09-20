package com.example.studentmanagementsystemandroidapp.requests.users;

public class FullStudentRequest {
    private UserRequest userRequest;
    private StudentRequest studentRequest;

    public FullStudentRequest() {
    }

    public FullStudentRequest(UserRequest userRequest, StudentRequest studentRequest) {
        this.userRequest = userRequest;
        this.studentRequest = studentRequest;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public StudentRequest getStudentRequest() {
        return studentRequest;
    }

    public void setStudentRequest(StudentRequest studentRequest) {
        this.studentRequest = studentRequest;
    }
}
