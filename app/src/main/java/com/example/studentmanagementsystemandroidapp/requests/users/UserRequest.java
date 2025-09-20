package com.example.studentmanagementsystemandroidapp.requests.users;

public class UserRequest {
    private String username;
    private String password;
    private String email;
    private int userTypeId;
    private int statusId = -1; // default, beacuse active id starts from 0, so def can not be 0

    public UserRequest() {
    }

    public UserRequest(String username, String password, String email, int userTypeId, int statusId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userTypeId = userTypeId;
        this.statusId = statusId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
