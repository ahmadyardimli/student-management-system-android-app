package com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails;

public class RequestFieldInfo {
    private String itemLabel;
    private String itemValue;
    private String errorMessage;

    public RequestFieldInfo(String labelText, String editTextHint, String errorMessage) {
        this.itemLabel = labelText;
        this.itemValue = editTextHint;
        this.errorMessage = errorMessage;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public String getItemValue() {
        return itemValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
