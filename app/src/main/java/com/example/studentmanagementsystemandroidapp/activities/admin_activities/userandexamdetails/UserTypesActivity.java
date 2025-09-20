package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApiImpl;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.UserType;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.users.UserTypeRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTypesActivity extends UserAndExamDetailsBaseActivity<UserType, UserTypeRequest>{
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected UserAndExamDetailsCommonApi<UserType, UserTypeRequest> createApi() {
        UserTypeApi userTypeApi = RetrofitInstance.getRetrofitInstance(this).create(UserTypeApi.class);
        return new UserTypeApiImpl(userTypeApi);
    }

    @Override
    protected int getClickedItemId(UserType item) {
        return item.getId();
    }

    @Override
    protected UserTypeRequest createRequestObject(List<String> values) {
        String userType = values.get(0).trim();
        // no validation
        validationErrorList.add(null);
        return new UserTypeRequest(userType);
    }

    @Override
    protected String itemCreatedMessage() {
        return "User type added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "User type deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "User type updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "User Types";
    }

    @Override
    protected String btnAddItemText() {
        return "Add User Type";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing User Types";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new user type by going to the 'Add User Type' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve user types.";
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
    protected void clearValidationErrorList() {
        validationErrorList.clear();
    }

    @Override
    protected UserType getChildModel() {
        return new UserType();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }
}
