package com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.teachers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.BaseAdminActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.SubjectsActivity;
import com.example.studentmanagementsystemandroidapp.adapters.StatusAdapter;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubjectApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubjectApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherCommunicationSenderStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherCommunicationSenderStatusApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserStatusApiImpl;
import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.data.StatusDataFetcher;
import com.example.studentmanagementsystemandroidapp.data.UserAndExamDetailsDataFetcher;
import com.example.studentmanagementsystemandroidapp.exceptions.RequestValidationException;
import com.example.studentmanagementsystemandroidapp.interfaces.SpecialSpinnerItemClickListener;
import com.example.studentmanagementsystemandroidapp.interfaces.data.DataFetchCallback;
import com.example.studentmanagementsystemandroidapp.interfaces.users.Status;
import com.example.studentmanagementsystemandroidapp.managers.userandexamdetails.UserAndExamDetailsCommonManager;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Subject;
import com.example.studentmanagementsystemandroidapp.models.users.Teacher;
import com.example.studentmanagementsystemandroidapp.models.users.TeacherCommunicationSenderStatus;
import com.example.studentmanagementsystemandroidapp.models.users.User;
import com.example.studentmanagementsystemandroidapp.models.users.UserStatus;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubjectRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.FullTeacherRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.TeacherRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.UserRequest;
import com.example.studentmanagementsystemandroidapp.utils.EmailValidator;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ScreenUtils;
import com.example.studentmanagementsystemandroidapp.utils.SubjectsDisplayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ValidationUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAndUpdateTeacherActivity extends BaseAdminActivity implements CustomSpinner.OnSpinnerEventsListener, SpecialSpinnerItemClickListener {
    private TextView usernameLabelTextView;
    private View usernameIncludeField;
    private TextView usernameValueTextView;
    private Button usernameUpdateButton;
    private final String USERNAME_LABEL = "Username";
    private TextView passwordLabelTextView;
    private View passwordIncludeField;
    private TextView passwordValueTextView;
    private Button passwordUpdateButton;
    private final String PASSWORD_LABEL = "Password";
    private TextView emailLabelTextView;
    private View emailIncludeField;
    private TextView emailValueTextView;
    private Button emailUpdateButton;
    private final String EMAIL_LABEL = "Email";
    private TextView statusLabelTextView;
    private View statusIncludeField;
    private TextView statusValueTextView;
    private Button statusUpdateButton;
    private final String STATUS_LABEL = "Status";
    private TextView createdAtLabelTextView;
    private View createdAtIncludeField;
    private TextView createdAtValueTextView;
    private final String CREATED_AT_LABEL = "Creation date";
    private TextView updatedAtLabelTextView;
    private View updatedAtIncludeField;
    private TextView updatedAtValueTextView;
    private final String UPDATED_AT_LABEL = "Last updated";
    private TextView nameLabelTextView;
    private View nameIncludeField;
    private TextView nameValueTextView;
    private Button nameUpdateButton;
    private final String NAME_LABEL = "Name";

    private TextView surnameLabelTextView;
    private View surnameIncludeField;
    private TextView surnameValueTextView;
    private Button surnameUpdateButton;
    private final String SURNAME_LABEL = "Surname";
    private TextView subjectLabelTextView;
    private View subjectIncludeField;
    private TextView subjectValueTextView;
    private Button subjectUpdateButton;
    private final String SUBJECT_LABEL = "Subject";

    private TextView communicationStatusLabelTextView;
    private View communicationStatusIncludeField;
    private TextView communicationStatusValueTextView;
    private Button communicationStatusUpdateButton;
    private final String COMMUNICATION_STATUS_LABEL = "Message Sending Permission";

    private final float LABEL_SIZE_PERCENTAGE = 0.02f;
    private final float VALUE_SIZE_PERCENTAGE = 0.015f;
    private float device_screen_size;

    private String id;
    private int teacherId;
    private String username;
    private String password = "Can not be seen";
    private String email;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String name;
    private String surname;
    private String subjects;
    private String communicationStatus;

    private UserAndExamDetailsCommonManager<Teacher, FullTeacherRequest> teacherItemManager;
    private UserAndExamDetailsCommonManager<User, UserRequest> userItemManager;
    private String emptyEmailTextMessage = "Not available";
    private String emptyEmailHintMessage = "Empty email";
    private ImageView eyeIcon;
    private boolean isPasswordVisible = false;
    private TextWatcher textWatcher;
    private View addRequestView;
    private TextView requestLabelTextView;
    private EditText requestValueEditText;
    private View dialogFieldErrorMessage;
    private CustomSpinner spinnerStatuses;
    private CustomSpinner spinnerTeacherCommunicationSenderStatuses;
    private TextView multiSelectView;
    private int dynamicContentPlaceholderHeight;
    private List<UserStatus> userStatuses;
    private List<TeacherCommunicationSenderStatus> teacherCommunicationSenderStatuses;
    private int dialogLabelTextSize;
    private boolean isTeacherRequest;
    private List<Subject> subjectList;
    private boolean selectedSubjects[];
    private List<Integer> selectedSubjectList = new ArrayList<>();
    private List<String> selectedSubjectNames = new ArrayList<>();
    private List<Integer> requestSubject_ids = new ArrayList<>();
    private List<String> teacherSubjectsFromDatabase = new ArrayList<>();
    private Intent resultIntent = new Intent();
    private double spinnerItemTextSizePercentage = 0.03;
    private LoadingOverlayUtils loadingOverlayUtils;
    private ViewGroup overlayTarget;

    // persistent Subjects dialog references
    private AlertDialog openSubjectsDialog;
    private LinearLayout openSubjectsCheckboxContainer;
    private View openSubjectsDialogErrorView;
    private static final int REQUEST_CODE_SUBJECTS = 1002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_update_teacher);
        setupNavigationAndActionBar("Teacher Details", this, "Admins", "Users", "User Details");
        loadingOverlayUtils = new LoadingOverlayUtils(this);
        overlayTarget = findViewById(android.R.id.content);
        dialogLabelTextSize = ScreenUtils.calculateHeightWithPercentage(this, 0.03);

        UserAndExamDetailsCommonApi<Teacher, FullTeacherRequest> teacherApi = createTeacherApi();
        teacherItemManager = new UserAndExamDetailsCommonManager<>(teacherApi, this);

        // retrieve the Teacher object from the intent
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            teacherId = intent.getIntExtra("teacherId", -1);
            username = intent.getStringExtra("username");
            email = intent.getStringExtra("email");
            status = intent.getStringExtra("status");
            createdAt = intent.getStringExtra("createdAt");
            updatedAt = intent.getStringExtra("updatedAt");
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            subjects = intent.getStringExtra("subjects");
            communicationStatus = intent.getStringExtra("communicationStatus");
            teacherSubjectsFromDatabase = intent.getStringArrayListExtra("subjectList");
        }
        initUpdateComponents();
    }

    private void initUpdateComponents() {
        device_screen_size = ScreenUtils.getDeviceScreenHeight(ViewAndUpdateTeacherActivity.this);

        usernameIncludeField = findViewById(R.id.layout_view_and_update_user_item_username);
        usernameLabelTextView = usernameIncludeField.findViewById(R.id.user_view_update_item_label);
        usernameValueTextView = usernameIncludeField.findViewById(R.id.user_view_update_item_value);
        usernameUpdateButton = usernameIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(usernameUpdateButton, this,0.2, 0.04, 0.3);
        usernameLabelTextView.setText(USERNAME_LABEL);
        usernameValueTextView.setText(username);
        usernameLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        usernameValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        passwordIncludeField = findViewById(R.id.layout_view_and_update_user_item_password);
        passwordLabelTextView = passwordIncludeField.findViewById(R.id.user_view_update_item_label);
        passwordValueTextView = passwordIncludeField.findViewById(R.id.user_view_update_item_value);
        passwordUpdateButton = passwordIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(passwordUpdateButton, this,0.2, 0.04, 0.3);
        passwordLabelTextView.setText(PASSWORD_LABEL);
        passwordValueTextView.setText(password);
        passwordLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        passwordValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        emailIncludeField = findViewById(R.id.layout_view_and_update_user_item_email);
        emailLabelTextView = emailIncludeField.findViewById(R.id.user_view_update_item_label);
        emailValueTextView = emailIncludeField.findViewById(R.id.user_view_update_item_value);
        emailUpdateButton = emailIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(emailUpdateButton, this,0.2, 0.04, 0.3);
        emailLabelTextView.setText(EMAIL_LABEL);
        if (email == null)
            emailValueTextView.setText(emptyEmailTextMessage);
        else
            emailValueTextView.setText(email);
        emailLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        emailValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        statusIncludeField = findViewById(R.id.layout_view_and_update_user_item_user_status);
        statusLabelTextView = statusIncludeField.findViewById(R.id.user_view_update_item_label);
        statusValueTextView = statusIncludeField.findViewById(R.id.user_view_update_item_value);
        statusUpdateButton = statusIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(statusUpdateButton, this,0.2, 0.04, 0.3);
        statusLabelTextView.setText(STATUS_LABEL);
        statusValueTextView.setText(status);
        statusLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        statusValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        createdAtIncludeField = findViewById(R.id.layout_view_and_update_user_item_creation_time);
        createdAtLabelTextView = createdAtIncludeField.findViewById(R.id.user_view_update_item_label);
        createdAtValueTextView = createdAtIncludeField.findViewById(R.id.user_view_update_item_value);
        createdAtLabelTextView.setText(CREATED_AT_LABEL);
        createdAtValueTextView.setText(createdAt);
        createdAtIncludeField.findViewById(R.id.user_view_update_item_update_button).setVisibility(View.GONE);
        createdAtLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        createdAtValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        updatedAtIncludeField = findViewById(R.id.layout_view_and_update_user_item_update_time);
        updatedAtLabelTextView = updatedAtIncludeField.findViewById(R.id.user_view_update_item_label);
        updatedAtValueTextView = updatedAtIncludeField.findViewById(R.id.user_view_update_item_value);
        updatedAtLabelTextView.setText(UPDATED_AT_LABEL);
        updatedAtValueTextView.setText(updatedAt);
        updatedAtIncludeField.findViewById(R.id.user_view_update_item_update_button).setVisibility(View.GONE);
        updatedAtLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        updatedAtValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        nameIncludeField = findViewById(R.id.layout_view_and_update_user_item_name);
        nameLabelTextView = nameIncludeField.findViewById(R.id.user_view_update_item_label);
        nameValueTextView = nameIncludeField.findViewById(R.id.user_view_update_item_value);
        nameUpdateButton = nameIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(nameUpdateButton, this,0.2, 0.04, 0.3);
        nameLabelTextView.setText(NAME_LABEL);
        nameValueTextView.setText(name);
        nameLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        nameValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        surnameIncludeField = findViewById(R.id.layout_view_and_update_user_item_surname);
        surnameLabelTextView = surnameIncludeField.findViewById(R.id.user_view_update_item_label);
        surnameValueTextView = surnameIncludeField.findViewById(R.id.user_view_update_item_value);
        surnameUpdateButton = surnameIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(surnameUpdateButton, this,0.2, 0.04, 0.3);
        surnameLabelTextView.setText(SURNAME_LABEL);
        surnameValueTextView.setText(surname);
        surnameLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        surnameValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        subjectIncludeField = findViewById(R.id.layout_view_and_update_user_item_subject);
        subjectLabelTextView = subjectIncludeField.findViewById(R.id.user_view_update_item_label);
        subjectValueTextView = subjectIncludeField.findViewById(R.id.user_view_update_item_value);
        subjectUpdateButton = subjectIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(subjectUpdateButton, this,0.2, 0.04, 0.3);
        subjectLabelTextView.setText(SUBJECT_LABEL);
        subjectValueTextView.setText(subjects);
        subjectLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        subjectValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        communicationStatusIncludeField = findViewById(R.id.layout_view_and_update_user_item_communication_status);
        communicationStatusLabelTextView = communicationStatusIncludeField.findViewById(R.id.user_view_update_item_label);
        communicationStatusValueTextView = communicationStatusIncludeField.findViewById(R.id.user_view_update_item_value);
        communicationStatusUpdateButton = communicationStatusIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(communicationStatusUpdateButton, this,0.2, 0.04, 0.3);
        communicationStatusLabelTextView.setText(COMMUNICATION_STATUS_LABEL);
        communicationStatusValueTextView.setText(communicationStatus);
        communicationStatusLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * LABEL_SIZE_PERCENTAGE);
        communicationStatusValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,  device_screen_size * VALUE_SIZE_PERCENTAGE);

        usernameUpdateButton.setOnClickListener(view -> updateUserItemField(USERNAME_LABEL, usernameValueTextView.getText().toString()));
        passwordUpdateButton.setOnClickListener(view -> updateUserItemField(PASSWORD_LABEL, passwordValueTextView.getText().toString()));
        emailUpdateButton.setOnClickListener(view -> updateUserItemField(EMAIL_LABEL, emailValueTextView.getText().toString()));
        statusUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoading(true);
//                Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
                fetchUserStatuses();
            }
        });

        subjectUpdateButton.setOnClickListener(v -> {
            isTeacherRequest = true;
            setLoading(true);
//            Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchSubjects();
        });

        nameUpdateButton.setOnClickListener(v -> {
            isTeacherRequest = true;
            updateUserItemField(NAME_LABEL, nameValueTextView.getText().toString());
        });

        surnameUpdateButton.setOnClickListener(v -> {
            isTeacherRequest = true;
            updateUserItemField(SURNAME_LABEL, surnameValueTextView.getText().toString());
        });

        communicationStatusUpdateButton.setOnClickListener(v -> {
            isTeacherRequest = true;
            setLoading(true);
//            Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchCommunicationSenderStatuses();
        });
    }

    private void fetchSubjects(){
        SubjectApi subjectApi = RetrofitInstance.getRetrofitInstance(this).create(SubjectApi.class);
        UserAndExamDetailsDataFetcher<Subject, SubjectRequest> subjectsDataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new SubjectApiImpl(subjectApi));

        subjectsDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Subject>() {
            @Override
            public void onDataFetched(List<Subject> data) {
                subjectList = new ArrayList<>();
                if (data != null) subjectList.addAll(data);

                // 1) Sort first
                SubjectsDisplayUtils.sortSubjectsInPlace(subjectList);

                // 2) Build selection against the **sorted** list
                java.util.HashSet<String> selectedNames =
                        new java.util.HashSet<>(teacherSubjectsFromDatabase != null
                                ? teacherSubjectsFromDatabase
                                : new java.util.ArrayList<>());

                selectedSubjects = new boolean[subjectList.size()];
                for (int i = 0; i < subjectList.size(); i++) {
                    // If you have IDs, prefer checking IDs here
                    selectedSubjects[i] = selectedNames.contains(subjectList.get(i).getSubject());
                }

                setLoading(false);
                updateUserItemField(SUBJECT_LABEL, subjectValueTextView.getText().toString());
            }

            @Override public void onSingleItemFetched(Subject item) { }

            @Override
            public void onDataFetchFailed(Throwable t) {
                setLoading(false);
                Toast.makeText(ViewAndUpdateTeacherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                setLoading(false);
            }
        });
    }

    private void fetchUserStatuses() {
        UserStatusApi userStatusApi = RetrofitInstance.getRetrofitInstance(this).create(UserStatusApi.class);
        StatusDataFetcher<UserStatus> userStatusStatusDataFetcher = new StatusDataFetcher<>(this);

        userStatusStatusDataFetcher.getAllStatusesFromDatabase(new UserStatusApiImpl(userStatusApi), new DataFetchCallback<UserStatus>() {
            @Override
            public void onDataFetched(List<UserStatus> data) {
                userStatuses = new ArrayList<>();
                setLoading(false);
                userStatuses.addAll(data);
                updateUserItemField(STATUS_LABEL, statusValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(UserStatus item) {

            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                setLoading(false);
                Toast.makeText(ViewAndUpdateTeacherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                setLoading(false);
            }
        });
    }

    private void fetchCommunicationSenderStatuses(){
        TeacherCommunicationSenderStatusApi teacherCommunicationSenderStatusApi = RetrofitInstance.getRetrofitInstance(this).create(TeacherCommunicationSenderStatusApi.class);
        StatusDataFetcher<TeacherCommunicationSenderStatus> teacherStatusDataFetcher = new StatusDataFetcher<>(this);

        teacherStatusDataFetcher.getAllStatusesFromDatabase(new TeacherCommunicationSenderStatusApiImpl(teacherCommunicationSenderStatusApi), new DataFetchCallback<TeacherCommunicationSenderStatus>() {
            @Override
            public void onDataFetched(List<TeacherCommunicationSenderStatus> data) {
                teacherCommunicationSenderStatuses = new ArrayList<>();
                setLoading(false);
                teacherCommunicationSenderStatuses.addAll(data);
                updateUserItemField(COMMUNICATION_STATUS_LABEL, communicationStatusValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(TeacherCommunicationSenderStatus item) {

            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                setLoading(false);
                Toast.makeText(ViewAndUpdateTeacherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                setLoading(false);
            }
        });
    }

    private UserAndExamDetailsCommonApi<Teacher, FullTeacherRequest> createTeacherApi() {
        TeacherApi teacherApi = RetrofitInstance.getRetrofitInstance(this).create(TeacherApi.class);
        return new TeacherApiImpl(teacherApi);
    }

    private void updateUserItemField(String labelText, String value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // inflate the layout for the dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update_item, null);
        builder.setView(dialogView);

        requestValueEditText = null;
        dynamicContentPlaceholderHeight = getWindow().getDecorView().getRootView().getHeight();

        // calculate the height of EditText based on a percentage of dialog height
        int marginTop = ScreenUtils.calculateHeightWithPercentage(this, 0.02);
        int marginBottom = ScreenUtils.calculateHeightWithPercentage(this, 0.02);
        int marginLeft = ScreenUtils.calculateWidthWithPercentage(this, 0.02);
        int marginRight = ScreenUtils.calculateWidthWithPercentage(this, 0.02);

        // find the ConstraintLayout for the footer
        ConstraintLayout footerLayout = dialogView.findViewById(R.id.footerLayoutDialogUpdateItem);

        // set up additional elements in the footer ( Button click listeners)
        Button btnCancel = footerLayout.findViewById(R.id.btnCancel);
        Button btnUpdate = footerLayout.findViewById(R.id.btnUpdate);

        ViewStyler.setButtonSize(btnCancel, this,0.15, 0.035, 0.27);
        ViewStyler.setButtonSize(btnUpdate, this,0.15, 0.035, 0.27);

        ViewGroup.MarginLayoutParams layoutParamsFooter = (ViewGroup.MarginLayoutParams) footerLayout.getLayoutParams();
        layoutParamsFooter.topMargin = marginTop;
        layoutParamsFooter.bottomMargin = marginBottom;

        ConstraintLayout.LayoutParams layoutParamsBtnUpdate = (ConstraintLayout.LayoutParams) btnUpdate.getLayoutParams();
        layoutParamsBtnUpdate.leftMargin = marginLeft;

        ConstraintLayout customDialogLayout = dialogView.findViewById(R.id.custom_dialog_constraintLayout);
        customDialogLayout.setPadding(0, 0, 0, marginBottom);

        LinearLayout dynamicRequestItemContainer = dialogView.findViewById(R.id.dynamicFieldsContainerUpdate);
        AlertDialog updateDialog = builder.create();

        if (labelText.equals(PASSWORD_LABEL)){
            addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.update_password, dynamicRequestItemContainer, false);
            View passwordUpdateLayout = addRequestView.findViewById(R.id.passwordUpdateField);
            requestLabelTextView = addRequestView.findViewById(R.id.textViewAddItem);
            requestValueEditText = passwordUpdateLayout.findViewById(R.id.passwordEditText);
            eyeIcon = passwordUpdateLayout.findViewById(R.id.eyeIcon);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageAddItem);

            requestLabelTextView.setText(labelText);

            eyeIcon.setOnClickListener(v -> {
                // toggle the visibility state
                isPasswordVisible = !isPasswordVisible;
                updateEyeIconVisibility(requestValueEditText);
            });

            // set the text size for the EditText
            requestValueEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogLabelTextSize);
            requestLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogLabelTextSize);
        }
        else if (labelText.equals(STATUS_LABEL)){
            addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerStatuses = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);

            spinnerLabel.setText("Status");
            setUpStatusSpinner(spinnerStatuses, spinnerLabel, userStatuses, status);
            attachSpinnerClearErrorListener(spinnerStatuses, dialogFieldErrorMessage);
        }
        else if (labelText.equals(COMMUNICATION_STATUS_LABEL)){
            addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerTeacherCommunicationSenderStatuses = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Message Sending Permission");
            setUpStatusSpinner(spinnerTeacherCommunicationSenderStatuses, spinnerLabel, teacherCommunicationSenderStatuses, communicationStatus);
            attachSpinnerClearErrorListener(spinnerTeacherCommunicationSenderStatuses, dialogFieldErrorMessage);
        }
        else if (labelText.equals(SUBJECT_LABEL)){
            addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.multiselect_dropdown_item, dynamicRequestItemContainer, false);
            multiSelectView = addRequestView.findViewById(R.id.multi_select_dropdown_TextView);
            TextView multiSelectViewLabel = addRequestView.findViewById(R.id.textViewAddItem);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageMultiSelect);
            multiSelectViewLabel.setText("Change Subjects");
            multiSelectView.setHint("select");
            multiSelectView.setClickable(true);
            multiSelectView.setFocusable(false);
            multiSelectViewLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogLabelTextSize);
            ViewStyler.setTextViewStyle(multiSelectView, this, 0,0.025, 0, subjects, getWindow().getDecorView().getRootView());
            setTopMarginForIncludeLayout(multiSelectView, 0.015f);
            handleSubjectSelection();
        }
        else {
            addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.request_item, dynamicRequestItemContainer, false);
            requestLabelTextView = addRequestView.findViewById(R.id.textViewAddItem);
            requestValueEditText = addRequestView.findViewById(R.id.editTextItem);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageAddItem);
            // set the text size for the EditText
            requestValueEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogLabelTextSize);
            requestLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogLabelTextSize);

            // set attributes
            requestLabelTextView.setText(labelText);

            if (labelText.equals(EMAIL_LABEL)){
                if (emailValueTextView.getText().toString().equals(emptyEmailTextMessage))
                    requestValueEditText.setHint(emptyEmailHintMessage);
                else
                    requestValueEditText.setText(value);
            }
            else {
                requestValueEditText.setText(value);
            }
        }

        if (addRequestView.getParent() != null) {
            ((ViewGroup) addRequestView.getParent()).removeView(addRequestView);
        }
        dynamicRequestItemContainer.addView(addRequestView);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().trim();

                if (labelText.equals(NAME_LABEL) || labelText.equals(SURNAME_LABEL)) {
                    if (text.isEmpty()) {
                        showErrorMessage(dialogFieldErrorMessage, labelText + " cannot be empty.");
                    } else {
                        try {
                            // uses the same utility you have on the Student screen
                            ValidationUtils.validateNameOrSurname(text, labelText);
                            hideErrorMessage(dialogFieldErrorMessage);
                        } catch (RequestValidationException ex) {
                            showErrorMessage(dialogFieldErrorMessage, ex.getMessage());
                        }
                    }
                    return;
                }

                // email validate only if not empty
                if (labelText.equals(EMAIL_LABEL)) {
                    if (!text.isEmpty() && !EmailValidator.validateEmail(text)) {
                        showErrorMessage(dialogFieldErrorMessage, "Email is not in a valid format.");
                    } else {
                        hideErrorMessage(dialogFieldErrorMessage);
                        if (text.isEmpty()) requestValueEditText.setHint(emptyEmailHintMessage);
                    }
                    return;
                }

                // Default behavior (required vs optional)
                boolean isOptionalField = labelText.equals(EMAIL_LABEL);
                if (isOptionalField) {
                    hideErrorMessage(dialogFieldErrorMessage);
                } else {
                    if (text.isEmpty()) {
                        showErrorMessage(dialogFieldErrorMessage, labelText + " cannot be empty.");
                    } else {
                        hideErrorMessage(dialogFieldErrorMessage);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        if (!labelText.equals(STATUS_LABEL) && !labelText.equals(SUBJECT_LABEL) && !labelText.equals(COMMUNICATION_STATUS_LABEL))
            requestValueEditText.addTextChangedListener(textWatcher);

        // set the left margin for dynamicRequestItemContainer
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) dynamicRequestItemContainer.getLayoutParams();
        layoutParams.leftMargin = marginRight;
        layoutParams.rightMargin = marginRight;

        dynamicRequestItemContainer.setLayoutParams(layoutParams);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We'll always use the combined request now
                FullTeacherRequest fullReq = new FullTeacherRequest();
                TeacherRequest teacherRequest = null;
                UserRequest userRequest = null;

                // Create inner requests only when needed
                if (requestValueEditText != null) {
                    String updatedValue = requestValueEditText.getText().toString().trim();
                    if (updatedValue.isEmpty() && !labelText.equals(EMAIL_LABEL)) return;
                    // FINAL GUARD just like Student screen
                    if (labelText.equals(NAME_LABEL) || labelText.equals(SURNAME_LABEL)) {
                        if (updatedValue.isEmpty()) {
                            showErrorMessage(dialogFieldErrorMessage, labelText + " cannot be empty.");
                            return;
                        }
                        try {
                            ValidationUtils.validateNameOrSurname(updatedValue, labelText);
                        } catch (RequestValidationException ex) {
                            showErrorMessage(dialogFieldErrorMessage, ex.getMessage());
                            return;
                        }
                    }

                    // teacher fields
                    if (labelText.equals(NAME_LABEL)) {
//                        Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
                        teacherRequest = new TeacherRequest();
                        teacherRequest.setName(updatedValue);
                    } else if (labelText.equals(SURNAME_LABEL)) {
//                        Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
                        teacherRequest = new TeacherRequest();
                        teacherRequest.setSurname(updatedValue);
                    }
                    // USER FIELDS
                    else if (labelText.equals(USERNAME_LABEL)) {
//                        Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
                        userRequest = new UserRequest();
                        userRequest.setUsername(updatedValue);
                    } else if (labelText.equals(EMAIL_LABEL)) {
                        if (!EmailValidator.validateEmail(updatedValue) && !updatedValue.isEmpty()) {
                            showErrorMessage(dialogFieldErrorMessage, "Email is not in a valid format.");
                            return;
                        }
//                        Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
                        userRequest = new UserRequest();
                        userRequest.setEmail(updatedValue);
                    } else if (labelText.equals(PASSWORD_LABEL)) {
                        return;
                    }
                } else {
                    if (labelText.equals(STATUS_LABEL)) {
//                        Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
                        UserStatus selected = (UserStatus) spinnerStatuses.getSelectedItem();
                        if (selected != null) {
                            userRequest = new UserRequest();
                            userRequest.setStatusId(selected.getStatusId());
                        }
                    } else if (labelText.equals(COMMUNICATION_STATUS_LABEL)) {
//                        Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
                        TeacherCommunicationSenderStatus sel = (TeacherCommunicationSenderStatus) spinnerTeacherCommunicationSenderStatuses.getSelectedItem();
                        if (sel != null) {
                            teacherRequest = new TeacherRequest();
                            teacherRequest.setCommunicationSenderStatusId(sel.getStatusId());
                        }
                    } else if (labelText.equals(SUBJECT_LABEL)) {
                        if (multiSelectView.getText().toString().isEmpty()) return;
                        else if (multiSelectView.getText().toString().equals(formatSubjectsList(teacherSubjectsFromDatabase))) {
                            showErrorMessage(dialogFieldErrorMessage, "No changes were made.");
                            return;
                        }
//                        Toast.makeText(ViewAndUpdateTeacherActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
                        teacherRequest = new TeacherRequest();
                        teacherRequest.setSubject_ids(requestSubject_ids);
                    }
                }

                if (teacherRequest != null) {
                    teacherRequest.setUserId(Integer.parseInt(id));
                    fullReq.setTeacherRequest(teacherRequest);
                }
                if (userRequest != null) {
                    fullReq.setUserRequest(userRequest);
                }
                setBlockingLoading(true);
//                setLoading(true);
       // Single endpoint call (by teacherId)
                handleTeacherUpdateItemResponse(teacherId, fullReq, updateDialog, dialogFieldErrorMessage, labelText);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTeacherRequest = false;
                updateDialog.dismiss();
            }
        });

        updateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isTeacherRequest = false;
            }
        });

        updateDialog.show();
    }

    private void handleUnsuccessfulUpdateResponse(Response<?> response) {
        try {
            Log.d("unsuccessful", response.errorBody().toString());
            showErrorMessage(dialogFieldErrorMessage, response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private void handleSubjectSelection() {
    multiSelectView.setOnClickListener(v -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewAndUpdateTeacherActivity.this);
        View dialogView = LayoutInflater.from(ViewAndUpdateTeacherActivity.this)
                .inflate(R.layout.dialog_update_subjects, null);
        builder.setView(dialogView);

        // title styling
        TextView title = dialogView.findViewById(R.id.titleText);
        if (title != null) {
            title.setVisibility(View.VISIBLE);
            title.setText("Change subjects");
            ViewStyler.setTextSizeByScreenHeight(title, this, 0.035);
            int padH = ScreenUtils.calculateWidthWithPercentage(this, 0.04f);
            int padTop = ScreenUtils.calculateHeightWithPercentage(this, 0.016f);
            int padBottom = ScreenUtils.calculateHeightWithPercentage(this, 0.008f);
            title.setPadding(padH, padTop, padH, padBottom);
        }

        ConstraintLayout footerLayout = dialogView.findViewById(R.id.footerLayoutDialogUpdateItem);
        Button btnCancel = footerLayout.findViewById(R.id.btnCancel);
        Button btnOk     = footerLayout.findViewById(R.id.btnUpdate);
        Button btnManage = dialogView.findViewById(R.id.btnManageSubjects);
        LinearLayout checkboxContainer = dialogView.findViewById(R.id.checkboxContainer);
        View dialogSubjectErrorView    = dialogView.findViewById(R.id.errorMessageMultiSelect);

        ViewStyler.setButtonSize(btnCancel, this, 0.15, 0.033, 0.30);
        ViewStyler.setButtonSize(btnOk,     this, 0.15, 0.033, 0.30);
        ViewStyler.setButtonSize(btnManage, this, 0.30, 0.035, 0.30);

        // checkboxes and wire immediate error clearing on toggle
        checkboxContainer.removeAllViews();
        for (int i = 0; i < subjectList.size(); i++) {
            View cbView = LayoutInflater.from(this)
                    .inflate(R.layout.check_box_layout, checkboxContainer, false);
            CheckBox cb = cbView.findViewById(R.id.app_check_box);
            cb.setText(subjectList.get(i).getSubject());
            cb.setChecked(selectedSubjects[i]);
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> hideErrorMessage(dialogSubjectErrorView));
            checkboxContainer.addView(cbView);
        }

        // snapshot previous selection by SUBJECT IDs to be robust to reordering
        final java.util.HashSet<Integer> prevCheckedIds = new java.util.HashSet<>();
        for (int i = 0; i < selectedSubjects.length && i < subjectList.size(); i++) {
            if (selectedSubjects[i]) prevCheckedIds.add(subjectList.get(i).getItemId());
        }

        AlertDialog dlg = builder.create();
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(true);
        dlg.show();

        // keep references so we can refresh the dialog contents in place
        openSubjectsDialog = dlg;
        openSubjectsCheckboxContainer = checkboxContainer;
        openSubjectsDialogErrorView = dialogSubjectErrorView;
        dlg.setOnDismissListener(di -> {
            openSubjectsDialog = null;
            openSubjectsCheckboxContainer = null;
            openSubjectsDialogErrorView = null;
        });

        // size the dialog and cap scroll area height
        android.view.Window w = dlg.getWindow();
        if (w != null) {
            android.util.DisplayMetrics dm = getResources().getDisplayMetrics();
            int width = (int) (dm.widthPixels * 0.8f);
            w.setLayout(width, android.view.WindowManager.LayoutParams.WRAP_CONTENT);

            final int maxHeight = ScreenUtils.calculateHeightWithPercentage(this, 0.65f);
            final android.widget.ScrollView scrollSubjects = dialogView.findViewById(R.id.scrollSubjects);
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

        //  manage subjects without closing this dialog; refresh on return
        btnManage.setOnClickListener(v12 -> {
            Intent intent = new Intent(this, SubjectsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SUBJECTS);
        });

        btnOk.setOnClickListener(v13 -> {
            // read current states from the container
            selectedSubjectList.clear();
            requestSubject_ids.clear();
            selectedSubjectNames.clear();

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
                showErrorMessage(dialogSubjectErrorView, "Add subject");
                validateMultiSelectViewShowError(selectedSubjectList, dialogFieldErrorMessage);
                return;
            }

            if (nowCheckedIds.equals(prevCheckedIds)) {
                showErrorMessage(dialogSubjectErrorView, "No changes were made.");
                return;
            } else {
                hideErrorMessage(dialogSubjectErrorView);
            }

            StringBuilder sb = new StringBuilder();
            SubjectsDisplayUtils.sortSubjectsInPlace(subjectList);
            for (int idx : selectedSubjectList) {
                Subject s = subjectList.get(idx);
                requestSubject_ids.add(s.getItemId());
                selectedSubjectNames.add(s.getSubject());
                if (sb.length() > 0) sb.append(", ");
                sb.append(s.getSubject());
            }

            multiSelectView.setText(sb.toString());
            hideErrorMessage(dialogFieldErrorMessage);
            dlg.dismiss();
        });
    });
}

    public void refreshSubjectsDialogAsync() {
        if (openSubjectsDialog == null || !openSubjectsDialog.isShowing()) return;

        SubjectApi subjectApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(SubjectApi.class);

        UserAndExamDetailsDataFetcher<Subject, SubjectRequest> dataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new SubjectApiImpl(subjectApi));

        // Preserve currently-checked IDs
        java.util.HashSet<Integer> checkedIds = new java.util.HashSet<>();
        for (int i = 0; i < subjectList.size(); i++) {
            if (i < selectedSubjects.length && selectedSubjects[i]) {
                checkedIds.add(subjectList.get(i).getItemId());
            }
        }

        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<Subject>() {
            @Override
            public void onDataFetched(List<Subject> data) {
                subjectList = new ArrayList<>();
                if (data != null) subjectList.addAll(data);
                selectedSubjects = new boolean[subjectList.size()];

                if (openSubjectsCheckboxContainer != null
                        && openSubjectsDialog != null
                        && openSubjectsDialog.isShowing()) {

                    openSubjectsCheckboxContainer.removeAllViews();
                    for (int i = 0; i < subjectList.size(); i++) {
                        Subject subject = subjectList.get(i);
                        View cbView = LayoutInflater.from(ViewAndUpdateTeacherActivity.this)
                                .inflate(R.layout.check_box_layout, openSubjectsCheckboxContainer, false);
                        CheckBox checkBox = cbView.findViewById(R.id.app_check_box);
                        checkBox.setText(subject.getSubject());
                        boolean checked = checkedIds.contains(subject.getItemId());
                        checkBox.setChecked(checked);
                        selectedSubjects[i] = checked;

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
                Toast.makeText(ViewAndUpdateTeacherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) { }
        });
    }

    private void validateMultiSelectViewShowError(List<Integer> selectedItemList, View subjectsMultiSelectErrorMessageView){
        if (selectedItemList.isEmpty()){
            // fieldIsEmpty = true;
            showErrorMessage(subjectsMultiSelectErrorMessageView, "A subject is required.");
        } else {
            hideErrorMessage(subjectsMultiSelectErrorMessageView);
        }
    }

    private String formatSubjectsList(List<String> subjects) {
        StringBuilder formattedSubjects = new StringBuilder();

        for (int i = 0; i <  subjects.size(); i++){
            formattedSubjects.append(subjects.get(i));

            if (i < subjects.size() - 1)
                formattedSubjects.append(", ");
        }

        return formattedSubjects.toString();
    }

    private void handleTeacherUpdateItemResponse(int teacherId,
                                                 FullTeacherRequest fullRequest,
                                                 AlertDialog updateDialog,
                                                 View dialogFieldErrorMessage,
                                                 String labelText) {
        teacherItemManager.updateItem(teacherId, fullRequest, new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                setBlockingLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    Teacher t = response.body();
                    updateDialog.dismiss();

                    // common updatedAt
                    if (t.getUser() != null) {
                        updatedAtValueTextView.setText(t.getUser().getUpdatedAt());
                    }

                    // reflect UI depending on the field changed
                    switch (labelText) {
                        case NAME_LABEL:
                            nameValueTextView.setText(t.getName());
                            storeUpdateFieldsForPrevActivity(null, t.getName(), null, null);
                            break;
                        case SURNAME_LABEL:
                            surnameValueTextView.setText(t.getSurname());
                            storeUpdateFieldsForPrevActivity(null, null, t.getSurname(), null);
                            break;
                        case SUBJECT_LABEL:
                            teacherSubjectsFromDatabase.clear();

                            // IDs returned from backend
                            List<Integer> updatedIds = t.getSubject_ids();

                            if (updatedIds != null && !updatedIds.isEmpty()) {
                                // keep IDs for future requests
                                requestSubject_ids.clear();
                                requestSubject_ids.addAll(updatedIds);

                                // rebuild display names from IDs using subjectList
                                selectedSubjectNames.clear();
                                if (subjectList != null && !subjectList.isEmpty()) {
                                    for (Integer sid : updatedIds) {
                                        for (Subject s : subjectList) {
                                            if (s.getItemId() == sid) {
                                                selectedSubjectNames.add(s.getSubject());
                                                break;
                                            }
                                        }
                                    }
                                }

                                teacherSubjectsFromDatabase.addAll(selectedSubjectNames);
                                subjects = formatSubjectsList(selectedSubjectNames);
                                subjectValueTextView.setText(subjects);
                                storeUpdateFieldsForPrevActivity(null, null, null, selectedSubjectNames);
                            } else {
                                // fallback – nothing returned, keep what user selected
                                teacherSubjectsFromDatabase.addAll(selectedSubjectNames);
                                subjects = formatSubjectsList(selectedSubjectNames);
                                subjectValueTextView.setText(subjects);
                                storeUpdateFieldsForPrevActivity(null, null, null, selectedSubjectNames);
                            }
                            break;
                        case COMMUNICATION_STATUS_LABEL:
                            if (t.getCommunicationSenderStatus() != null) {
                                communicationStatus = t.getCommunicationSenderStatus().getStatusText();
                                communicationStatusValueTextView.setText(communicationStatus);
                            }
                            break;

                        // USER-part now also comes from full update:
                        case USERNAME_LABEL:
                            if (t.getUser() != null) {
                                usernameValueTextView.setText(t.getUser().getUsername());
                            }
                            break;
                        case EMAIL_LABEL:
                            if (t.getUser() != null) {
                                String em = t.getUser().getEmail();
                                emailValueTextView.setText(em == null ? "Not exist" : em);
                            }
                            break;
                        case STATUS_LABEL:
                            if (t.getUser() != null && t.getUser().getStatus() != null) {
                                status = t.getUser().getStatus().getStatusText();
                                statusValueTextView.setText(status);
                                storeUpdateFieldsForPrevActivity(t.getUser().getStatus(), null, null, null);
                            }
                            break;
                    }
                } else {
                    handleUnsuccessfulUpdateResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                setBlockingLoading(false);
                // optionally show a toast/log
            }
        });
    }

    private void setLoading(boolean loading) {
        if (isFinishing() || isDestroyed()) return;
        if (loading) {
            loadingOverlayUtils.showLayoutOverlay(overlayTarget);
            setUpdateButtonsEnabled(false);
        } else {
            loadingOverlayUtils.hideLayoutOverlay();
            setUpdateButtonsEnabled(true);
        }
    }

    private void setUpdateButtonsEnabled(boolean enabled) {
        if (usernameUpdateButton != null) usernameUpdateButton.setEnabled(enabled);
        if (passwordUpdateButton != null) passwordUpdateButton.setEnabled(enabled);
        if (emailUpdateButton != null) emailUpdateButton.setEnabled(enabled);
        if (statusUpdateButton != null) statusUpdateButton.setEnabled(enabled);
        if (nameUpdateButton != null) nameUpdateButton.setEnabled(enabled);
        if (surnameUpdateButton != null) surnameUpdateButton.setEnabled(enabled);
        if (subjectUpdateButton != null) subjectUpdateButton.setEnabled(enabled);
        if (communicationStatusUpdateButton != null) communicationStatusUpdateButton.setEnabled(enabled);
    }

    private <T extends Status> void setUpStatusSpinner(CustomSpinner spinner, TextView spinnerLabel, List<T> statusList, String status){
        StatusAdapter<T> statusAdapter = new StatusAdapter<>(this, statusList, dynamicContentPlaceholderHeight, spinnerItemTextSizePercentage, null, spinner);
        spinner.setAdapter(statusAdapter);
        
        int initialPosition = 0;

        for (int i = 0; i < statusList.size(); i++){
            if (status.equals(statusList.get(i).getStatusText())){
                initialPosition = i;
            }
        }

        spinner.setSelection(initialPosition);
        setUpBaseSpinner(spinner, spinnerLabel);
    }

    private void setUpBaseSpinner(CustomSpinner spinner, TextView spinnerLabel){
        spinner.setSpinnerEventsListener(this);
        spinnerLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogLabelTextSize);
        setDropDownVerticalOffset(spinner);
        setTopMarginForIncludeLayout(spinner, 0.015f);
    }

    private void setBlockingLoading(boolean show) {
        if (show) {
            loadingOverlayUtils.showActivityOverlay(this, /*dimBackground=*/true);
        } else {
            loadingOverlayUtils.hideActivityOverlay();
        }
    }

    private void attachSpinnerClearErrorListener(final CustomSpinner spinner, final View errorView) {
        if (spinner == null || errorView == null) return;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hideErrorMessage(errorView);   // instantly clear the error when a new item is picked
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no-op (or hideErrorMessage(errorView) if you prefer)
            }
        });
    }

    private void storeUpdateFieldsForPrevActivity(UserStatus updatedStatus, String updTeacherName, String updTeacherSurname, List<String> updatedTeacherSubjects){
        resultIntent.putExtra("teacherId", teacherId);

        if (updatedStatus != null){
            resultIntent.putExtra("updatedStatus", updatedStatus);
        }
        if (updTeacherName != null) {
            resultIntent.putExtra("updatedTeacherName", updTeacherName);
        }
        if (updTeacherSurname != null){
            resultIntent.putExtra("updatedTeacherSurname", updTeacherSurname);
        }
        if (updatedTeacherSubjects != null){
            ArrayList<String> arrayList = new ArrayList<>(updatedTeacherSubjects);
            resultIntent.putStringArrayListExtra("updatedTeacherSubjects", arrayList);
        }

        setResult(Activity.RESULT_OK, resultIntent);
    }

    private void setTopMarginForIncludeLayout(View includeLayout, float bottomMarginPercentage){
        // Calculate the bottom margin value
        int marginBottom = (int) (dynamicContentPlaceholderHeight * bottomMarginPercentage);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) includeLayout.getLayoutParams();
        layoutParams.topMargin = marginBottom;
        includeLayout.setLayoutParams(layoutParams);
    }

    private void updateEyeIconVisibility(EditText passwordEditText) {
        // Remove the TextWatcher temporarily to prevent it from triggering
        passwordEditText.removeTextChangedListener(textWatcher);

        int cursorPosition = passwordEditText.getSelectionStart();
        if (isPasswordVisible) {
            passwordEditText.setTransformationMethod(null);
            eyeIcon.setImageResource(R.drawable.eye_icon_active);
        } else {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            eyeIcon.setImageResource(R.drawable.eye_icon_disabled);
        }
        passwordEditText.addTextChangedListener(textWatcher);
        // Restore cursor position
        passwordEditText.setSelection(cursorPosition);
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
    public void onPopupWindowOpened(Spinner spinner) {
        if (spinner == spinnerStatuses) {
            spinnerStatuses.setBackground(this.getResources().getDrawable(R.drawable.background_request_spinner_active));
        } else if (spinner == spinnerTeacherCommunicationSenderStatuses) {
            spinnerTeacherCommunicationSenderStatuses.setBackground(this.getResources().getDrawable(R.drawable.background_request_spinner_active));
        }
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        if (spinner == spinnerStatuses) {
            spinnerStatuses.setBackground(this.getResources().getDrawable(R.drawable.background_request_spinner_inactive));
        } else if (spinner == spinnerTeacherCommunicationSenderStatuses) {
            spinnerTeacherCommunicationSenderStatuses.setBackground(this.getResources().getDrawable(R.drawable.background_request_spinner_inactive));
        }
    }

    @Override
    public void onSpecialItemClick(CustomSpinner currentSpinner) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SUBJECTS) {
            refreshSubjectsDialogAsync();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //returns immediately if dialog not showing
        refreshSubjectsDialogAsync();
    }
}