package com.example.studentmanagementsystemandroidapp.requests.userandexamdetails;

public class StudentTypeRequest {
    private String type;

    public StudentTypeRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
