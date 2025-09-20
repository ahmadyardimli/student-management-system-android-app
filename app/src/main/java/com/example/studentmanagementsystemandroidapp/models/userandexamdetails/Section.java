package com.example.studentmanagementsystemandroidapp.models.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;

import java.util.Arrays;
import java.util.List;

public class Section implements RecyclerViewItemPositionable {
    private int id;
    private String section;
    private int position;

    public Section() {
    }

    public Section(int id, String bolmeName) {
        this.id = id;
        this.section = bolmeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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
        return section;
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfo() {
        return Arrays.asList(
                new RequestFieldInfo("Section", section, "Please enter the section.")
        );
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfoForCreation() {
        return Arrays.asList(
                new RequestFieldInfo("Add Section", "Section", "Please enter the section.")
        );
    }
}
