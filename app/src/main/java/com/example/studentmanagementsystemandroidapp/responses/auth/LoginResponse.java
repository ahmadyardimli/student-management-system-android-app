package com.example.studentmanagementsystemandroidapp.responses.auth;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private int subjectId;
    private String message;
    private String role;
    private String userType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getSubjectId() {
        return subjectId;
    }


    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public String getUserType() {
        return userType;
    }
}
