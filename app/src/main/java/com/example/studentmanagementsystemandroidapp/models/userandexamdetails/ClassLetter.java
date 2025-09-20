package com.example.studentmanagementsystemandroidapp.models.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;

import java.util.Arrays;
import java.util.List;

public class ClassLetter implements RecyclerViewItemPositionable {
    private int id;
    private String letterValue;
    private int position;

    public ClassLetter() {
    }

    public ClassLetter(int id, String letterValue) {
        this.id = id;
        this.letterValue = letterValue;
    }

    public int getId() {
        return id;
    }

    public String getLetterValue() {
        return letterValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLetterValue(String letterValue) {
        this.letterValue = letterValue;
    }

    // Implementation of RecyclerViewItemPositionable
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
        return letterValue;
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfo() {
        // This method is used for dynamic form-building
        return Arrays.asList(
                new RequestFieldInfo(
                        "Class Letter",
                        (letterValue != null ? letterValue : ""),
                        "Enter the class letter."
                )
        );
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfoForCreation() {
        // Field descriptions for creation dialogs
        return Arrays.asList(
                new RequestFieldInfo(
                        "Add Class Letter",
                        "Class Letter",
                        "Enter the class letter."
                )
        );
    }
}
