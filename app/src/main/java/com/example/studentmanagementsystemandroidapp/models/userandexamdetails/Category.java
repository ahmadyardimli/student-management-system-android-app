package com.example.studentmanagementsystemandroidapp.models.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import java.util.Arrays;
import java.util.List;

public class Category implements RecyclerViewItemPositionable {
    private int id;
    private String category;
    private int minClass;
    private int maxClass;
    private int position;

    public Category() {
    }

    public Category(int id, String categoryName) {
        this.id = id;
        this.category = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return category;
    }

    public int getMinClass() {
        return minClass;
    }

    public void setMinClass(int minClass) {
        this.minClass = minClass;
    }

    public int getMaxClass() {
        return maxClass;
    }

    public void setMaxClass(int maxClass) {
        this.maxClass = maxClass;
    }


    @Override
    public List<RequestFieldInfo> getRequestFieldInfo() {
        return Arrays.asList(
                new RequestFieldInfo("Category", category, "Enter the category."),
                new RequestFieldInfo("Starting class", String.valueOf(minClass), "Enter the starting class."),
                new RequestFieldInfo("Ending class", String.valueOf(maxClass), "Enter the ending class.")
        );
    }


    @Override
    public List<RequestFieldInfo> getRequestFieldInfoForCreation() {
        return Arrays.asList(
                new RequestFieldInfo("Add Category", "Category", "Enter the category."),
                new RequestFieldInfo("Add Starting Class", "Starting class", "Enter the starting class."),
                new RequestFieldInfo("Add Ending Class", "Ending class", "Enter the ending class.")
        );
    }
}