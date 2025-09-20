package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassNumberApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassNumberApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassNumber;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassNumberRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassNumbersActivity extends UserAndExamDetailsBaseActivity<ClassNumber, ClassNumberRequest> {
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected ClassNumberRequest createRequestObject(List<String> values) {
        int numberValue = Integer.parseInt(values.get(0));
        return new ClassNumberRequest(numberValue);
    }

    @Override
    protected List<String> requestFieldValidationErrorList() {
        return validationErrorList;
    }

    @Override
    protected void clearValidationErrorList() {
        validationErrorList.clear();
    }

    @Override
    protected ClassNumber getChildModel() {
        return new ClassNumber();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        // Show both update and delete buttons, for example
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }

    @Override
    protected List<Class<?>> getRequestDataTypes() {
        return Arrays.asList(Integer.class);
    }

    @Override
    protected UserAndExamDetailsCommonApi<ClassNumber, ClassNumberRequest> createApi() {
        ClassNumberApi classNumberApi = RetrofitInstance.getRetrofitInstance(this).create(ClassNumberApi.class);
        return new ClassNumberApiImpl(classNumberApi);
    }

    @Override
    protected int getClickedItemId(ClassNumber item) {
        return item.getId();
    }

    @Override
    protected String itemCreatedMessage() {
        return "Class number added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "Class number deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Class number updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Class Numbers";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Class Number";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Class Numbers";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new class number by going to the 'Add Class Number' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve class numbers.";
    }
}
