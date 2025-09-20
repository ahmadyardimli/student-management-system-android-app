package com.example.studentmanagementsystemandroidapp.requests.userandexamdetails;

public class ForeignLanguageRequest {
    private String foreignLanguage;

    public ForeignLanguageRequest(String foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public String getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(String foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }
}
