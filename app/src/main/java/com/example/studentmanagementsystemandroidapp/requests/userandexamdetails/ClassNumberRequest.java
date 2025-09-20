package com.example.studentmanagementsystemandroidapp.requests.userandexamdetails;

public class ClassNumberRequest {
    private int numberValue;

    public ClassNumberRequest(int numberValue) {
        this.numberValue = numberValue;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }
}
