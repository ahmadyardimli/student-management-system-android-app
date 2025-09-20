package com.example.studentmanagementsystemandroidapp.models.users;

import com.example.studentmanagementsystemandroidapp.interfaces.users.Status;

import java.io.Serializable;

public class UserStatus implements Status, Serializable {
    private int id;
    private String status;

    public UserStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getStatusId() {
        return id;
    }

    @Override
    public String getStatusText() {
        return status;
    }
}
