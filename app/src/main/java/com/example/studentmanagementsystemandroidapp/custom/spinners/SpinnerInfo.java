package com.example.studentmanagementsystemandroidapp.custom.spinners;

import android.view.View;

public class SpinnerInfo {
    private CustomSpinner spinner;
    private View errorMessageView;
    private boolean isInitialized;
    private String errorMessage;

    public SpinnerInfo(CustomSpinner spinner, View errorMessageView, String errorMessage) {
        this.spinner = spinner;
        this.errorMessageView = errorMessageView;
        this.errorMessage = errorMessage;
        this.isInitialized = false;
    }

    // Getters and setters
    public CustomSpinner getSpinner() {
        return spinner;
    }

    public void setSpinner(CustomSpinner spinner) {
        this.spinner = spinner;
    }

    public View getErrorMessageView() {
        return errorMessageView;
    }

    public void setErrorMessageView(View errorMessageView) {
        this.errorMessageView = errorMessageView;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
