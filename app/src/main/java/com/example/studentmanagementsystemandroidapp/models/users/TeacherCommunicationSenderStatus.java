package com.example.studentmanagementsystemandroidapp.models.users;

import com.example.studentmanagementsystemandroidapp.interfaces.users.Status;

public class TeacherCommunicationSenderStatus implements Status {
    private int id;
    private String status;

    public TeacherCommunicationSenderStatus(int id, String status) {
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
