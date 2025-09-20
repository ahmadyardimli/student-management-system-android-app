package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.BaseAdminActivity;
import com.example.studentmanagementsystemandroidapp.adapters.UserAndExamDetailsCommonAdapter;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.enums.UserAndExamDetailsBtnVisibility;
import com.example.studentmanagementsystemandroidapp.exceptions.RequestValidationException;
import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import com.example.studentmanagementsystemandroidapp.managers.userandexamdetails.UserAndExamDetailsCommonManager;
import com.example.studentmanagementsystemandroidapp.utils.KeyboardUtils;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ScreenUtils;
import com.example.studentmanagementsystemandroidapp.utils.ValidationUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class UserAndExamDetailsBaseActivity<Model extends RecyclerViewItemPositionable, Req> extends BaseAdminActivity implements UserAndExamDetailsCommonAdapter.OnUpdateClickListener {
    private Button btnAddItem;
    private Button btnExistingItems;
    private FrameLayout dynamicContentPlaceholder;
    private UserAndExamDetailsCommonManager<Model, Req> itemManager;
    private List<Model> modelItems;

    private int updateDialogPosition; // Variable to store the position of the item being updated
    private RecyclerView recyclerView;

    private enum ViewState { EXISTING_ITEMS, ADD_ITEM }
    private ViewState currentView = ViewState.EXISTING_ITEMS;

    private LoadingOverlayUtils loadingOverlayUtils;
    private boolean isDeleteRequestInProgress = false;
    private boolean isCreateRequestInProgress = false;
    private boolean isUpdateRequestInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_and_exam_details_base_activity);
        setupNavigationAndActionBar(getActionBarTitle(), this, "Admins", "Users", "User Details");

        // Handle back navigation for delete, create, and update in-progress states
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isDeleteRequestInProgress) {
                    Toast.makeText(UserAndExamDetailsBaseActivity.this, "Please wait until the delete operation is completed.", Toast.LENGTH_SHORT).show();
                } else if (isCreateRequestInProgress) {
                    Toast.makeText(UserAndExamDetailsBaseActivity.this, "Please wait until the create operation is completed.", Toast.LENGTH_SHORT).show();
                } else if (isUpdateRequestInProgress) {
                    Toast.makeText(UserAndExamDetailsBaseActivity.this, "Please wait until the update operation is completed.", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });

        btnExistingItems = findViewById(R.id.btnExistingItems);
        btnAddItem = findViewById(R.id.btnAddItem);
        dynamicContentPlaceholder = findViewById(R.id.dynamicContentPlaceholderLayout);

        // Set common layout's header buttons' (existing, add) text dynamically from child activity.
        setCommonLayoutButtonText(btnExistingItems, btnExistingItemsText());
        setCommonLayoutButtonText(btnAddItem, btnAddItemText());

        loadingOverlayUtils = new LoadingOverlayUtils(this);
        UserAndExamDetailsCommonApi<Model, Req> api = createApi();

        // Initialize the manager using the obtained API
        itemManager = new UserAndExamDetailsCommonManager<>(api, this);
        ViewStyler.setButtonSize(btnExistingItems, this, 0.4, 0.07, 0.25);
        ViewStyler.setButtonSize(btnAddItem, this, 0.4, 0.07, 0.25);

        // Initially show existing items view
        showExistingItemsView();

        Model model = getChildModel();
        btnAddItem.setOnClickListener(view -> {
//            if (modelItems != null) {
                showAddItemView(model);
//                modelItems = null;
//            }
        });
        btnExistingItems.setOnClickListener(view -> {
            showExistingItemsView();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingOverlayUtils.hideLayoutOverlay();
    }

    private void setCommonLayoutButtonText(Button button, String text) {
        button.setText(text);
    }

    private void showExistingItemsView() {
        currentView = ViewState.EXISTING_ITEMS;
        KeyboardUtils.hideKeyboard(this);
        btnExistingItems.setBackgroundResource(R.drawable.button_category_pressed); // Update button appearance for pressed state
        btnAddItem.setBackgroundResource(R.drawable.button_category_default);

        // Remove any existing views in the container
        dynamicContentPlaceholder.removeAllViews();

        // Inflate the layout_existing_items.xml and add it to the dynamic content placeholder
        View existingItemsView = LayoutInflater.from(this).inflate(R.layout.layout_existing_items, dynamicContentPlaceholder, false);

        btnExistingItems.setEnabled(false);

        // Show loading overlay
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);

        // Fetch the list of existing items from the backend
        itemManager.getAllItems(this, new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                // Hide loading overlay
                loadingOverlayUtils.hideLayoutOverlay();
                // Re-enable both buttons getting all items
                btnAddItem.setEnabled(true);
                btnExistingItems.setEnabled(true);

                if (currentView != ViewState.EXISTING_ITEMS) return; // Exit if the user has navigated to Add Item view
                if (response.isSuccessful()) {
                    modelItems = response.body();

                    // Add RecyclerView setup logic
                    recyclerView = existingItemsView.findViewById(R.id.recyclerViewItems);
                    recyclerView.setLayoutManager(new LinearLayoutManager(UserAndExamDetailsBaseActivity.this));

                    // Create and set the adapter
                    UserAndExamDetailsCommonAdapter<Model> adapter = new UserAndExamDetailsCommonAdapter<>(modelItems, R.layout.user_exam_details_adapter_item_design);
                    adapter.setOnUpdateClickListener(UserAndExamDetailsBaseActivity.this);
                    adapter.setButtonVisibility(getButtonVisibility()); // Set the visibility of button(s)
                    recyclerView.setAdapter(adapter);

                    dynamicContentPlaceholder.addView(existingItemsView);
                }
                else {
                    modelItems = new ArrayList<>();
                    TextView noItemMessage = existingItemsView.findViewById(R.id.noItemMessage);

                    // Build the message (server error on its own line, then your hint)
                    String serverMsg = null;
                    try {
                        serverMsg = response.errorBody() != null ? response.errorBody().string() : null;
                    } catch (IOException e) {
                        Toast.makeText(UserAndExamDetailsBaseActivity.this, getIOExceptionErrorMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    String finalMsg = (serverMsg == null ? "" : serverMsg + "\n\n") + getNoItemExistsMessage();
                    noItemMessage.setText(finalMsg);

                    // Show + style the empty state
                    noItemMessage.setVisibility(View.VISIBLE);
                    noItemMessage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    noItemMessage.setGravity(android.view.Gravity.CENTER);

                    // Use SP for text size (it don’t depend on container height)
                    noItemMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                    // Add margins & padding in dp
                    int marginPx  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
                    int paddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());

                    ViewGroup.LayoutParams baseLp = noItemMessage.getLayoutParams();
                    if (baseLp instanceof RelativeLayout.LayoutParams) {
                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) baseLp;
                        lp.setMargins(marginPx, marginPx, marginPx, marginPx);
                        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT; // let margins take effect across the width
                        noItemMessage.setLayoutParams(lp);
                    }
                    noItemMessage.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);

                    dynamicContentPlaceholder.addView(existingItemsView);
                    Log.e("UserAndExamDetailsBaseActivity", "Failed to get existing items: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                loadingOverlayUtils.hideLayoutOverlay();
                // Re-enable both buttons getting all items fail
                btnAddItem.setEnabled(true);
                btnExistingItems.setEnabled(true);

                Toast.makeText(UserAndExamDetailsBaseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showAddItemView(Model model) {
        currentView = ViewState.ADD_ITEM;
        btnAddItem.setBackgroundResource(R.drawable.button_category_pressed);
        btnExistingItems.setBackgroundResource(R.drawable.button_category_default);
        KeyboardUtils.hideKeyboard(this);

        // Remove any existing views in the container
        dynamicContentPlaceholder.removeAllViews();

        List<View> requestViewList = new ArrayList<>();
        List<EditText> requestInputEditTextList = new ArrayList<>();
        int margin = ScreenUtils.calculateHeightWithPercentage(this, 0.02);

        // Inflate the layout_add_item.xml and add it to the dynamic content placeholder
        View addItemView = LayoutInflater.from(this).inflate(R.layout.layout_add_item, dynamicContentPlaceholder, false);
        ScrollView addItemScrollView = addItemView.findViewById(R.id.addItemScrollView);
        LinearLayout dynamicRequestItemContainer = addItemView.findViewById(R.id.dynamicFieldsContainer);
        Button btnAddItem = addItemView.findViewById(R.id.buttonAddItem);
        List<RequestFieldInfo> requestFieldInfoList = model.getRequestFieldInfoForCreation();

        // Set starting point of add view item (5% from top of dynamicContentPlaceholder)
        float topMarginPercentage = 0.05f;
        int topMargin = (int) (dynamicContentPlaceholder.getHeight() * topMarginPercentage);
        ViewStyler.setAddItemViewStartWithPercentage(topMargin, addItemView);

        for (int i = 0; i < requestFieldInfoList.size(); i++) {
            View addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.request_item, dynamicRequestItemContainer, false);
            ViewGroup.MarginLayoutParams layoutParamsAddRequestView = (ViewGroup.MarginLayoutParams) addRequestView.getLayoutParams();
            layoutParamsAddRequestView.bottomMargin = margin;

            // Find views inside the inflated layout
            TextView requestLabelTextView = addRequestView.findViewById(R.id.textViewAddItem);
            final EditText requestValueEditText = addRequestView.findViewById(R.id.editTextItem);
            View fieldErrorMessage = addRequestView.findViewById(R.id.errorMessageAddItem);

            // Get RequestFieldInfo for the current field
            RequestFieldInfo currentFieldInfo = requestFieldInfoList.get(i);

            // Set styling for each view
            ViewStyler.setTextViewStyle(requestLabelTextView, this, 0.00, 0.03, 0.4, currentFieldInfo.getItemLabel(), dynamicContentPlaceholder);
            ViewStyler.setEditTextStyle(requestValueEditText, this, 0.00, 0.03, 0.4, currentFieldInfo.getItemValue(), ContextCompat.getColor(this, R.color.sms_yellow), dynamicContentPlaceholder);
            ViewStyler.setButtonSize(btnAddItem, this, 0.4, 0.06, 0.27);

            requestViewList.add(addRequestView);
            requestInputEditTextList.add(requestValueEditText);

            // Set an OnTouchListener for the ScrollView
            addItemScrollView.setOnTouchListener((v, event) -> {
                KeyboardUtils.hideKeyboard(this);
                return false; // Allow touch events to be passed to child views
            });

            // TextWatcher for real-time validation
            requestValueEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // No action needed before text changes
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // No action needed here
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String input = editable.toString().trim();
                    View errorView = fieldErrorMessage;

                    if (input.isEmpty()) {
                        // show error if input is empty
                        showErrorMessage(errorView, currentFieldInfo.getErrorMessage());
                    } else {
                        try {
                            ValidationUtils.validateSingleSpace(input);
                            // if validation passes, hide error message
                            hideErrorMessage(errorView);
                        } catch (RequestValidationException e) {
                            // if validation fails, show error message
                            showErrorMessage(errorView, e.getMessage());
                        }
                    }
                }
            });
        }

        for (View item : requestViewList) {
            addViewToContainer(dynamicRequestItemContainer, item);
        }
        dynamicContentPlaceholder.addView(addItemView);

        btnAddItem.setOnClickListener(v -> {
            // clear old validation errors so they don't carry over between attempts
            clearValidationErrorList();

            boolean hasError = false;
            boolean hasIncompatibleField = false;
            List<String> items = new ArrayList<>();
            List<Class<?>> dataTypes = getRequestDataTypes(); // Get the data types

            // for each field, hide old error messages and validate new input
            for (int i = 0; i < requestInputEditTextList.size(); i++) {
                EditText editText = requestInputEditTextList.get(i);
                String userInput = editText.getText().toString().trim();
                items.add(userInput);

                View errorView = requestViewList.get(i).findViewById(R.id.errorMessageAddItem);
                RequestFieldInfo currentFieldInfo = requestFieldInfoList.get(i);

                // always hide any previous error left over from an earlier attempt
                hideErrorMessage(errorView);

                if (userInput.isEmpty()) {
                    hasError = true;
                    showErrorMessage(errorView, currentFieldInfo.getErrorMessage());
                } else {
                    try {
                        ValidationUtils.validateSingleSpace(userInput);
                    } catch (RequestValidationException e) {
                        hasError = true;
                        showErrorMessage(errorView, e.getMessage());
                    }

                    // check if the input is convertible to the specified data type
                    if (!isConvertibleToDataType(userInput, dataTypes.get(i))) {
                        hasError = true;
                        hasIncompatibleField = true;
                        showErrorMessage(errorView, "A number must be entered.");
                    }
                }
            }

            // if any local errors were found, stop here
            if (hasError || hasIncompatibleField) {
                return;
            }

            // disable buttons & show loading overlay since local validations passed
            btnAddItem.setEnabled(false);
            btnExistingItems.setEnabled(false);
            loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);
            isCreateRequestInProgress = true; // marking creation in progress

            itemManager.createItem(
                    UserAndExamDetailsBaseActivity.this,
                    createRequestObject(items),
                    new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            // hide loading overlay
                            loadingOverlayUtils.hideLayoutOverlay();
                            // re-enable both buttons
                            btnAddItem.setEnabled(true);
                            btnExistingItems.setEnabled(true);
                            isCreateRequestInProgress = false;

                            if (response.isSuccessful()) {
                                Model createdItem = response.body();
                                if (createdItem != null) {
                                    String toastMessage = itemCreatedMessage() + createdItem.getItemName();
                                    Toast.makeText(UserAndExamDetailsBaseActivity.this, toastMessage, Toast.LENGTH_SHORT).show();

                                    // clear all EditText fields
                                    for (EditText editText : requestInputEditTextList) {
                                        editText.setText("");
                                    }
                                    // hide all error messages
                                    for (View view : requestViewList) {
                                        hideErrorMessage(view.findViewById(R.id.errorMessageAddItem));
                                    }
                                    clearValidationErrorList();
                                }
                            } else {
                                // if the server responded with an error status (like 400 or 409),
                                // let handleUnsuccessfulResponse show the new error
                                handleUnsuccessfulResponse(requestViewList, response);
                            }
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {
                            loadingOverlayUtils.hideLayoutOverlay();
                            btnAddItem.setEnabled(true);
                            btnExistingItems.setEnabled(true);
                            isCreateRequestInProgress = false; // Done

                            if (t.getMessage() != null) {
                                Toast.makeText(UserAndExamDetailsBaseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            Log.e("UserAndExamDetailsBaseActivity", "Failed to create item: " + t.getMessage());
                        }
                    }
            );
        });
    }

    private void addViewToContainer(LinearLayout container, View view) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout newViewContainer = new LinearLayout(container.getContext());
        newViewContainer.setLayoutParams(layoutParams);
        newViewContainer.addView(view);

        container.addView(newViewContainer);
    }

    private boolean isConvertibleToDataType(String input, Class<?> dataType) {
        try {
            if (dataType.equals(Integer.class)) {
                // check if the input is convertible to Integer
                Integer.parseInt(input);
            } else if (dataType.equals(String.class)) {
                // No conversion needed for String
                return true;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showErrorMessage(View errorView, String message) {
        TextView fieldErrorMessage = errorView.findViewById(R.id.errorMessageTextView);
        fieldErrorMessage.setText(message);
        errorView.setVisibility(View.VISIBLE);
        ViewStyler.setErrorMessageStyle(fieldErrorMessage, this, 0.4);
    }

    private void hideErrorMessage(View errorView) {
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateClick(int position) {
        updateDialogPosition = position;
        // Get the clicked item
        Model clickedItem = modelItems.get(position);
        // Create and show the update dialog
        showUpdateDialog(clickedItem);
    }

    @Override
    public void onDeleteClick(int position) {
        // Get the clicked item
        Model clickedItem = modelItems.get(position);
        // Disable both buttons during delete request
        btnAddItem.setEnabled(false);
        btnExistingItems.setEnabled(false);
        // Mark delete request as started
        Log.d("UserAndExamDetailsBaseActivity", "Setting isDeleteRequestInProgress to true.");
        isDeleteRequestInProgress = true;

        // Show loading overlay
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);

        // Call the deleteItem method in your manager
        itemManager.deleteItem(getClickedItemId(clickedItem), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadingOverlayUtils.hideLayoutOverlay();
                btnAddItem.setEnabled(true); // Re-enable btnAddItem after delete request completes
                btnExistingItems.setEnabled(true); // Re-enable btnExistingItem after delete request completes
                Log.d("UserAndExamDetailsBaseActivity", "Delete response received, setting isDeleteRequestInProgress to false.");
                isDeleteRequestInProgress = false; // Mark delete request as completed

                if (response.isSuccessful()) {
                    modelItems.remove(position);
                    // update positions in the list
                    updatePositions();
                    // refresh the adapter
                    recyclerView.getAdapter().notifyDataSetChanged();
                    Toast.makeText(UserAndExamDetailsBaseActivity.this, itemDeletedMessage(), Toast.LENGTH_SHORT).show();

                    if (modelItems.isEmpty()) {
                        showExistingItemsView();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loadingOverlayUtils.hideLayoutOverlay();
                // re-enable btnAddItem after delete request completes
                btnAddItem.setEnabled(true);
                btnExistingItems.setEnabled(true); // Re-enable btnExistingItem after delete request completes
                Log.d("UserAndExamDetailsBaseActivity", "Delete failed, setting isDeleteRequestInProgress to false.");
                isDeleteRequestInProgress = false; // Mark delete request as completed
                // handle the failure
                Log.e("UserAndExamDetailsBaseActivity", "Failed to delete item: " + t.getMessage());
                if (t.getMessage() != null) {
                    Toast.makeText(UserAndExamDetailsBaseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePositions() {
        for (int i = 0; i < modelItems.size(); i++) {
            // Update the position of each item in the list
            modelItems.get(i).setPosition(i);
            // Notify the adapter about the change in item positions
            recyclerView.getAdapter().notifyItemChanged(i);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showUpdateDialog(Model model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update_item, null);
        builder.setView(dialogView);

        // Calculate the height of EditText based on a percentage of dialog height
        int textSize = ScreenUtils.calculateHeightWithPercentage(this, 0.03);
        int marginTop = ScreenUtils.calculateHeightWithPercentage(this, 0.02);
        int marginBottom = ScreenUtils.calculateHeightWithPercentage(this, 0.02);
        int marginLeft = ScreenUtils.calculateWidthWithPercentage(this, 0.02);
        int marginRight = ScreenUtils.calculateWidthWithPercentage(this, 0.02);

        // Find the ConstraintLayout for the footer
        ConstraintLayout footerLayout = dialogView.findViewById(R.id.footerLayoutDialogUpdateItem);

        // Set up additional elements in the footer
        Button btnCancel = footerLayout.findViewById(R.id.btnCancel);
        Button btnUpdate = footerLayout.findViewById(R.id.btnUpdate);

        ViewStyler.setButtonSize(btnCancel, this, 0.15, 0.035, 0.27);
        ViewStyler.setButtonSize(btnUpdate, this, 0.15, 0.035, 0.27);

        ViewGroup.MarginLayoutParams layoutParamsFooter = (ViewGroup.MarginLayoutParams) footerLayout.getLayoutParams();
        layoutParamsFooter.topMargin = marginTop;
        layoutParamsFooter.bottomMargin = marginBottom;

        ConstraintLayout.LayoutParams layoutParamsBtnUpdate = (ConstraintLayout.LayoutParams) btnUpdate.getLayoutParams();
        layoutParamsBtnUpdate.leftMargin = marginLeft;

        ConstraintLayout customDialogLayout = dialogView.findViewById(R.id.custom_dialog_constraintLayout);
        customDialogLayout.setPadding(0, 0, 0, marginBottom);

        List<View> requestViewList = new ArrayList<>();
        List<EditText> requestInputEditTextList = new ArrayList<>();
        List<View> errorViews = new ArrayList<>();
        ScrollView addItemScrollView = dialogView.findViewById(R.id.scrollViewUpdateDialog);
        LinearLayout dynamicRequestItemContainer = dialogView.findViewById(R.id.dynamicFieldsContainerUpdate);
        AlertDialog updateDialog = builder.create();

        List<RequestFieldInfo> requestFieldLabelList = model.getRequestFieldInfo();

        for (int i = 0; i < requestFieldLabelList.size(); i++) {
            View addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.request_item, dynamicRequestItemContainer, false);
            TextView requestLabelTextView = addRequestView.findViewById(R.id.textViewAddItem);
            final EditText requestValueEditText = addRequestView.findViewById(R.id.editTextItem);
            View dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageAddItem);

            requestViewList.add(addRequestView);
            errorViews.add(dialogFieldErrorMessage);

            RequestFieldInfo currentFieldInfo = requestFieldLabelList.get(i);
            requestLabelTextView.setText(currentFieldInfo.getItemLabel());
            requestValueEditText.setText(currentFieldInfo.getItemValue());

            addItemScrollView.setOnTouchListener((v, event) -> {
                KeyboardUtils.hideKeyboard(this);
                return false;
            });

            // Set the text size for the EditText
            requestValueEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            requestLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

            // Set the left margin for dynamicRequestItemContainer
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) dynamicRequestItemContainer.getLayoutParams();
            layoutParams.leftMargin = marginRight;
            layoutParams.rightMargin = marginRight;

            dynamicRequestItemContainer.setLayoutParams(layoutParams);

            requestInputEditTextList.add(requestValueEditText);

            // TextWatcher for real-time validation
            requestValueEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // No action needed before text changes
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // No action needed here
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String input = editable.toString().trim();
                    View errorView = dialogFieldErrorMessage;

                    if (input.isEmpty()) {
                        // show error if input is empty
                        showErrorMessage(errorView, currentFieldInfo.getErrorMessage());
                    } else {
                        try {
                            // perform validation
                            ValidationUtils.validateSingleSpace(input);
                            // if validation passes, hide error message
                            hideErrorMessage(errorView);
                        } catch (RequestValidationException e) {
                            // if validation fails, show error message
                            showErrorMessage(errorView, e.getMessage());
                        }
                    }
                }
            });
        }

        for (View item : requestViewList) {
            addViewToContainer(dynamicRequestItemContainer, item);
        }

        btnUpdate.setOnClickListener(v -> {
            // clear old validation errors so they don't carry over
            clearValidationErrorList();

            // build the list of user inputs
            boolean hasError = false;
            List<String> items = new ArrayList<>();
            List<Class<?>> dataTypes = getRequestDataTypes();

            for (int i = 0; i < requestInputEditTextList.size(); i++) {
                EditText editText = requestInputEditTextList.get(i);
                String input = editText.getText().toString().trim();
                items.add(input);

                View errorView = requestViewList.get(i).findViewById(R.id.errorMessageAddItem);
                RequestFieldInfo currentFieldInfo = requestFieldLabelList.get(i);

                // clear old UI error from the field (in case it’s left from previous attempts)
                hideErrorMessage(errorView);

                // check empty
                if (input.isEmpty()) {
                    hasError = true;
                    showErrorMessage(errorView, currentFieldInfo.getErrorMessage());
                } else {
                    // local validations (single letter, single space, etc.)
                    try {
                        ValidationUtils.validateSingleSpace(input);
                        // possibly also validateSingleLetter(...) if needed
                    } catch (RequestValidationException e) {
                        hasError = true;
                        showErrorMessage(errorView, e.getMessage());
                    }

                    // check if input can be converted to the expected data type
                    if (!isConvertibleToDataType(input, dataTypes.get(i))) {
                        hasError = true;
                        showErrorMessage(errorView, "A number must be entered.");
                    }
                }
            }

            if (hasError) {
                // stop here if local validation failed
                return;
            }

            // if local validations pass, disable buttons and show loading
            btnAddItem.setEnabled(false);
            btnExistingItems.setEnabled(false);
            loadingOverlayUtils.showActivityOverlay(this, true);
            isUpdateRequestInProgress = true;

            // send the update request
            itemManager.updateItem(model.getItemId(), createRequestObject(items), new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    loadingOverlayUtils.hideActivityOverlay();
                    btnAddItem.setEnabled(true);
                    btnExistingItems.setEnabled(true);
                    isUpdateRequestInProgress = false;

                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(UserAndExamDetailsBaseActivity.this,
                                itemSuccessfulUpdateMessage(),
                                Toast.LENGTH_SHORT).show();
                        modelItems.set(updateDialogPosition, response.body());
                        recyclerView.getAdapter().notifyItemChanged(updateDialogPosition);
                        updateDialog.dismiss();
                        clearValidationErrorList();
                    } else {
                        // server error
                        handleUnsuccessfulResponse(requestViewList, response);
                    }
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    loadingOverlayUtils.hideActivityOverlay();
                    btnAddItem.setEnabled(true);
                    btnExistingItems.setEnabled(true);
                    isUpdateRequestInProgress = false;

                    if (t.getMessage() != null) {
                        Toast.makeText(UserAndExamDetailsBaseActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        btnCancel.setOnClickListener(v -> updateDialog.dismiss());
        updateDialog.show();
    }

    private void handleUnsuccessfulResponse(List<View> views, Response<Model> response) {
        boolean hasRequestValidationError = false;

        // if client-side validation was already stored, show it
        if (requestFieldValidationErrorList() != null && !requestFieldValidationErrorList().isEmpty()) {
            for (int i = 0; i < views.size(); i++) {
                String error = requestFieldValidationErrorList().get(i);
                if (error != null) {
                    hasRequestValidationError = true;
                    showErrorMessage(views.get(i).findViewById(R.id.errorMessageAddItem), error);
                }
            }
            clearValidationErrorList();
        }

        if (!hasRequestValidationError) {
            try {
                String serverError = response.errorBody().string();

                // place the server error into the first field's slot
                if (requestFieldValidationErrorList() != null && !views.isEmpty()) {
                    // If the list is empty, do add
                    if (requestFieldValidationErrorList().isEmpty()) {
                        requestFieldValidationErrorList().add(serverError);
                    } else {
                        requestFieldValidationErrorList().set(0, serverError);
                    }

                    showErrorMessage(views.get(0).findViewById(R.id.errorMessageAddItem), serverError);
                } else {
                    // fallback if there's no known field
                    Toast.makeText(this, serverError, Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected abstract UserAndExamDetailsCommonApi<Model, Req> createApi();

    protected abstract int getClickedItemId(Model item);

    protected abstract Req createRequestObject(List<String> items);

    protected abstract String itemCreatedMessage();

    protected abstract String itemDeletedMessage();

    protected abstract String itemSuccessfulUpdateMessage();

    protected abstract String getActionBarTitle();

    protected abstract String btnAddItemText();

    protected abstract String btnExistingItemsText();

    protected abstract String getNoItemExistsMessage();

    protected abstract String getIOExceptionErrorMessage();

    protected abstract List<Class<?>> getRequestDataTypes();

    protected abstract List<String> requestFieldValidationErrorList();

    protected abstract void clearValidationErrorList();

    protected abstract Model getChildModel();
    // Abstract method to define button visibility behavior
    protected abstract UserAndExamDetailsBtnVisibility getButtonVisibility();
}
