package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;
import android.app.Activity;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubGroupApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubGroupApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.SubGroup;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubGroupRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubGroupsActivity extends UserAndExamDetailsBaseActivity<SubGroup, SubGroupRequest>{
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected UserAndExamDetailsCommonApi<SubGroup, SubGroupRequest> createApi() {
        SubGroupApi subGroupApi = RetrofitInstance.getRetrofitInstance(this).create(SubGroupApi.class);
        return new SubGroupApiImpl(subGroupApi);
    }

    @Override
    protected int getClickedItemId(SubGroup item) {
        return item.getId();
    }

    @Override
    protected SubGroupRequest createRequestObject(List<String> items) {
        String subGroup = items.get(0);
        validationErrorList.add(null);
        return new SubGroupRequest(subGroup);
    }

    @Override
    protected String itemCreatedMessage() {
        return "Sub Group added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "Sub Group deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Successfully updated the existing Sub Group.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Sub Groups";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Sub Group";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Sub Groups";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return "You can add a new sub group from the 'Add Sub Group' section.";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to fetch sub groups.";
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
    protected SubGroup getChildModel() {
        return new SubGroup();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        super.onBackPressed();
    }
}