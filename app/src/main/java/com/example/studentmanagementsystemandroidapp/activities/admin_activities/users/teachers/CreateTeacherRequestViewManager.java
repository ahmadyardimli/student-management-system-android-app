package com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.teachers;

import static com.example.studentmanagementsystemandroidapp.utils.ButtonUtils.disableButtons;
import static com.example.studentmanagementsystemandroidapp.utils.ButtonUtils.enableButtons;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.SubjectsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.UserTypesActivity;
import com.example.studentmanagementsystemandroidapp.adapters.CommonSpinnerAdapter;
import com.example.studentmanagementsystemandroidapp.adapters.StatusAdapter;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubjectApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubjectApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherCommunicationSenderStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherCommunicationSenderStatusApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserStatusApiImpl;
import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.custom.spinners.SpinnerInfo;
import com.example.studentmanagementsystemandroidapp.data.StatusDataFetcher;
import com.example.studentmanagementsystemandroidapp.data.UserAndExamDetailsDataFetcher;
import com.example.studentmanagementsystemandroidapp.enums.SpinnerType;
import com.example.studentmanagementsystemandroidapp.exceptions.RequestValidationException;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import com.example.studentmanagementsystemandroidapp.interfaces.SpecialSpinnerItemClickListener;
import com.example.studentmanagementsystemandroidapp.interfaces.data.DataFetchCallback;
import com.example.studentmanagementsystemandroidapp.interfaces.users.Status;
import com.example.studentmanagementsystemandroidapp.managers.userandexamdetails.UserAndExamDetailsCommonManager;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Subject;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.UserType;
import com.example.studentmanagementsystemandroidapp.models.users.Teacher;
import com.example.studentmanagementsystemandroidapp.models.users.TeacherCommunicationSenderStatus;
import com.example.studentmanagementsystemandroidapp.models.users.UserStatus;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubjectRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.UserTypeRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.FullTeacherRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.TeacherRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.UserRequest;
import com.example.studentmanagementsystemandroidapp.utils.EmailValidator;
import com.example.studentmanagementsystemandroidapp.utils.KeyboardUtils;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ScreenUtils;
import com.example.studentmanagementsystemandroidapp.utils.SubjectsDisplayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ValidationUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTeacherRequestViewManager implements CustomSpinner.OnSpinnerEventsListener, SpecialSpinnerItemClickListener {
    private Context context;
    private Button btnExistingTeachers;
    private Button btnAddTeachers;
    private FrameLayout dynamicContentPlaceholder;
    private CustomSpinner spinnerStatuses;
    private CustomSpinner spinnerTeacherCommunicationSenderStatuses;
    private int dynamicContentPlaceholderHeight;
    private CustomSpinner spinnerUserTypes;
    private double spinnerItemTextSizePercentage = 0.03;
    private UserAndExamDetailsCommonManager<Teacher, FullTeacherRequest> teacherItemManager;
    private boolean[] selectedSubjects;
    private List<Integer> selectedSubjectList;
    private List<Subject> subjectList;
    private List<Integer> requestSubject_ids;
    private List<UserStatus> userStatuses;
    private List<TeacherCommunicationSenderStatus> teacherCommunicationSenderStatuses;
    private List<UserType> userTypes;
    private List<View> errorFields = new ArrayList<>();
    private boolean userStatusesAreFetched = false;
    private boolean teacherCommunicationSenderStatusesAreFetched = false;
    private boolean subjectsAreFetched = false;
    private boolean userTypesAreFetched = false;
    private boolean hasError = false;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private View usernameErrorMessageView;
    private View passwordErrorMessageView;
    private View emailErrorMessageView;
    private View teacherNameErrorMessageView;
    private View teacherSurnameErrorMessageView;
    private View subjectsMultiSelectErrorMessageView;
    private EditText emailEditText;
    private EditText teacherNameEditText;
    private EditText teacherSurnameEditText;
    private View firstErrorField;
    private ScrollView addItemScrollView;
    private View userTypeErrorMessageView;
    private Button btnAddItem;
    private TextView multiSelectView;
    private static final int REQUEST_CODE_USER_TYPES = 1001;
    private LoadingOverlayUtils loadingOverlayUtils;
    private int dialogLabelTextSize;
    private ArrayList<SpinnerInfo> requiredSpinners;
    private Map<String, Boolean> fieldCanBeEmptyMap = new HashMap<>();
    // add with fields
    private volatile int pageGen = 0;
    private int hostGen = -1;
    private volatile boolean cancelled = false;
    private AlertDialog openSubjectsDialog;
    private LinearLayout openSubjectsCheckboxContainer;
    private View openSubjectsDialogErrorView;
    public  static final int REQUEST_CODE_SUBJECTS = 1002;
    public CreateTeacherRequestViewManager(Context context, Button btnExistingTeachers, Button btnAddTeachers, FrameLayout dynamicContentPlaceholder) {
        this.context = context;
        this.btnExistingTeachers = btnExistingTeachers;
        this.btnAddTeachers = btnAddTeachers;
        this.dynamicContentPlaceholder = dynamicContentPlaceholder;
        this.selectedSubjectList = new ArrayList<>();
        this.subjectList = new ArrayList<>();
        this.requestSubject_ids = new ArrayList<>();
        this.userStatuses = new ArrayList<>();
        this.teacherCommunicationSenderStatuses = new ArrayList<>();
        this.userTypes = new ArrayList<>();
        this.loadingOverlayUtils = new LoadingOverlayUtils(context);
    }

    public void fetchDataAndShowAddTeacher(int gen) {
        hostGen = gen;
        cancelled = false;
        resetFetchedFlags();

        dynamicContentPlaceholder.removeAllViews();
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);

        fetchUserTypes(gen);
        fetchUserStatuses(gen);
        fetchTeacherCommunicationSenderStatuses(gen);
        fetchSubjects(gen);
    }

    public void cancelOngoingWork() {
        cancelled = true;
        hostGen = -1;
        loadingOverlayUtils.hideLayoutOverlay();
        enableButtons(btnAddTeachers, btnExistingTeachers);
    }

    private boolean isAlive(int gen) {
        return !cancelled && gen == hostGen;
    }

    private void resetFetchedFlags() {
        userStatusesAreFetched = false;
        teacherCommunicationSenderStatusesAreFetched = false;
        subjectsAreFetched = false;
        userTypesAreFetched = false;
    }

    private void fetchUserStatuses(final int guard) {
        UserStatusApi userStatusApi = RetrofitInstance.getRetrofitInstance(context).create(UserStatusApi.class);
        StatusDataFetcher<UserStatus> userStatusDataFetcher = new StatusDataFetcher<>(context);

        userStatusDataFetcher.getAllStatusesFromDatabase(new UserStatusApiImpl(userStatusApi), new DataFetchCallback<UserStatus>() {
            @Override
            public void onDataFetched(List<UserStatus> data) {
                if (!isAlive(guard)) return;
                userStatuses.clear();
                if (data != null) userStatuses.addAll(data);

                userStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(UserStatus item) {

            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                userStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                userStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchTeacherCommunicationSenderStatuses(final int guard) {
        TeacherCommunicationSenderStatusApi teacherCommunicationStatusApi = RetrofitInstance.getRetrofitInstance(context).create(TeacherCommunicationSenderStatusApi.class);
        StatusDataFetcher<TeacherCommunicationSenderStatus> teacherStatusDataFetcher = new StatusDataFetcher<>(context);

        teacherStatusDataFetcher.getAllStatusesFromDatabase(new TeacherCommunicationSenderStatusApiImpl(teacherCommunicationStatusApi), new DataFetchCallback<TeacherCommunicationSenderStatus>() {
            @Override
            public void onDataFetched(List<TeacherCommunicationSenderStatus> data) {
                if (!isAlive(guard)) return;
                teacherCommunicationSenderStatuses.clear();
                if (data != null) teacherCommunicationSenderStatuses.addAll(data);
                teacherCommunicationSenderStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(TeacherCommunicationSenderStatus item) {

            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                teacherCommunicationSenderStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                teacherCommunicationSenderStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchSubjects(final int guard) {
        SubjectApi subjectApi = RetrofitInstance.getRetrofitInstance(context).create(SubjectApi.class);
        UserAndExamDetailsDataFetcher<Subject, SubjectRequest> subjectsDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new SubjectApiImpl(subjectApi));

        subjectsDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Subject>() {
            @Override
            public void onDataFetched(List<Subject> data) {
                if (!isAlive(guard)) return;
                subjectList.clear();
                if (data != null) subjectList.addAll(data);
                SubjectsDisplayUtils.sortSubjectsInPlace(subjectList);
                selectedSubjects = new boolean[subjectList.size()];
                subjectsAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(Subject item) {

            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                subjectsAreFetched = true;
                checkAndShowAddItemView(guard);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                subjectsAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchUserTypes(final int guard) {
        UserTypeApi userTypeApi = RetrofitInstance.getRetrofitInstance(context).create(UserTypeApi.class);
        UserAndExamDetailsDataFetcher<UserType, UserTypeRequest> userTypesDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new UserTypeApiImpl(userTypeApi));

        userTypesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<UserType>() {
            @Override
            public void onDataFetched(List<UserType> data) {
                if (!isAlive(guard)) return;
                userTypes.clear();
                if(data != null){
                    userTypes.addAll(data);
                }
                userTypesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(UserType item) {

            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                userTypesAreFetched = true;
                checkAndShowAddItemView(guard);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                userTypesAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    public void updateUserTypes(List<UserType> userTypes) {
        // Remove the sleep call
        this.userTypes.clear();
        if (userTypes != null) {
            this.userTypes.addAll(userTypes);
        }
        if (spinnerUserTypes == null){
            Log.d("UT US NULL", "IS NULL");
        }
        updateSpinnerAdapter(spinnerUserTypes, this.userTypes, false);
    }


    private void checkAndShowAddItemView(int gen) {
        if (!isAlive(gen)) return;
        if (userStatusesAreFetched && teacherCommunicationSenderStatusesAreFetched &&
                subjectsAreFetched && userTypesAreFetched) {

            if (!isAlive(gen)) return;
            loadingOverlayUtils.hideLayoutOverlay();

            if (!isAlive(gen)) return;
            dynamicContentPlaceholder.removeAllViews();

            if (!isAlive(gen)) return;
            showAddItemView();
        }
    }

    private <T extends RecyclerViewItemPositionable> void updateSpinnerAdapter(CustomSpinner spinner, List<T> items, boolean allowEmptyOption) {
        // save the previously selected item's ID
        T previousSelectedItem = (T) spinner.getSelectedItem();
        Integer previousSelectedItemId = null;
        if (previousSelectedItem != null) {
            previousSelectedItemId = previousSelectedItem.getItemId();
        }

        // update the adapter
        CommonSpinnerAdapter<T> adapter = new CommonSpinnerAdapter<>(
                context,
                items,
                dynamicContentPlaceholderHeight,
                spinnerItemTextSizePercentage,
                this,
                spinner,
                allowEmptyOption,
                true,
                previousSelectedItemId
        );
        spinner.setAdapter(adapter);

        // restore the previous selection using the adapter's method
        int position = adapter.getPositionOfSelectedItem();
        if (position >= 0) {
            spinner.setSelection(position);
        } else if (allowEmptyOption) {
            spinner.setSelection(0); // select the empty option if allowed
        } else {
            spinner.setSelection(-1); // clear selection
        }

        // handle error messages and validation
        SpinnerInfo spinnerInfo = getSpinnerInfo(spinner);
        if (spinnerInfo != null) {
            View errorMessageView = spinnerInfo.getErrorMessageView();
            if (errorMessageView != null && !items.isEmpty()) {
                hideErrorMessage(errorMessageView);
            }

            if (spinnerInfo.isInitialized()) {
                validateSpinner(spinnerInfo);
            }
        }
    }

    private SpinnerInfo getSpinnerInfo(CustomSpinner spinner) {
        for (SpinnerInfo info : requiredSpinners) {
            if (info.getSpinner() == spinner) {
                return info;
            }
        }
        return null;
    }

    @SuppressLint({"ClickableViewAccessibility", "CutPasteId"})
    private void showAddItemView() {
        enableButtons(btnAddTeachers, btnExistingTeachers);
        KeyboardUtils.hideKeyboard(context);
        dynamicContentPlaceholder.removeAllViews();
        View addItemView = LayoutInflater.from(context).inflate(R.layout.layout_add_item, dynamicContentPlaceholder, false);
        addItemScrollView = addItemView.findViewById(R.id.addItemScrollView);
        LinearLayout dynamicRequestItemContainer = addItemView.findViewById(R.id.dynamicFieldsContainer);
        View addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.layout_teacher_request, dynamicRequestItemContainer, false);

        dynamicContentPlaceholderHeight = dynamicContentPlaceholder.getHeight();

        // set up username, password, and email and other fields
        setUpField(addRequestView, R.id.teacherUsernameInclude, "Create username *", "Username",0, "Username", false);
        usernameEditText = addRequestView.findViewById(R.id.teacherUsernameInclude).findViewById(R.id.editTextItem);
        usernameErrorMessageView = addRequestView.findViewById(R.id.teacherUsernameInclude).findViewById(R.id.errorMessageAddItem);

        setUpField(addRequestView, R.id.teacherPasswordInclude, "Create password *", "Password", 0.03f, "Password", false);
        passwordEditText = addRequestView.findViewById(R.id.teacherPasswordInclude).findViewById(R.id.editTextItem);
        passwordErrorMessageView = addRequestView.findViewById(R.id.teacherPasswordInclude).findViewById(R.id.errorMessageAddItem);

        setUpField(addRequestView, R.id.teacherEmailInclude, "Create Email (optional)", "Email", 0.03f, "Email", true);
        emailEditText = addRequestView.findViewById(R.id.teacherEmailInclude).findViewById(R.id.editTextItem);
        emailErrorMessageView = addRequestView.findViewById(R.id.teacherEmailInclude).findViewById(R.id.errorMessageAddItem);

        setUpField(addRequestView, R.id.teacherNameInclude, "Create Name *", "Name", 0.03f, "Name", false);
        teacherNameEditText = addRequestView.findViewById(R.id.teacherNameInclude).findViewById(R.id.editTextItem);
        teacherNameErrorMessageView = addRequestView.findViewById(R.id.teacherNameInclude).findViewById(R.id.errorMessageAddItem);
        setUpField(addRequestView, R.id.teacherSurnameInclude, "Create Surname *", "Surname", 0.03f, "Surname", false);
        teacherSurnameEditText = addRequestView.findViewById(R.id.teacherSurnameInclude).findViewById(R.id.editTextItem);
        teacherSurnameErrorMessageView = addRequestView.findViewById(R.id.teacherSurnameInclude).findViewById(R.id.errorMessageAddItem);

        // user type
        View userTypeInclude = addRequestView.findViewById(R.id.userTypeInclude);
        spinnerUserTypes = userTypeInclude.findViewById(R.id.requestSpinner);
        userTypeErrorMessageView = userTypeInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerUserTypes, userTypeInclude, userTypes, "User Type (select 'Teacher') *", SpinnerType.USER_TYPES, false);
        setSpinnerEmptyListener(spinnerUserTypes, userTypes, SpinnerType.USER_TYPES, false);

        // user status
        View userStatusInclude = addRequestView.findViewById(R.id.userStatusInclude);
        spinnerStatuses = userStatusInclude.findViewById(R.id.requestSpinner);
        setUpStatusSpinner(spinnerStatuses, userStatusInclude, userStatuses, "Status *");

        // teacher communication sender statuses
        View teacherSenderStatusInclude = addRequestView.findViewById(R.id.teacherCommunicationSenderStatusInclude);
        spinnerTeacherCommunicationSenderStatuses = teacherSenderStatusInclude.findViewById(R.id.requestSpinner);
        setUpStatusSpinner(spinnerTeacherCommunicationSenderStatuses, teacherSenderStatusInclude, teacherCommunicationSenderStatuses, "Message Sending Permission *");

        // Set starting point of add view item (5% from top of dynamicContentPlaceholder)
        float topMarginPercentage = 0.05f;
        int topMargin = (int) (dynamicContentPlaceholderHeight * topMarginPercentage);
        ViewStyler.setAddItemViewStartWithPercentage(topMargin, addItemView);

        // initialize the list
        requiredSpinners = new ArrayList<>();
        // For spinnerUserTypes
        requiredSpinners.add(new SpinnerInfo(spinnerUserTypes, userTypeErrorMessageView, "User type must be selected"));
        // Set up listeners for each required spinner
        for (SpinnerInfo spinnerInfo : requiredSpinners) {
            setUpSpinnerValidation(spinnerInfo);
        }
        // multi select dropdown
        View multiSelectSubjectsInclude = addRequestView.findViewById(R.id.teacherSubjectInclude);
        multiSelectView = multiSelectSubjectsInclude.findViewById(R.id.multi_select_dropdown_TextView);
        subjectsMultiSelectErrorMessageView = multiSelectSubjectsInclude.findViewById(R.id.errorMessageMultiSelect);

        multiSelectView.setHint("subject");
        ViewStyler.setTextViewStyle(multiSelectView, context, 0,0.03, 0.43, "subject", dynamicContentPlaceholder);
        TextView multiSelectViewLabel = multiSelectSubjectsInclude.findViewById(R.id.textViewAddItem);
        ViewStyler.setTextViewStyle(multiSelectViewLabel, context, 0,0.03, 0.43, "Select Subject *", dynamicContentPlaceholder);
        multiSelectView.setClickable(true);
        multiSelectView.setFocusable(false);
        multiSelectView.setFocusableInTouchMode(false);
        multiSelectView.setTextIsSelectable(false);
        multiSelectView.setLongClickable(false);

        setTopMarginForIncludeLayout(multiSelectSubjectsInclude, 0.02f);
        setTopMarginForIncludeLayout(multiSelectView, 0.015f);
        handleSubjectSelection();

        btnAddItem = addItemView.findViewById(R.id.buttonAddItem);

        dynamicRequestItemContainer.addView(addRequestView);
        dynamicContentPlaceholder.addView(addItemView);

        ViewStyler.setButtonSize(btnAddItem, context,0.43, 0.06, 0.27, 0.025, 0.025);

        // set an OnTouchListener for the ScrollView
        addItemScrollView.setOnTouchListener((v, event) -> {
            KeyboardUtils.hideKeyboard(context);
            return false; // allow touch events to be passed to child views
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBtnAddItem();
            }
        });
    }

    private void validateMultiSelectViewShowError(List<Integer> selectedItemList) {
        if (selectedItemList.isEmpty()) {
            showErrorMessage(subjectsMultiSelectErrorMessageView, "Please add a subject");
            hasError = true; // mark hasError if no subjects are selected
            errorFields.add(multiSelectView);
        } else {
            hideErrorMessage(subjectsMultiSelectErrorMessageView);
        }
    }

    private void setUpSpinnerValidation(SpinnerInfo spinnerInfo) {
        spinnerInfo.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerInfo.isInitialized()) {
                    validateSpinner(spinnerInfo);
                } else {
                    spinnerInfo.setInitialized(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (spinnerInfo.isInitialized()) {
                    validateSpinner(spinnerInfo);
                } else {
                    spinnerInfo.setInitialized(true);
                }
            }
        });
    }

    private void validateSpinner(SpinnerInfo spinnerInfo) {
        if (!spinnerInfo.isInitialized()) {
            return;
        }
        Object selectedItem = spinnerInfo.getSpinner().getSelectedItem();
        if (selectedItem == null) {
            showErrorMessage(spinnerInfo.getErrorMessageView(), spinnerInfo.getErrorMessage());
            hasError = true;
            errorFields.add(spinnerInfo.getSpinner());
        } else {
            // special validation for spinnerUserTypes needing to be "Student"
            if (spinnerInfo.getSpinner() == spinnerUserTypes) {
                UserType selectedUserType = (UserType) selectedItem;
                if (!selectedUserType.getType().trim().equalsIgnoreCase("Teacher")) {
                    showErrorMessage(spinnerInfo.getErrorMessageView(), "User type must be \"Teacher\"");
                    hasError = true;
                    errorFields.add(spinnerInfo.getSpinner());
                } else {
                    hideErrorMessage(spinnerInfo.getErrorMessageView());
                }
            } else {
                hideErrorMessage(spinnerInfo.getErrorMessageView());
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private <T> void setSpinnerEmptyListener(CustomSpinner spinner, List<T> detailList, SpinnerType spinnerType, boolean allowEmptyOption) {
        spinner.setOnTouchListener((v, event) -> {
            // if the spinner allows empty option or contains items, do not show dialog; just open the spinner
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (allowEmptyOption || !detailList.isEmpty()) {
                    spinner.performClick();
                    return true;
                } else {
                    // otherwise, show dialog if no items exist and empty option is not allowed
                    showEmptySpinnerDialog(spinnerType);
                    return true;
                }
            }
            return false;
        });
    }

    private void showEmptySpinnerDialog(SpinnerType spinnerType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_item, null);
        builder.setView(dialogView);

        int marginTop = ScreenUtils.calculateHeightWithPercentage(context, 0.015);
        int marginBottom = ScreenUtils.calculateHeightWithPercentage(context, 0.015);
        int marginLeft = ScreenUtils.calculateWidthWithPercentage(context, 0.02);

        ConstraintLayout footerLayout = dialogView.findViewById(R.id.footerLayoutDialogUpdateItem);
        Button btnCancel = footerLayout.findViewById(R.id.btnCancel);
        Button btnNavigate = footerLayout.findViewById(R.id.btnUpdate);

        ViewStyler.setButtonSize(btnCancel, context, 0, 0.045, 0.3);
        ViewStyler.setButtonSize(btnNavigate, context, 0, 0.045, 0.3);

        ViewGroup.MarginLayoutParams layoutParamsFooter = (ViewGroup.MarginLayoutParams) footerLayout.getLayoutParams();
        layoutParamsFooter.topMargin = marginTop;
        layoutParamsFooter.bottomMargin = marginBottom;

        ConstraintLayout.LayoutParams layoutParamsBtnUpdate = (ConstraintLayout.LayoutParams) btnNavigate.getLayoutParams();
        layoutParamsBtnUpdate.leftMargin = marginLeft;

        ConstraintLayout customDialogLayout = dialogView.findViewById(R.id.custom_dialog_constraintLayout);
        customDialogLayout.setPadding(0, 0, 0, marginBottom);

        LinearLayout dynamicRequestItemContainer = dialogView.findViewById(R.id.dynamicFieldsContainerUpdate);
        View addRequestView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_message, dynamicRequestItemContainer, false);
        AlertDialog updateDialog = builder.create();
        TextView dialogMessage = addRequestView.findViewById(R.id.dialogMessageTextView);

        ConstraintLayout customDialogMessageLayout = addRequestView.findViewById(R.id.custom_dialog_message_constraintLayout);
        double paddingPercentage = 0.02;
        int padding = ScreenUtils.calculatePaddingWithPercentage(context, paddingPercentage);
        customDialogMessageLayout.setPadding(padding, padding, padding, padding);

        String dialogMessageText = null;
        Intent intent = null;

        if (spinnerType == SpinnerType.USER_TYPES) {
            dialogMessageText = "User type is not available. It must be added for new student registration. Tap 'Add' to go to the 'User Type' page and add one.";
            intent = new Intent(context, UserTypesActivity.class);
        }

        dialogMessage.setText(dialogMessageText);
        dialogLabelTextSize = ScreenUtils.calculateHeightWithPercentage(context, 0.022);
        dialogMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogLabelTextSize);

        if (addRequestView.getParent() != null) {
            ((ViewGroup) addRequestView.getParent()).removeView(addRequestView);
        }
        dynamicRequestItemContainer.addView(addRequestView);
        btnNavigate.setText("Add");
        Intent finalIntent = intent;
        btnNavigate.setOnClickListener(v -> {
            if (finalIntent != null) {
                int requestCode = 0;
                if(spinnerType == SpinnerType.USER_TYPES) {
                    requestCode = REQUEST_CODE_USER_TYPES;
                } else {

                }

                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(finalIntent, requestCode);
                } else {
                    context.startActivity(finalIntent);
                }
            }
            updateDialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> updateDialog.dismiss());
        updateDialog.show();
    }

    private void handleBtnAddItem() {
        disableButtons(btnAddItem);
        hasError = false;
        firstErrorField = null;
        // perform all validations
        performAllValidations();
        // if any errors were detected during validation, exit early
        if (hasError) {
            hasError = false; // Reset hasError for the next attempt
            enableButtons(btnAddItem);

            // scroll to the first error field
            if (firstErrorField != null) {
                addItemScrollView.post(() -> {
                    // calculate the scroll position
                    int scrollToY = getRelativeTop(firstErrorField) - addItemScrollView.getHeight() / 2;
                    if (scrollToY < 0) scrollToY = 0;
                    addItemScrollView.smoothScrollTo(0, scrollToY);
                });
                // only request focus if the firstErrorField is focusable
                if (firstErrorField.isFocusable()) {
                    firstErrorField.requestFocus();
                }
            }
            return;
        }

        // show loading overlay
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);

        // create UserRequest and TeacherRequest
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(usernameEditText.getText().toString().trim());
        userRequest.setPassword(passwordEditText.getText().toString().trim());
        userRequest.setEmail(emailEditText.getText().toString().trim());

        UserType selectedUserType = (UserType) spinnerUserTypes.getSelectedItem();
        userRequest.setUserTypeId(selectedUserType.getItemId());

        UserStatus selectedStatus = (UserStatus) spinnerStatuses.getSelectedItem();
        userRequest.setStatusId(selectedStatus.getStatusId());

        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setName(teacherNameEditText.getText().toString().trim());
        teacherRequest.setSurname(teacherSurnameEditText.getText().toString().trim());

        TeacherCommunicationSenderStatus selectedSenderStatus = (TeacherCommunicationSenderStatus) spinnerTeacherCommunicationSenderStatuses.getSelectedItem();
        teacherRequest.setCommunicationSenderStatusId(selectedSenderStatus.getStatusId());
        teacherRequest.setSubject_ids(requestSubject_ids);

        // create the FullTeacherRequest with both UserRequest and TeacherRequest
        FullTeacherRequest fullTeacherRequest = new FullTeacherRequest(userRequest, teacherRequest);

        // create Teacher API manager and initiate API call
        TeacherApi teacherApi = RetrofitInstance.getRetrofitInstance(context).create(TeacherApi.class);
        teacherItemManager = new UserAndExamDetailsCommonManager<>(new TeacherApiImpl(teacherApi), context);

        teacherItemManager.createItem(context, fullTeacherRequest, new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if (!isAlive(hostGen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                enableButtons(btnAddItem);
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Teacher registration was successful.", Toast.LENGTH_SHORT).show();
                    clearNewUserAndTeacherRequestFields();
                } else {
                    enableButtons(btnAddItem);
                    handleUnsuccessfulResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                if (!isAlive(hostGen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                enableButtons(btnAddItem);
            }
        });
    }

    private void handleUnsuccessfulResponse(Response<?> response) {
        loadingOverlayUtils.hideLayoutOverlay();
        enableButtons(btnAddItem);

        try {
            if (response.errorBody() != null) {
                String errorMessage = response.errorBody().string();
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "An unknown error occurred.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to load error details.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearNewUserAndTeacherRequestFields() {
        // reset text fields
        usernameEditText.setText("");
        passwordEditText.setText("");
        emailEditText.setText("");
        teacherNameEditText.setText("");
        teacherSurnameEditText.setText("");

        // clear any error messages
        hideErrorMessage(usernameErrorMessageView);
        hideErrorMessage(passwordErrorMessageView);
        hideErrorMessage(emailErrorMessageView);
        hideErrorMessage(teacherNameErrorMessageView);
        hideErrorMessage(teacherSurnameErrorMessageView);
        hideErrorMessage(subjectsMultiSelectErrorMessageView);

        // reset spinners
        for (SpinnerInfo spinnerInfo : requiredSpinners) {
            resetSpinnerToDefault(spinnerInfo.getSpinner(), false);
            hideErrorMessage(spinnerInfo.getErrorMessageView());
            spinnerInfo.setInitialized(false);
        }

        // clear multi-select view
        clearMultiSelectView();

        // scroll back to the top
        addItemScrollView.post(() -> addItemScrollView.smoothScrollTo(0, 0));
    }

    private View getTopMostView(List<View> views) {
        View topMostView = null;
        int minPosition = Integer.MAX_VALUE;

        for (View view : views) {
            int position = getRelativeTop(view);
            if (position < minPosition) {
                minPosition = position;
                topMostView = view;
            }
        }
        return topMostView;
    }

    private int getRelativeTop(View view) {
        if (view.getParent() == addItemScrollView || view.getParent() == null) {
            return view.getTop();
        } else {
            return view.getTop() + getRelativeTop((View) view.getParent());
        }
    }

    private void clearMultiSelectView() {
        multiSelectView.setText("");
        selectedSubjectList.clear();
        requestSubject_ids.clear();
        for (int i = 0; i < selectedSubjects.length; i++){
            selectedSubjects[i] = false;
        }
    }

    private void resetSpinnerToDefault(CustomSpinner spinner, boolean allowEmptyOption) {
        if (spinner.getAdapter() == null || spinner.getAdapter().getCount() == 0) {
            // if the spinner has no items, set selection to -1 (no selection)
            spinner.setSelection(-1);
        } else {
            if (allowEmptyOption) {
                spinner.setSelection(0); // select the empty option
            } else {
                spinner.setSelection(0); // select the first item
            }
        }
    }

    private <T extends RecyclerViewItemPositionable> void setUpSpinner(CustomSpinner spinner, View includeLayout,
                                                                       List<T> itemList, String spinnerLabelText,
                                                                       SpinnerType spinnerType, boolean allowEmptyOption) {
        CommonSpinnerAdapter<T> adapter = new CommonSpinnerAdapter<>(
                context,
                itemList,
                dynamicContentPlaceholderHeight,
                spinnerItemTextSizePercentage,
                this,
                spinner,
                allowEmptyOption,
                true, null
        );

        spinner.setAdapter(adapter);
        setUpBaseSpinnerStyle(spinner, includeLayout, spinnerLabelText);
        spinner.setTag(spinnerType);
    }

    private <T extends Status> void setUpStatusSpinner(CustomSpinner spinner, View includeLayout, List<T> statusList, String spinnerLabelText){
        StatusAdapter<T> statusAdapter = new StatusAdapter<>(context, statusList, dynamicContentPlaceholderHeight, spinnerItemTextSizePercentage, null, spinner);
        spinner.setAdapter(statusAdapter);
        setUpBaseSpinnerStyle(spinner, includeLayout, spinnerLabelText);
    }

    private void setDropDownVerticalOffset(final CustomSpinner spinner) {
        // Add a global layout listener to wait until the view is laid out
        ViewTreeObserver vto = spinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Retrieve the height after the layout is calculated
                int spinnerHeight = spinner.getHeight();
                Log.d("Spinner height", String.valueOf(spinnerHeight));

                // Set the dropdown vertical offset
                spinner.setDropDownVerticalOffset(spinnerHeight);

                // Remove the listener to avoid multiple calls
                spinner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void handleSubjectSelection() {
    multiSelectView.setOnClickListener(v -> {
        SubjectsDisplayUtils.sortSubjectsInPlace(subjectList);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_subjects, null);
        builder.setView(dialogView);

        TextView title = dialogView.findViewById(R.id.titleText);
        if (title != null) {
            title.setVisibility(View.VISIBLE);
            title.setText("Pick subjects");
            ViewStyler.setTextSizeByScreenHeight(title, context, 0.035);
            int padH = ScreenUtils.calculateWidthWithPercentage(context, 0.04f);
            int padTop = ScreenUtils.calculateHeightWithPercentage(context, 0.016f);
            int padBottom = ScreenUtils.calculateHeightWithPercentage(context, 0.008f);
            title.setPadding(padH, padTop, padH, padBottom);
        }

        ConstraintLayout footerLayout = dialogView.findViewById(R.id.footerLayoutDialogUpdateItem);
        Button btnCancel = footerLayout.findViewById(R.id.btnCancel);
        Button btnOk     = footerLayout.findViewById(R.id.btnUpdate);
        Button btnManage = dialogView.findViewById(R.id.btnManageSubjects);
        LinearLayout checkboxContainer = dialogView.findViewById(R.id.checkboxContainer);
        View dialogSubjectErrorView    = dialogView.findViewById(R.id.errorMessageMultiSelect);

        ViewStyler.setButtonSize(btnCancel, context, 0.15, 0.033, 0.30);
        ViewStyler.setButtonSize(btnOk,     context, 0.15, 0.033, 0.30);
        ViewStyler.setButtonSize(btnManage, context, 0.30, 0.035, 0.30);

        // checkboxes (no external "allBoxes" list; read from container instead)
        checkboxContainer.removeAllViews();
        for (int i = 0; i < subjectList.size(); i++) {
            View cbView = LayoutInflater.from(context).inflate(R.layout.check_box_layout, checkboxContainer, false);
            CheckBox cb = cbView.findViewById(R.id.app_check_box);
            cb.setText(subjectList.get(i).getSubject());
            cb.setChecked(selectedSubjects[i]);
            // Clear error immediately when user changes any checkbox
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> hideErrorMessage(dialogSubjectErrorView));
            checkboxContainer.addView(cbView);
        }

        // snapshot previous selection by SUBJECT IDs (robust to reordering/refresh)
        final java.util.HashSet<Integer> prevCheckedIds = new java.util.HashSet<>();
        for (int i = 0; i < selectedSubjects.length && i < subjectList.size(); i++) {
            if (selectedSubjects[i]) prevCheckedIds.add(subjectList.get(i).getItemId());
        }

        AlertDialog dlg = builder.create();
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(true);
        dlg.show();

        // remember dialog + views so refreshSubjectsDialogAsync() can update in place
        openSubjectsDialog = dlg;
        openSubjectsCheckboxContainer = checkboxContainer;
        openSubjectsDialogErrorView = dialogSubjectErrorView;
        dlg.setOnDismissListener(di -> {
            openSubjectsDialog = null;
            openSubjectsCheckboxContainer = null;
            openSubjectsDialogErrorView = null;
        });

        Window w = dlg.getWindow();
        if (w != null) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int width = (int) (dm.widthPixels * 0.8f);
            w.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);

            final int maxHeight = ScreenUtils.calculateHeightWithPercentage(context, 0.65f);
            final ScrollView scrollSubjects = dialogView.findViewById(R.id.scrollSubjects);
            scrollSubjects.post(() -> {
                int contentH = (scrollSubjects.getChildCount() > 0)
                        ? scrollSubjects.getChildAt(0).getMeasuredHeight()
                        : 0;
                ViewGroup.LayoutParams lp = scrollSubjects.getLayoutParams();
                lp.height = contentH > maxHeight ? maxHeight : ViewGroup.LayoutParams.WRAP_CONTENT;
                scrollSubjects.setLayoutParams(lp);
            });
        }

        btnCancel.setOnClickListener(v1 -> dlg.dismiss());

        btnManage.setOnClickListener(v12 -> {
            // keep dialog open.  refresh eill happen checkboxes when come back
            Intent intent = new Intent(context, SubjectsActivity.class);
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_SUBJECTS);
            } else {
                context.startActivity(intent);
            }
        });

        btnOk.setOnClickListener(v13 -> {
            // read current states from the container (works even after refresh)
            selectedSubjectList.clear();
            requestSubject_ids.clear();
            java.util.HashSet<Integer> nowCheckedIds = new java.util.HashSet<>();

            for (int i = 0; i < checkboxContainer.getChildCount() && i < subjectList.size(); i++) {
                View row = checkboxContainer.getChildAt(i);
                CheckBox cb = row.findViewById(R.id.app_check_box);
                boolean checked = cb != null && cb.isChecked();
                if (i < selectedSubjects.length) {
                    selectedSubjects[i] = checked;
                }
                if (checked) {
                    selectedSubjectList.add(i);
                    nowCheckedIds.add(subjectList.get(i).getItemId());
                }
            }

            if (nowCheckedIds.isEmpty()) {
                showErrorMessage(dialogSubjectErrorView, "Please add a subject");
                validateMultiSelectViewShowError(selectedSubjectList);
                return;
            }

            // block confirm when nothing changed
            if (nowCheckedIds.equals(prevCheckedIds)) {
                showErrorMessage(dialogSubjectErrorView, "No changes were made.");
                return;
            } else {
                hideErrorMessage(dialogSubjectErrorView);
            }

            // apply changes to the form
            StringBuilder sb = new StringBuilder();
//            SubjectsDisplayUtils.sortSubjectsInPlace(subjectList);
            for (int idx : selectedSubjectList) {
                Subject s = subjectList.get(idx);
                requestSubject_ids.add(s.getItemId());
                if (sb.length() > 0) sb.append("\n");
                sb.append(s.getSubject());
            }
            multiSelectView.setText(sb.toString());
            hideErrorMessage(subjectsMultiSelectErrorMessageView);

            dlg.dismiss();
        });
    });
}
    public void refreshSubjectsDialogAsync() {
        if (openSubjectsDialog == null || !openSubjectsDialog.isShowing()) return;

        SubjectApi subjectApi = RetrofitInstance
                .getRetrofitInstance(context)
                .create(SubjectApi.class);

        UserAndExamDetailsDataFetcher<Subject, SubjectRequest> dataFetcher =
                new UserAndExamDetailsDataFetcher<>(context, new SubjectApiImpl(subjectApi));

        // Preserve currently-checked IDs (robust to changes)
        java.util.HashSet<Integer> checkedIds = new java.util.HashSet<>();
        for (int i = 0; i < subjectList.size(); i++) {
            if (i < selectedSubjects.length && selectedSubjects[i]) {
                checkedIds.add(subjectList.get(i).getItemId());
            }
        }

        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<Subject>() {
            @Override
            public void onDataFetched(List<Subject> data) {
                subjectList.clear();
                if (data != null) subjectList.addAll(data);
                SubjectsDisplayUtils.sortSubjectsInPlace(subjectList);
                selectedSubjects = new boolean[subjectList.size()];

                if (openSubjectsCheckboxContainer != null
                        && openSubjectsDialog != null
                        && openSubjectsDialog.isShowing()) {

                    openSubjectsCheckboxContainer.removeAllViews();
                    for (int i = 0; i < subjectList.size(); i++) {
                        Subject subject = subjectList.get(i);
                        View cbView = LayoutInflater.from(context)
                                .inflate(R.layout.check_box_layout, openSubjectsCheckboxContainer, false);
                        CheckBox checkBox = cbView.findViewById(R.id.app_check_box);
                        checkBox.setText(subject.getSubject());
                        boolean checked = checkedIds.contains(subject.getItemId());
                        checkBox.setChecked(checked);
                        selectedSubjects[i] = checked;

                        // Clear any dialog error as soon as user toggles a checkbox
                        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (openSubjectsDialogErrorView != null) {
                                hideErrorMessage(openSubjectsDialogErrorView);
                            }
                        });

                        openSubjectsCheckboxContainer.addView(cbView);
                    }
                }
            }

            @Override public void onSingleItemFetched(Subject item) {}

            @Override
            public void onDataFetchFailed(Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {}
        });
    }


    private void setUpField(View parentView, int fieldId, String label, String hint, float includeFieldTopMarginPercentage, String fieldName, boolean canBeEmpty) {
        View fieldInclude = parentView.findViewById(fieldId);
        TextView fieldLabel = fieldInclude.findViewById(R.id.textViewAddItem);
        EditText fieldValue = fieldInclude.findViewById(R.id.editTextItem);
        View errorMessageView = fieldInclude.findViewById(R.id.errorMessageAddItem);

        fieldCanBeEmptyMap.put(fieldName, canBeEmpty);

        ViewStyler.setTextViewStyle(fieldLabel, context, 0, 0.03, 0.43, label, dynamicContentPlaceholder);
        ViewStyler.setEditTextStyle(fieldValue, context, 0, 0.03, 0.43, hint, ContextCompat.getColor(context, R.color.sms_yellow), dynamicContentPlaceholder);
        setTopMarginForIncludeLayout(fieldInclude, includeFieldTopMarginPercentage);
        fieldValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String text = charSequence.toString().trim();
                if (text.isEmpty() && !canBeEmpty){
                    showErrorMessage(errorMessageView, "Enter " + fieldName + ".");
                } else {
                    hideErrorMessage(errorMessageView);
                    validateFieldAndShowError(fieldValue, errorMessageView, fieldName);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed
            }
        });
    }

    private void performAllValidations() {
        // reset error tracking at the beginning
        hasError = false;
        errorFields.clear();

        // validate individual fields
        validateFieldAndShowError(usernameEditText, usernameErrorMessageView, "Username");
        validateFieldAndShowError(passwordEditText, passwordErrorMessageView, "Password");
        validateFieldAndShowError(emailEditText, emailErrorMessageView, "Email");
        validateFieldAndShowError(teacherNameEditText, teacherNameErrorMessageView, "Name");
        validateFieldAndShowError(teacherSurnameEditText, teacherSurnameErrorMessageView, "Surname");

        // validate spinners
        for (SpinnerInfo spinnerInfo : requiredSpinners) {
            validateSpinner(spinnerInfo);
        }

        // perform multi-select validation (always last validation)
        validateMultiSelectViewShowError(selectedSubjectList);

        // determine the topmost error field
        if (!errorFields.isEmpty()) {
            firstErrorField = getTopMostView(errorFields);
        } else {
            firstErrorField = null;
        }
    }

    private void validateFieldAndShowError(EditText fieldValue, View errorMessageView, String fieldName) {
        String fieldText = fieldValue.getText().toString().trim();
        // get the canBeEmpty status for the field
        boolean canBeEmpty = Boolean.TRUE.equals(fieldCanBeEmptyMap.getOrDefault(fieldName, true));

        // check if the field is empty and cannot be empty
        if (fieldText.isEmpty() && !canBeEmpty) {
            showErrorMessage(errorMessageView, "Enter " + fieldName + ".");
            hasError = true;
            errorFields.add(fieldValue);
            return; // prevent upcoming validations
        } else {
            hideErrorMessage(errorMessageView);
        }

        switch (fieldName) {
            case "Name":
            case "Surname":
                try {
                    ValidationUtils.validateNameOrSurname(fieldText, fieldName);
                    hideErrorMessage(errorMessageView);
                } catch (RequestValidationException e) {
                    showErrorMessage(errorMessageView, e.getMessage());
                    hasError = true;
                    errorFields.add(fieldValue);
                }
                break;

            case "Email": // email validation
                if (!fieldText.isEmpty() && !EmailValidator.validateEmail(fieldText)) {
                    showErrorMessage(errorMessageView, "Email is not in a valid format.");
                    hasError = true;
                    errorFields.add(fieldValue);
                } else {
                    hideErrorMessage(errorMessageView);
                }
                break;

            default:
                // for other fields, no additional validation
                break;
        }
    }

    private void hideErrorMessage(View errorView) {
        errorView.setVisibility(View.GONE);
    }

    private void showErrorMessage(View errorView, String message) {
        TextView fieldErrorMessage = errorView.findViewById(R.id.errorMessageTextView);
        fieldErrorMessage.setText(message);
        errorView.setVisibility(View.VISIBLE);
        ViewStyler.setErrorMessageStyle(fieldErrorMessage, context, 0.4);
    }

    private void setUpBaseSpinnerStyle(CustomSpinner spinner, View includeLayout, String spinnerLabelText){
        spinner.setSpinnerEventsListener(this);
        ViewStyler.setSpinnerStyle(spinner, context, 0.001, 0.43, 0);
        TextView spinnerLabel = includeLayout.findViewById(R.id.requestSpinnerLabelTextView);
        ViewStyler
                .setTextViewStyle(spinnerLabel,
                        context,
                        0,
                        0.03,
                        0.43,
                        spinnerLabelText,
                        dynamicContentPlaceholder);

        setDropDownVerticalOffset(spinner);

        setTopMarginForIncludeLayout(includeLayout, 0.03f);
        setTopMarginForIncludeLayout(spinner, 0.015f);
    }

    private void setTopMarginForIncludeLayout(View includeLayout, float bottomMarginPercentage){
        // calculate the bottom margin value
        int marginBottom = (int) (dynamicContentPlaceholderHeight * bottomMarginPercentage);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) includeLayout.getLayoutParams();
        layoutParams.topMargin = marginBottom;
        includeLayout.setLayoutParams(layoutParams);
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        if (spinner == spinnerStatuses) {
            spinnerStatuses.setBackground(context.getResources().getDrawable(R.drawable.background_request_spinner_active));
        } else if (spinner == spinnerTeacherCommunicationSenderStatuses) {
            spinnerTeacherCommunicationSenderStatuses.setBackground(context.getResources().getDrawable(R.drawable.background_request_spinner_active));
        }
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        if (spinner == spinnerStatuses) {
            spinnerStatuses.setBackground(context.getResources().getDrawable(R.drawable.background_request_spinner_inactive));
        } else if (spinner == spinnerTeacherCommunicationSenderStatuses) {
            spinnerTeacherCommunicationSenderStatuses.setBackground(context.getResources().getDrawable(R.drawable.background_request_spinner_inactive));
        }
    }

    @Override
    public void onSpecialItemClick(CustomSpinner currentSpinner) {
        Log.d("On special item", "OUTSIDE CHECK");
        SpinnerType spinnerType = (SpinnerType) currentSpinner.getTag();
        if (spinnerType == SpinnerType.USER_TYPES){
            Log.d("On special item", "INSIDE CHECK");
            navigateToUserTypesPage();
        }
    }

    private void navigateToUserTypesPage() {
        Log.d("NAVIGATION UTYPES", "OUTSIDE CHECK");
        Intent intent = new Intent(context, UserTypesActivity.class);
        if (context instanceof Activity) {
            Log.d("NAVIGATION UTYPES", "INSIDE CHECK");
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_USER_TYPES);
        } else {
            context.startActivity(intent);
        }
    }
}