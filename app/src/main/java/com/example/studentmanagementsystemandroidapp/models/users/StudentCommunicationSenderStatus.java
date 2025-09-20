package com.example.studentmanagementsystemandroidapp.models.users;

import com.example.studentmanagementsystemandroidapp.interfaces.users.Status;

public class StudentCommunicationSenderStatus implements Status {
    private int id;
    private String status;

    public StudentCommunicationSenderStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    // Getter and Setter methods
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

    // Implementing Status interface methods
    @Override
    public int getStatusId() {
        return id;
    }

    @Override
    public String getStatusText() {
        return status;
    }
}