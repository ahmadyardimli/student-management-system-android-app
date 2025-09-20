package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubjectApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubjectApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Subject;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubjectRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectsActivity extends UserAndExamDetailsBaseActivity<Subject, SubjectRequest> {
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected UserAndExamDetailsCommonApi<Subject, SubjectRequest> createApi() {
       SubjectApi subjectApi = RetrofitInstance.getRetrofitInstance(this).create(SubjectApi.class);
        return new SubjectApiImpl(subjectApi);
    }

    @Override
    protected int getClickedItemId(Subject item) {
        return item.getId();
    }

    @Override
    protected SubjectRequest createRequestObject(List<String> items) {
        String subject = items.get(0);
        validationErrorList.add(null);
        return new SubjectRequest(subject);
    }

    @Override
    protected String itemCreatedMessage() {
        return "Subject added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "Subject deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Subject updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Subjects";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Subjects";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Subjects";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new subject by going to the 'Add Subject' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve subjects.";
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
    protected Subject getChildModel() {
        return new Subject();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }
}
