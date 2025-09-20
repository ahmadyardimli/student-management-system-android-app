package com.example.studentmanagementsystemandroidapp.requests.userandexamdetails;

public class CategoryRequest {
    private String category;
    private int minClass;
    private int maxClass;

    public CategoryRequest(String category, int minClass, int maxClass) {
        this.category = category;
        this.minClass = minClass;
        this.maxClass = maxClass;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
