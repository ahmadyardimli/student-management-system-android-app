package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.CategoryApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.CategoryApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Category;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.CategoryRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoriesActivity extends UserAndExamDetailsBaseActivity<Category, CategoryRequest> {
    List<String> validationErrorList = new ArrayList<>();

    @Override
    protected CategoryRequest createRequestObject(List<String> values) {
        // Extract values from the list and create a CategoryRequest object
        String category = values.get(0);
        int minClass = Integer.parseInt(values.get(1));
        int maxClass = Integer.parseInt(values.get(2));

        validationErrorList.add(null);

        if (minClass < 1 || minClass > 11)
            validationErrorList.add("Starting class must be between 1 and 11.");
        else
            validationErrorList.add(null);

        if (maxClass < 1 || maxClass > 11)
            validationErrorList.add("Ending class must be between 1 and 11.");
        else
            validationErrorList.add(null);

        return new CategoryRequest(category, minClass, maxClass);
    }

    @Override
    protected List<String> requestFieldValidationErrorList(){
        return validationErrorList;
    }

    @Override
    protected void clearValidationErrorList() {
        if (validationErrorList != null) {
            validationErrorList.clear();
        }
    }

    @Override
    protected Category getChildModel() {
        return new Category();
    }

    @Override
    protected UserAndExamDetailsBtnVisibility getButtonVisibility() {
        return UserAndExamDetailsBtnVisibility.SHOW_BOTH;
    }

    @Override
    protected List<Class<?>> getRequestDataTypes() {
        return Arrays.asList(String.class, Integer.class, Integer.class);
    }

    @Override
    protected UserAndExamDetailsCommonApi<Category, CategoryRequest> createApi() {
        CategoryApi categoryApi = RetrofitInstance.getRetrofitInstance(this).create(CategoryApi.class);
        return new CategoryApiImpl(categoryApi);
    }

    @Override
    protected int getClickedItemId(Category item) {
        return item.getId();
    }

    @Override
    protected String itemCreatedMessage() {
        return "Category added: ";
    }


    @Override
    protected String itemDeletedMessage() {
        return "Category deleted successfully.";
    }


    @Override
    protected String itemSuccessfulUpdateMessage() {
        return "Category updated successfully.";
    }

    @Override
    protected String getActionBarTitle() {
        return "Categories";
    }

    @Override
    protected String btnAddItemText() {
        return "Add Category";
    }

    @Override
    protected String btnExistingItemsText() {
        return "Existing Categories";
    }

    @Override
    protected String getNoItemExistsMessage() {
        return " You can add a new category by going to the 'Add Category' section. ";
    }

    @Override
    protected String getIOExceptionErrorMessage() {
        return "Failed to retrieve categories.";
    }
}