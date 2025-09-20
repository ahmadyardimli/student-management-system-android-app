package com.example.studentmanagementsystemandroidapp.requests.users;

public class UserTypeRequest {
    private String type;

    public UserTypeRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
