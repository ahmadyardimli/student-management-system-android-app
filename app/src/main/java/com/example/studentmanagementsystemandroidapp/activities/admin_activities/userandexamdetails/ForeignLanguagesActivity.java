package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;
import android.util.Log;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ForeignLanguageApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ForeignLanguageApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ForeignLanguage;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ForeignLanguageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForeignLanguagesActivity extends UserAndExamDetailsBaseActivity<ForeignLanguage, ForeignLanguageRequest> {
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected UserAndExamDetailsCommonApi<ForeignLanguage, ForeignLanguageRequest> createApi() {
        ForeignLanguageApi foreignLanguageApi = RetrofitInstance.getRetrofitInstance(this).create(ForeignLanguageApi.class);
        return new ForeignLanguageApiImpl(foreignLanguageApi);
    }

    @Override
    protected ForeignLanguageRequest createRequestObject(List<String> values) {
        String forLanguage = values.get(0);
        Log.d("VAL 0 FOR AC", forLanguage);
        validationErrorList.add(null);
        return new ForeignLanguageRequest(forLanguage);
    }

    @Override
    protected String itemCreatedMessage() {
        return "New foreign language added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "Foreign language deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Foreign language updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Foreign Languages";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Foreign Language";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Foreign Languages";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new foreign language by going to the 'Add Foreign Language' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve foreign languages.";
    }

    @Override
    protected int getClickedItemId(ForeignLanguage item) {
        return item.getId();
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
    protected ForeignLanguage getChildModel() {
        return new ForeignLanguage();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }
}
