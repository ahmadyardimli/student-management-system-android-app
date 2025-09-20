package com.example.studentmanagementsystemandroidapp.models.userandexamdetails;
import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import java.util.Arrays;
import java.util.List;

public class StudentType implements RecyclerViewItemPositionable {
    private int id;
    private String type;
    private int position;

    public StudentType() {
    }

    public StudentType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getItemId() {
        return id;
    }

    @Override
    public String getItemName() {
        return type;
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfo() {
        return Arrays.asList(
                new RequestFieldInfo("Student Type", type, "Enter the student type.")
        );
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfoForCreation() {
        return Arrays.asList(
                new RequestFieldInfo("Add Student Type", "Student Type", "Enter the student type.")
        );
    }
}
