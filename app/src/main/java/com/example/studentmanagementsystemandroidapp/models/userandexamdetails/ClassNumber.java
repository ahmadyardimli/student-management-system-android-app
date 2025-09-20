package com.example.studentmanagementsystemandroidapp.models.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;

import java.util.Arrays;
import java.util.List;

public class ClassNumber implements RecyclerViewItemPositionable {
    private int id;
    private int numberValue;
    private int position;

    public ClassNumber() {
    }

    public ClassNumber(int id, int numberValue) {
        this.id = id;
        this.numberValue = numberValue;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
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
        return String.valueOf(numberValue);
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfo() {
        return Arrays.asList(
                new RequestFieldInfo(
                        "Class Number",
                        String.valueOf(numberValue),
                        "Enter the class number."
                )
        );
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfoForCreation() {
        return Arrays.asList(
                new RequestFieldInfo(
                        "Add Class Number",
                        "Class Number",
                        "Enter the class number."
                )
        );
    }
}
