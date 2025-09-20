package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassLetterApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassLetterApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.exceptions.RequestValidationException;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassLetter;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassLetterRequest;
import com.example.studentmanagementsystemandroidapp.utils.ValidationUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassLettersActivity extends UserAndExamDetailsBaseActivity<ClassLetter, ClassLetterRequest> {
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected ClassLetterRequest createRequestObject(List<String> values) {
        String letterValue = values.get(0);

        try {
            ValidationUtils.validateSingleLetter(letterValue);
            // If validation passed, you can set no error
            validationErrorList.add(null);
        } catch (RequestValidationException ex) {
            // If validation fails, set the error message
            validationErrorList.add(ex.getMessage());
        }
        return new ClassLetterRequest(letterValue);
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
    protected ClassLetter getChildModel() {
        return new ClassLetter();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        // show both update and delete buttons
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }

    @Override
    protected List<Class<?>> getRequestDataTypes() {
        // expect a single field: letterValue
        return Arrays.asList(String.class);
    }

    @Override
    protected UserAndExamDetailsCommonApi<ClassLetter, ClassLetterRequest> createApi() {
        ClassLetterApi classLetterApi = RetrofitInstance.getRetrofitInstance(this).create(ClassLetterApi.class);
        return new ClassLetterApiImpl(classLetterApi);
    }

    @Override
    protected int getClickedItemId(ClassLetter item) {
        return item.getId();
    }

    @Override
    protected String itemCreatedMessage() {
        return "Class letter added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "Class letter deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Class letter updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Class Letters";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Class Letter";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Class Letters";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new class letter by going to the 'Add Class Letter' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve class letters.";
    }
}
