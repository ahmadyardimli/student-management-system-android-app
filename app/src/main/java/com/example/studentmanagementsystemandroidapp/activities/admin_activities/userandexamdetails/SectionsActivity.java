package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SectionApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SectionApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Section;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SectionRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionsActivity extends UserAndExamDetailsBaseActivity<Section, SectionRequest> {
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected UserAndExamDetailsCommonApi<Section, SectionRequest> createApi() {
        SectionApi sectionApi = RetrofitInstance.getRetrofitInstance(this).create(SectionApi.class);
        return new SectionApiImpl(sectionApi);
    }

    @Override
    protected int getClickedItemId(Section item) {
        return item.getId();
    }

    @Override
    protected SectionRequest createRequestObject(List<String> values) {
        String section = values.get(0);
        validationErrorList.add(null);
        return new SectionRequest(section);
    }

    @Override
    protected String itemCreatedMessage() {
        return "Section added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "Section deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Section updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Sections";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Sections";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Sections";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new section by going to the 'Add Section' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve sections.";
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
    protected Section getChildModel() {
        return new Section();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }
}

