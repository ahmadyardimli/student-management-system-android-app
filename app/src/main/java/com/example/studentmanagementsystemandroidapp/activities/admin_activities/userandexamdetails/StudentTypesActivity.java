package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.StudentTypeApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.StudentTypeApiImpl;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.StudentType;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.StudentTypeRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentTypesActivity extends UserAndExamDetailsBaseActivity<StudentType, StudentTypeRequest> {
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected UserAndExamDetailsCommonApi<StudentType, StudentTypeRequest> createApi() {
        StudentTypeApi studentTypeApi = RetrofitInstance.getRetrofitInstance(this).create(StudentTypeApi.class);
        return new StudentTypeApiImpl(studentTypeApi);
    }

    @Override
    protected int getClickedItemId(StudentType item) {
        return item.getId();
    }

    @Override
    protected StudentTypeRequest createRequestObject(List<String> items) {
        String studentType = items.get(0);
        // no special validation
        validationErrorList.add(null);
        return new StudentTypeRequest(studentType);
    }

    @Override
    protected String itemCreatedMessage() {
        return "Student type added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "Student type deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Student type updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Student Types";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Student Type";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Student Types";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new student type by going to the 'Add Student Type' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve student types.";
    }

    @Override
    protected List<Class<?>> getRequestDataTypes() {
        return Arrays.asList(String.class);
    }

    @Override
    protected List<String> requestFieldValidationErrorList() {
        return validationErrorList;
    }

    @Override
    protected void clearValidationErrorList() { validationErrorList.clear();
    }

    @Override
    protected StudentType getChildModel() {
        return new StudentType();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }
}
