package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.GroupApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.GroupApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Group;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.GroupRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupsActivity extends UserAndExamDetailsBaseActivity<Group, GroupRequest> {
    private final List<String> validationErrorList = new ArrayList<>();

    @Override
    protected UserAndExamDetailsCommonApi<Group, GroupRequest> createApi() {
        GroupApi groupApi = RetrofitInstance.getRetrofitInstance(this).create(GroupApi.class);
        return new GroupApiImpl(groupApi);
    }

    @Override
    protected int getClickedItemId(Group item) {
        return item.getId();
    }

    @Override
    protected GroupRequest createRequestObject(List<String> items) {
        String group = items.get(0);
        validationErrorList.add(null);
        return new GroupRequest(group);
    }

    @Override
    protected String itemCreatedMessage() {
        return "Group added: ";
    }

    @Override
    protected String itemDeletedMessage() {
        return "Group deleted successfully.";
    }

    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Group updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Groups";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Group";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Groups";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new group by going to the 'Add Group' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve groups.";
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
    protected Group getChildModel() {
        return new Group();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }
}
