package com.example.studentmanagementsystemandroidapp.requests.userandexamdetails;

public class ClassLetterRequest {

    private String letterValue;

    public ClassLetterRequest(String letterValue) {
        this.letterValue = letterValue;
    }

    public String getLetterValue() {
        return letterValue;
    }

    public void setLetterValue(String letterValue) {
        this.letterValue = letterValue;
    }
}
