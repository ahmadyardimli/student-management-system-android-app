package com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.students;

import static android.content.ContentValues.TAG;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.BaseAdminActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.SubGroupsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.SectionsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.CategoriesActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.ClassLettersActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.ClassNumbersActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.ForeignLanguagesActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.GroupsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.StudentTypesActivity;
import com.example.studentmanagementsystemandroidapp.adapters.CommonSpinnerAdapter;
import com.example.studentmanagementsystemandroidapp.adapters.StatusAdapter;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassLetterApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassLetterApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassNumberApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassNumberApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.StudentTypeApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.StudentTypeApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.GroupApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.GroupApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubGroupApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubGroupApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.CategoryApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.CategoryApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SectionApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SectionApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ForeignLanguageApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ForeignLanguageApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentCommunicationSenderStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentCommunicationSenderStatusApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserStatusApiImpl;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;
import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.data.StatusDataFetcher;
import com.example.studentmanagementsystemandroidapp.data.UserAndExamDetailsDataFetcher;
import com.example.studentmanagementsystemandroidapp.enums.SpinnerType;
import com.example.studentmanagementsystemandroidapp.exceptions.RequestValidationException;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import com.example.studentmanagementsystemandroidapp.interfaces.SpecialSpinnerItemClickListener;
import com.example.studentmanagementsystemandroidapp.interfaces.data.DataFetchCallback;
import com.example.studentmanagementsystemandroidapp.interfaces.users.Status;
import com.example.studentmanagementsystemandroidapp.managers.userandexamdetails.UserAndExamDetailsCommonManager;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassLetter;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassNumber;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Section;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.StudentType;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Group;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.SubGroup;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Category;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ForeignLanguage;
import com.example.studentmanagementsystemandroidapp.models.users.Student;
import com.example.studentmanagementsystemandroidapp.models.users.StudentCommunicationSenderStatus;
import com.example.studentmanagementsystemandroidapp.models.users.User;
import com.example.studentmanagementsystemandroidapp.models.users.UserStatus;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassLetterRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassNumberRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.StudentTypeRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.GroupRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubGroupRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.CategoryRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SectionRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ForeignLanguageRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.FullStudentRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.StudentRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.UserRequest;
import com.example.studentmanagementsystemandroidapp.utils.EmailValidator;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ScreenUtils;
import com.example.studentmanagementsystemandroidapp.utils.SortUtils;
import com.example.studentmanagementsystemandroidapp.utils.ValidationUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAndUpdateStudentActivity extends BaseAdminActivity
        implements CustomSpinner.OnSpinnerEventsListener, SpecialSpinnerItemClickListener {

    private View usernameIncludeField;
    private TextView usernameLabelTextView;
    private TextView usernameValueTextView;
    private Button usernameUpdateButton;
    private View passwordIncludeField;
    private TextView passwordLabelTextView;
    private TextView passwordValueTextView;
    private Button passwordUpdateButton;
    private View emailIncludeField;
    private TextView emailLabelTextView;
    private TextView emailValueTextView;
    private Button emailUpdateButton;
    private View statusIncludeField;
    private TextView statusLabelTextView;
    private TextView statusValueTextView;
    private Button statusUpdateButton;
    private View createdAtIncludeField;
    private TextView createdAtLabelTextView;
    private TextView createdAtValueTextView;
    private View updatedAtIncludeField;
    private TextView updatedAtLabelTextView;
    private TextView updatedAtValueTextView;
    private View nameIncludeField;
    private TextView nameLabelTextView;
    private TextView nameValueTextView;
    private Button nameUpdateButton;
    private View surnameIncludeField;
    private TextView surnameLabelTextView;
    private TextView surnameValueTextView;
    private Button surnameUpdateButton;
    private View studentCodeIncludeField;
    private TextView studentCodeLabelTextView;
    private TextView studentCodeValueTextView;
    private Button studentCodeUpdateButton;
    private View studentTypeIncludeField;
    private TextView studentTypeLabelTextView;
    private TextView studentTypeValueTextView;
    private Button studentTypeUpdateButton;
    private View groupIncludeField;
    private TextView groupLabelTextView;
    private TextView groupValueTextView;
    private Button groupUpdateButton;
    private View subGroupIncludeField;
    private TextView subGroupLabelTextView;
    private TextView subGroupValueTextView;
    private Button subGroupUpdateButton;
    private View categoryIncludeField;
    private TextView categoryLabelTextView;
    private TextView categoryValueTextView;
    private Button categoryUpdateButton;
    private View sectionIncludeField;
    private TextView sectionLabelTextView;
    private TextView sectionValueTextView;
    private Button sectionUpdateButton;
    private View foreignLanguageIncludeField;
    private TextView foreignLanguageLabelTextView;
    private TextView foreignLanguageValueTextView;
    private Button foreignLanguageUpdateButton;
    private View fatherNameIncludeField;
    private TextView fatherNameLabelTextView;
    private TextView fatherNameValueTextView;
    private Button fatherNameUpdateButton;
    private View mobilePhoneIncludeField;
    private TextView mobilePhoneLabelTextView;
    private TextView mobilePhoneValueTextView;
    private Button mobilePhoneUpdateButton;
    private View schoolClassCodeIncludeField;
    private TextView schoolClassCodeLabelTextView;
    private TextView schoolClassCodeValueTextView;
    private Button schoolClassCodeUpdateButton;
    private View addressIncludeField;
    private TextView addressLabelTextView;
    private TextView addressValueTextView;
    private Button addressUpdateButton;
    private View classNumberIncludeField;
    private TextView classNumberLabelTextView;
    private TextView classNumberValueTextView;
    private Button classNumberUpdateButton;
    private View communicationStatusIncludeField;
    private TextView communicationStatusLabelTextView;
    private TextView communicationStatusValueTextView;
    private Button communicationStatusUpdateButton;
    private View classLetterIncludeField;
    private TextView classLetterLabelTextView, classLetterValueTextView;
    private Button classLetterUpdateButton;

    private final String USERNAME_LABEL = "Username";
    private final String PASSWORD_LABEL = "Password";
    private final String EMAIL_LABEL = "Email";
    private final String STATUS_LABEL = "Status";
    private final String CREATED_AT_LABEL = "Created At";
    private final String UPDATED_AT_LABEL = "Last Updated";
    private final String NAME_LABEL = "Name";
    private final String SURNAME_LABEL = "Last Name";
    private final String STUDENT_CODE_LABEL = "Student Code";
    private final String STUDENT_TYPE_LABEL = "Student Type";
    private final String GROUP_LABEL = "Group";
    private final String SUB_GROUP_LABEL = "Subgroup";
    private final String CATEGORY_LABEL = "Category";
    private final String SECTION_LABEL = "Section";
    private final String FOREIGN_LANGUAGE_LABEL = "Foreign Language";
    private final String FATHER_NAME_LABEL = "Father's Name";
    private final String MOBILE_PHONE_LABEL = "Mobile Phone";
    private final String SCHOOL_CLASS_CODE_LABEL = "School Class Code";
    private final String ADDRESS_LABEL = "Address";
    private final String CLASS_NUMBER_LABEL = "Class Number";
    private final String CLASS_LETTER_LABEL = "Class Letter";
    private final String COMMUNICATION_STATUS_LABEL = "Message Sending Permission";
    private final String CATEGORY_CLASS_NUMBER_LABEL = "Category & Class Number";

    private int studentId;
    private int userId;
    private String username;
    private String email;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String name;
    private String surname;
    private String studentCode;
    private String studentType;
    private String group;
    private String subGroup;
    private String category;
    private String section;
    private String foreignLanguage;
    private String fatherName;
    private String mobilePhone;
    private String schoolClassCode;
    private String address;
    private String classNumber;
    private String classLetter;
    private String communicationStatus;
    private UserAndExamDetailsCommonManager<Student, FullStudentRequest> studentItemManager;
    private UserAndExamDetailsCommonManager<User, UserRequest> userItemManager;
    private final float LABEL_SIZE_PERCENTAGE = 0.02f;
    private final float VALUE_SIZE_PERCENTAGE = 0.015f;
    private float device_screen_size;
    private int dynamicContentPlaceholderHeight;
    private double spinnerItemTextSizePercentage = 0.03;
    private View dialogFieldErrorMessage;
    private CustomSpinner spinnerStatuses;
    private CustomSpinner spinnerStudentTypes;
    private CustomSpinner spinnerGroups;
    private CustomSpinner spinnerSubGroups;
    private CustomSpinner spinnerCategories;
    private CustomSpinner spinnerSections;
    private CustomSpinner spinnerForeignLanguages;
    private CustomSpinner spinnerClassNumbers;
    private CustomSpinner spinnerClassLetters;
    private CustomSpinner spinnerCommunicationSenderStatuses;

    private List<UserStatus> userStatuses;
    private List<StudentCommunicationSenderStatus> studentCommunicationSenderStatuses;
    private List<StudentType> studentTypesList;
    private List<Group> groupsList;
    private List<SubGroup> subGroupsList;
    private List<Category> categoriesList;
    private List<Section> sectionsList;
    private List<ForeignLanguage> foreignLanguagesList;
    private List<ClassNumber> classNumberList;
    private List<ClassLetter> classLettersList;

    private boolean isStudentRequest; // Distinguish whether we are updating Student or User
    private Intent resultIntent = new Intent();

    private String emptyEmailTextMessage = "Not exist";
    private String emptyEmailHintMessage = "Empty email";

    private int dialogLabelTextSize;

    private static final int REQUEST_CODE_STUDENT_TYPES = 1002;
    private static final int REQUEST_CODE_GROUPS = 1003;
    private static final int REQUEST_CODE_SUB_GROUPS = 1004;
    private static final int REQUEST_CODE_CATEGORIES = 1005;
    private static final int REQUEST_CODE_SECTIONS = 1006;
    private static final int REQUEST_CODE_FOREIGN_LANGUAGES = 1007;
    private static final int REQUEST_CODE_CLASS_NUMBERS = 1008;
    private static final int REQUEST_CODE_CLASS_LETTERS = 1009;

    private AlertDialog currentUpdateDialog;
    private AlertDialog emptySpinnerDialog;

    private final Object genLock = new Object();
    private int pageGen = 0;
    private final List<Call<?>> inFlight = new ArrayList<>();
    // Loading overlay
    private LoadingOverlayUtils loadingOverlayUtils;
    private View overlayHost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_update_student);
        loadingOverlayUtils = new LoadingOverlayUtils(this);
        overlayHost = findViewById(android.R.id.content);

        bumpGen();

        setupNavigationAndActionBar("Student Details", this, "Admins", "Users", "User Details");
        dialogLabelTextSize = ScreenUtils.calculateHeightWithPercentage(this, 0.03);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            this.studentId = intent.getIntExtra("studentId", -1);
            this.userId = intent.getIntExtra("id", -1);
            this.username = intent.getStringExtra("username");
            this.email = intent.getStringExtra("email");
            this.status = intent.getStringExtra("status");
            this.createdAt = intent.getStringExtra("createdAt");
            this.updatedAt = intent.getStringExtra("updatedAt");
            this.name = intent.getStringExtra("name");
            this.surname = intent.getStringExtra("surname");
            this.studentCode = intent.getStringExtra("studentCode");
            this.studentType = intent.getStringExtra("studentType");
            this.group = intent.getStringExtra("group");
            this.subGroup = intent.getStringExtra("subGroup");
            this.category = intent.getStringExtra("category");
            this.section = intent.getStringExtra("section");
            this.foreignLanguage = intent.getStringExtra("foreignLanguage");
            this.fatherName = intent.getStringExtra("fatherName");
            this.mobilePhone = intent.getStringExtra("mobilePhone");
            this.schoolClassCode = intent.getStringExtra("schoolClassCode");
            this.address = intent.getStringExtra("address");
            this.classNumber = intent.getStringExtra("classNumber");
            this.classLetter = intent.getStringExtra("classLetter");
            this.communicationStatus = intent.getStringExtra("communicationStatus");
        }

        // create manager for Students
        StudentApi studentApi = RetrofitInstance.getRetrofitInstance(this).create(StudentApi.class);
        studentItemManager = new UserAndExamDetailsCommonManager<>(new StudentApiImpl(studentApi), this);

        // create manager for Users (since username, email, status belong to the user)
        UserApi userApi = RetrofitInstance.getRetrofitInstance(this).create(UserApi.class);
        userItemManager = new UserAndExamDetailsCommonManager<>(new UserApiImpl(userApi), this);

        // init the UI
        initUpdateComponents();
    }

    private void initUpdateComponents() {
        device_screen_size = ScreenUtils.getDeviceScreenHeight(ViewAndUpdateStudentActivity.this);
        // username
        usernameIncludeField = findViewById(R.id.layout_view_and_update_user_item_username);
        usernameLabelTextView = usernameIncludeField.findViewById(R.id.user_view_update_item_label);
        usernameValueTextView = usernameIncludeField.findViewById(R.id.user_view_update_item_value);
        usernameUpdateButton = usernameIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(usernameUpdateButton, this, 0.2, 0.04, 0.3);
        usernameLabelTextView.setText(USERNAME_LABEL);
        usernameValueTextView.setText(username);
        usernameLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        usernameValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        usernameUpdateButton.setOnClickListener(v -> {
            isStudentRequest = false; // updating the "User"
            updateUserItemField(USERNAME_LABEL, usernameValueTextView.getText().toString());
        });

        passwordIncludeField = findViewById(R.id.layout_view_and_update_user_item_password);
        passwordLabelTextView = passwordIncludeField.findViewById(R.id.user_view_update_item_label);
        passwordValueTextView = passwordIncludeField.findViewById(R.id.user_view_update_item_value);
        passwordUpdateButton = passwordIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(passwordUpdateButton, this, 0.2, 0.04, 0.3);
        passwordLabelTextView.setText(PASSWORD_LABEL);
        passwordValueTextView.setText("Cannot be seen");
        passwordLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        passwordValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);
        passwordUpdateButton.setVisibility(View.GONE);

        // email
        emailIncludeField = findViewById(R.id.layout_view_and_update_user_item_email);
        emailLabelTextView = emailIncludeField.findViewById(R.id.user_view_update_item_label);
        emailValueTextView = emailIncludeField.findViewById(R.id.user_view_update_item_value);
        emailUpdateButton = emailIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(emailUpdateButton, this, 0.2, 0.04, 0.3);
        emailLabelTextView.setText(EMAIL_LABEL);
        if (email == null) {
            emailValueTextView.setText(emptyEmailTextMessage);
        } else {
            emailValueTextView.setText(email);
        }
        emailLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        emailValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        emailUpdateButton.setOnClickListener(v -> {
            isStudentRequest = false;
            updateUserItemField(EMAIL_LABEL, emailValueTextView.getText().toString());
        });

        // status
        statusIncludeField = findViewById(R.id.layout_view_and_update_user_item_user_status);
        statusLabelTextView = statusIncludeField.findViewById(R.id.user_view_update_item_label);
        statusValueTextView = statusIncludeField.findViewById(R.id.user_view_update_item_value);
        statusUpdateButton = statusIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(statusUpdateButton, this, 0.2, 0.04, 0.3);
        statusLabelTextView.setText(STATUS_LABEL);
        statusValueTextView.setText(status);
        statusLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        statusValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        statusUpdateButton.setOnClickListener(v -> {
            isStudentRequest = false;
            showLoading();
            fetchUserStatuses();
        });

        // createdAt (non-editable)
        createdAtIncludeField = findViewById(R.id.layout_view_and_update_user_item_creation_time);
        createdAtLabelTextView = createdAtIncludeField.findViewById(R.id.user_view_update_item_label);
        createdAtValueTextView = createdAtIncludeField.findViewById(R.id.user_view_update_item_value);
        createdAtIncludeField.findViewById(R.id.user_view_update_item_update_button).setVisibility(View.GONE); // Hide update button
        createdAtLabelTextView.setText(CREATED_AT_LABEL);
        createdAtValueTextView.setText(createdAt);
        createdAtLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        createdAtValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        // updatedAt (non-editable)
        updatedAtIncludeField = findViewById(R.id.layout_view_and_update_user_item_update_time);
        updatedAtLabelTextView = updatedAtIncludeField.findViewById(R.id.user_view_update_item_label);
        updatedAtValueTextView = updatedAtIncludeField.findViewById(R.id.user_view_update_item_value);
        updatedAtIncludeField.findViewById(R.id.user_view_update_item_update_button).setVisibility(View.GONE);
        updatedAtLabelTextView.setText(UPDATED_AT_LABEL);
        updatedAtValueTextView.setText(updatedAt);
        updatedAtLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        updatedAtValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        // name
        nameIncludeField = findViewById(R.id.layout_view_and_update_user_item_name);
        nameLabelTextView = nameIncludeField.findViewById(R.id.user_view_update_item_label);
        nameValueTextView = nameIncludeField.findViewById(R.id.user_view_update_item_value);
        nameUpdateButton = nameIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(nameUpdateButton, this, 0.2, 0.04, 0.3);
        nameLabelTextView.setText(NAME_LABEL);
        nameValueTextView.setText(name);
        nameLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        nameValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        nameUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            updateUserItemField(NAME_LABEL, nameValueTextView.getText().toString());
        });

        // surname
        surnameIncludeField = findViewById(R.id.layout_view_and_update_user_item_surname);
        surnameLabelTextView = surnameIncludeField.findViewById(R.id.user_view_update_item_label);
        surnameValueTextView = surnameIncludeField.findViewById(R.id.user_view_update_item_value);
        surnameUpdateButton = surnameIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(surnameUpdateButton, this, 0.2, 0.04, 0.3);
        surnameLabelTextView.setText(SURNAME_LABEL);
        surnameValueTextView.setText(surname);
        surnameLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        surnameValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        surnameUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            updateUserItemField(SURNAME_LABEL, surnameValueTextView.getText().toString());
        });

        // student code
        studentCodeIncludeField = findViewById(R.id.layout_view_and_update_user_item_student_code);
        studentCodeLabelTextView = studentCodeIncludeField.findViewById(R.id.user_view_update_item_label);
        studentCodeValueTextView = studentCodeIncludeField.findViewById(R.id.user_view_update_item_value);
        studentCodeUpdateButton = studentCodeIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(studentCodeUpdateButton, this, 0.2, 0.04, 0.3);
        studentCodeLabelTextView.setText(STUDENT_CODE_LABEL);
        studentCodeValueTextView.setText(studentCode);
        studentCodeLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        studentCodeValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        studentCodeUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            updateUserItemField(STUDENT_CODE_LABEL, studentCodeValueTextView.getText().toString());
        });

        // student yype
        studentTypeIncludeField = findViewById(R.id.layout_view_and_update_user_item_student_type);
        studentTypeLabelTextView = studentTypeIncludeField.findViewById(R.id.user_view_update_item_label);
        studentTypeValueTextView = studentTypeIncludeField.findViewById(R.id.user_view_update_item_value);
        studentTypeUpdateButton = studentTypeIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(studentTypeUpdateButton, this, 0.2, 0.04, 0.3);
        studentTypeLabelTextView.setText(STUDENT_TYPE_LABEL);
        studentTypeValueTextView.setText(studentType);
        studentTypeLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        studentTypeValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        studentTypeUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
//            Toast.makeText(ViewAndUpdateStudentActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchStudentTypes();
        });

        // Group
        groupIncludeField = findViewById(R.id.layout_view_and_update_user_item_group);
        groupLabelTextView = groupIncludeField.findViewById(R.id.user_view_update_item_label);
        groupValueTextView = groupIncludeField.findViewById(R.id.user_view_update_item_value);
        groupUpdateButton = groupIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(groupUpdateButton, this, 0.2, 0.04, 0.3);
        groupLabelTextView.setText(GROUP_LABEL);
        groupValueTextView.setText(group);
        groupLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        groupValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        groupUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
//            Toast.makeText(ViewAndUpdateStudentActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchGroups();
        });

        // SubGroup
        subGroupIncludeField = findViewById(R.id.layout_view_and_update_user_item_sub_group);
        subGroupLabelTextView = subGroupIncludeField.findViewById(R.id.user_view_update_item_label);
        subGroupValueTextView = subGroupIncludeField.findViewById(R.id.user_view_update_item_value);
        subGroupUpdateButton = subGroupIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(subGroupUpdateButton, this, 0.2, 0.04, 0.3);
        subGroupLabelTextView.setText(SUB_GROUP_LABEL);
        subGroupValueTextView.setText(subGroup);
        subGroupLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        subGroupValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        subGroupUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
//            Toast.makeText(ViewAndUpdateStudentActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchSubGroups();
        });

        // category
        categoryIncludeField = findViewById(R.id.layout_view_and_update_user_item_category);
        categoryLabelTextView = categoryIncludeField.findViewById(R.id.user_view_update_item_label);
        categoryValueTextView = categoryIncludeField.findViewById(R.id.user_view_update_item_value);
        categoryUpdateButton = categoryIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(categoryUpdateButton, this, 0.2, 0.04, 0.3);
        categoryLabelTextView.setText(CATEGORY_LABEL);
        categoryValueTextView.setText(category);
        categoryLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        categoryValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        categoryUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
            fetchCategoriesAndClassNumbers();
        });

        // Section
        sectionIncludeField = findViewById(R.id.layout_view_and_update_user_item_section);
        sectionLabelTextView = sectionIncludeField.findViewById(R.id.user_view_update_item_label);
        sectionValueTextView = sectionIncludeField.findViewById(R.id.user_view_update_item_value);
        sectionUpdateButton = sectionIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(sectionUpdateButton, this, 0.2, 0.04, 0.3);
        sectionLabelTextView.setText(SECTION_LABEL);
        sectionValueTextView.setText(section);
        sectionLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        sectionValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        sectionUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
            fetchSections();
        });

        // foreign language
        foreignLanguageIncludeField = findViewById(R.id.layout_view_and_update_user_item_foreign_language);
        foreignLanguageLabelTextView = foreignLanguageIncludeField.findViewById(R.id.user_view_update_item_label);
        foreignLanguageValueTextView = foreignLanguageIncludeField.findViewById(R.id.user_view_update_item_value);
        foreignLanguageUpdateButton = foreignLanguageIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(foreignLanguageUpdateButton, this, 0.2, 0.04, 0.3);
        foreignLanguageLabelTextView.setText(FOREIGN_LANGUAGE_LABEL);
        foreignLanguageValueTextView.setText(foreignLanguage);
        foreignLanguageLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        foreignLanguageValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        foreignLanguageUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
//            Toast.makeText(ViewAndUpdateStudentActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchForeignLanguages();
        });

        // father name
        fatherNameIncludeField = findViewById(R.id.layout_view_and_update_user_item_father_name);
        fatherNameLabelTextView = fatherNameIncludeField.findViewById(R.id.user_view_update_item_label);
        fatherNameValueTextView = fatherNameIncludeField.findViewById(R.id.user_view_update_item_value);
        fatherNameUpdateButton = fatherNameIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(fatherNameUpdateButton, this, 0.2, 0.04, 0.3);
        fatherNameLabelTextView.setText(FATHER_NAME_LABEL);
        fatherNameValueTextView.setText(fatherName);
        fatherNameLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        fatherNameValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        fatherNameUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            updateUserItemField(FATHER_NAME_LABEL, fatherNameValueTextView.getText().toString());
        });

        // mobile phone
        mobilePhoneIncludeField = findViewById(R.id.layout_view_and_update_user_item_mobile_phone);
        mobilePhoneLabelTextView = mobilePhoneIncludeField.findViewById(R.id.user_view_update_item_label);
        mobilePhoneValueTextView = mobilePhoneIncludeField.findViewById(R.id.user_view_update_item_value);
        mobilePhoneUpdateButton = mobilePhoneIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(mobilePhoneUpdateButton, this, 0.2, 0.04, 0.3);
        mobilePhoneLabelTextView.setText(MOBILE_PHONE_LABEL);
        mobilePhoneValueTextView.setText(mobilePhone);
        mobilePhoneLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        mobilePhoneValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        mobilePhoneUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            updateUserItemField(MOBILE_PHONE_LABEL, mobilePhoneValueTextView.getText().toString());
        });

        // school class code
        schoolClassCodeIncludeField = findViewById(R.id.layout_view_and_update_user_item_school_class_code);
        schoolClassCodeLabelTextView = schoolClassCodeIncludeField.findViewById(R.id.user_view_update_item_label);
        schoolClassCodeValueTextView = schoolClassCodeIncludeField.findViewById(R.id.user_view_update_item_value);
        schoolClassCodeUpdateButton = schoolClassCodeIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(schoolClassCodeUpdateButton, this, 0.2, 0.04, 0.3);
        schoolClassCodeLabelTextView.setText(SCHOOL_CLASS_CODE_LABEL);
        schoolClassCodeValueTextView.setText(schoolClassCode);
        schoolClassCodeLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        schoolClassCodeValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        schoolClassCodeUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            updateUserItemField(SCHOOL_CLASS_CODE_LABEL, schoolClassCodeValueTextView.getText().toString());
        });

        // address
        addressIncludeField = findViewById(R.id.layout_view_and_update_user_item_address);
        addressLabelTextView = addressIncludeField.findViewById(R.id.user_view_update_item_label);
        addressValueTextView = addressIncludeField.findViewById(R.id.user_view_update_item_value);
        addressUpdateButton = addressIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(addressUpdateButton, this, 0.2, 0.04, 0.3);
        addressLabelTextView.setText(ADDRESS_LABEL);
        addressValueTextView.setText(address);
        addressLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        addressValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        addressUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            updateUserItemField(ADDRESS_LABEL, addressValueTextView.getText().toString());
        });

        // class number
        classNumberIncludeField = findViewById(R.id.layout_view_and_update_user_item_class_number);
        classNumberLabelTextView = classNumberIncludeField.findViewById(R.id.user_view_update_item_label);
        classNumberValueTextView = classNumberIncludeField.findViewById(R.id.user_view_update_item_value);
        classNumberUpdateButton = classNumberIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(classNumberUpdateButton, this, 0.2, 0.04, 0.3);
        classNumberLabelTextView.setText(CLASS_NUMBER_LABEL);
        classNumberValueTextView.setText(classNumber);
        classNumberLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        classNumberValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        classNumberUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
//            Toast.makeText(ViewAndUpdateStudentActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchCategoriesAndClassNumbers();
        });

        // class letter
        classLetterIncludeField = findViewById(R.id.layout_view_and_update_user_item_class_letter);
        classLetterLabelTextView = classLetterIncludeField.findViewById(R.id.user_view_update_item_label);
        classLetterValueTextView = classLetterIncludeField.findViewById(R.id.user_view_update_item_value);
        classLetterUpdateButton = classLetterIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(classLetterUpdateButton, this, 0.2, 0.04, 0.3);

        classLetterLabelTextView.setText(CLASS_LETTER_LABEL);
        classLetterValueTextView.setText(classLetter != null ? classLetter : "");
        classLetterLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        classLetterValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        classLetterUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
//            Toast.makeText(ViewAndUpdateStudentActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchClassLetters();
        });

        // communication status
        communicationStatusIncludeField = findViewById(R.id.layout_view_and_update_user_item_communication_status);
        communicationStatusLabelTextView = communicationStatusIncludeField.findViewById(R.id.user_view_update_item_label);
        communicationStatusValueTextView = communicationStatusIncludeField.findViewById(R.id.user_view_update_item_value);
        communicationStatusUpdateButton = communicationStatusIncludeField.findViewById(R.id.user_view_update_item_update_button);
        ViewStyler.setButtonSize(communicationStatusUpdateButton, this, 0.2, 0.04, 0.3);
        communicationStatusLabelTextView.setText(COMMUNICATION_STATUS_LABEL);
        communicationStatusValueTextView.setText(communicationStatus);
        communicationStatusLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * LABEL_SIZE_PERCENTAGE);
        communicationStatusValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, device_screen_size * VALUE_SIZE_PERCENTAGE);

        communicationStatusUpdateButton.setOnClickListener(v -> {
            isStudentRequest = true;
            showLoading();
//            Toast.makeText(ViewAndUpdateStudentActivity.this, "Gözləyin", Toast.LENGTH_SHORT).show();
            fetchCommunicationSenderStatuses();
        });
    }

    private void fetchUserStatuses() {
        final int gen = pageGen;
        UserStatusApi userStatusApi = RetrofitInstance.getRetrofitInstance(this).create(UserStatusApi.class);
        StatusDataFetcher<UserStatus> userStatusDataFetcher = new StatusDataFetcher<>(this);
        userStatusDataFetcher.getAllStatusesFromDatabase(new UserStatusApiImpl(userStatusApi), new DataFetchCallback<UserStatus>() {
            @Override
            public void onDataFetched(List<UserStatus> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                userStatuses = new ArrayList<>(data);
                hideLoading();
                updateUserItemField(STATUS_LABEL, statusValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(UserStatus item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private void fetchCommunicationSenderStatuses() {
        final int gen = pageGen;
        StudentCommunicationSenderStatusApi commApi = RetrofitInstance.getRetrofitInstance(this).create(StudentCommunicationSenderStatusApi.class);
        StatusDataFetcher<StudentCommunicationSenderStatus> dataFetcher = new StatusDataFetcher<>(this);
        dataFetcher.getAllStatusesFromDatabase(new StudentCommunicationSenderStatusApiImpl(commApi), new DataFetchCallback<StudentCommunicationSenderStatus>() {
            @Override
            public void onDataFetched(List<StudentCommunicationSenderStatus> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                studentCommunicationSenderStatuses = new ArrayList<>(data);
                hideLoading();
                updateUserItemField(COMMUNICATION_STATUS_LABEL, communicationStatusValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(StudentCommunicationSenderStatus item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return; hideLoading();
            }
        });
    }

    private void fetchStudentTypes() {
        final int gen = pageGen;
        StudentTypeApi studentTypeApi = RetrofitInstance.getRetrofitInstance(this).create(StudentTypeApi.class);
        UserAndExamDetailsDataFetcher<StudentType, StudentTypeRequest> dataFetcher = new UserAndExamDetailsDataFetcher<>(this, new StudentTypeApiImpl(studentTypeApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<StudentType>() {
            @Override
            public void onDataFetched(List<StudentType> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                if (data != null) {
                    studentTypesList = new ArrayList<>(data);
                } else {
                    studentTypesList = new ArrayList<>();
                }
                hideLoading();
                updateUserItemField(STUDENT_TYPE_LABEL, studentTypeValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(StudentType item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private void fetchGroups() {
        final int gen = pageGen;
        GroupApi groupApi = RetrofitInstance.getRetrofitInstance(this).create(GroupApi.class);
        UserAndExamDetailsDataFetcher<Group, GroupRequest> dataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new GroupApiImpl(groupApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<Group>() {
            @Override
            public void onDataFetched(List<Group> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                groupsList = new ArrayList<>(data != null ? data : new ArrayList<>());
                hideLoading();
                updateUserItemField(GROUP_LABEL, groupValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(Group item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return; hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private void fetchSubGroups() {
        final int gen = pageGen;
        SubGroupApi subGroupApi = RetrofitInstance.getRetrofitInstance(this).create(SubGroupApi.class);
        UserAndExamDetailsDataFetcher<SubGroup, SubGroupRequest> dataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new SubGroupApiImpl(subGroupApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<SubGroup>() {
            @Override
            public void onDataFetched(List<SubGroup> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                subGroupsList = new ArrayList<>(data != null ? data : new ArrayList<>());
                hideLoading();
                updateUserItemField(SUB_GROUP_LABEL, subGroupValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(SubGroup item) {
                // Not used in this context
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private void fetchCategories() {
        final int gen = pageGen;
        CategoryApi categoryApi = RetrofitInstance.getRetrofitInstance(this).create(CategoryApi.class);
        UserAndExamDetailsDataFetcher<Category, CategoryRequest> dataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new CategoryApiImpl(categoryApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<Category>() {
            @Override
            public void onDataFetched(List<Category> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                if (data != null) {
                    categoriesList = new ArrayList<>(data);
                } else {
                    categoriesList = new ArrayList<>();
                }
                hideLoading();
                // call updateUserItemField to update the spinner (or dialog) for categories
                updateUserItemField(CATEGORY_LABEL, categoryValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(Category item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private void fetchSections() {
        final int gen = pageGen;
        SectionApi sectionApi = RetrofitInstance.getRetrofitInstance(this).create(SectionApi.class);
        UserAndExamDetailsDataFetcher<Section, SectionRequest> dataFetcher = new UserAndExamDetailsDataFetcher<>(this, new SectionApiImpl(sectionApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<Section>() {
            @Override
            public void onDataFetched(List<Section> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                if (data != null) {
                    sectionsList = new ArrayList<>(data);
                } else {
                    sectionsList = new ArrayList<>();
                }
                hideLoading();
                updateUserItemField(SECTION_LABEL, sectionValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(Section item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private void fetchForeignLanguages() {
        final int gen = pageGen;
        ForeignLanguageApi flApi = RetrofitInstance.getRetrofitInstance(this).create(ForeignLanguageApi.class);
        UserAndExamDetailsDataFetcher<ForeignLanguage, ForeignLanguageRequest> dataFetcher = new UserAndExamDetailsDataFetcher<>(this, new ForeignLanguageApiImpl(flApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<ForeignLanguage>() {
            @Override
            public void onDataFetched(List<ForeignLanguage> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                foreignLanguagesList = new ArrayList<>(data != null ? data : new ArrayList<>());
                hideLoading();
                updateUserItemField(FOREIGN_LANGUAGE_LABEL, foreignLanguageValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(ForeignLanguage item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private void fetchClassNumbers() {
        final int gen = pageGen;
        ClassNumberApi classNumberApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(ClassNumberApi.class);
        UserAndExamDetailsDataFetcher<ClassNumber, ClassNumberRequest> dataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new ClassNumberApiImpl(classNumberApi));

        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<ClassNumber>() {
            @Override
            public void onDataFetched(List<ClassNumber> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                if (data != null) {
                    classNumberList = new ArrayList<>(SortUtils.sortNumbersInAscOrder(data, ClassNumber::getNumberValue));
                } else {
                    classNumberList = new ArrayList<>();
                }
                hideLoading();
                updateUserItemField(CLASS_NUMBER_LABEL, classNumberValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(ClassNumber item) {
                // Not used here
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private void fetchClassLetters() {
        final int gen = pageGen;
        ClassLetterApi classLetterApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(ClassLetterApi.class);
        UserAndExamDetailsDataFetcher<ClassLetter, ClassLetterRequest> dataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new ClassLetterApiImpl(classLetterApi));

        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<ClassLetter>() {
            @Override
            public void onDataFetched(List<ClassLetter> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                if (data != null) {
                    classLettersList = new ArrayList<>(SortUtils.sortInAlphabeticalOrder(data, ClassLetter::getLetterValue));
                } else {
                    classLettersList = new ArrayList<>();
                }
                hideLoading();
                updateUserItemField(CLASS_LETTER_LABEL, classLetterValueTextView.getText().toString());
            }

            @Override
            public void onSingleItemFetched(ClassLetter item) {
                // Not used here
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                // handle unsuccessful response if needed
                if (!isActiveGen(gen)) return;
                hideLoading();
                Toast.makeText(ViewAndUpdateStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }
        });
    }

    private <T extends Status> void setUpStatusSpinnerForUpdate(
            CustomSpinner spinner,
            List<T> statusList,
            double dynamicHeight,
            double textSizePct,
            String currentValue,
            TextView labelTextView
    ) {
        // create and set StatusAdapter
        StatusAdapter<T> adapter = new StatusAdapter<>(
                this,
                statusList,
                dynamicHeight,
                textSizePct,
                null,
                spinner
        );
        spinner.setAdapter(adapter);

        // optionally let's set a label:
        if (labelTextView != null) {
            labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (float) (dynamicHeight * 0.03));
        }

        // pre-select the spinner item that matches currentValue
        int initialPosition = 0;
        for (int i = 0; i < statusList.size(); i++) {
            if (currentValue != null && currentValue.equals(statusList.get(i).getStatusText())) {
                initialPosition = i;
                break;
            }
        }
        spinner.setSelection(initialPosition);
        setUpBaseSpinner(spinner, labelTextView);
    }

    private <T extends RecyclerViewItemPositionable> View buildSpinnerField(
            LinearLayout container,
            List<T> items,
            String labelText,
            SpinnerType type,
            List<T> currentList,
            String currentValue,
            boolean allowEmpty
    ) {
        View row = LayoutInflater.from(this)
                .inflate(R.layout.request_spinner, container, false);

        CustomSpinner spinner = row.findViewById(R.id.requestSpinner);
        TextView label = row.findViewById(R.id.requestSpinnerLabelTextView);
        View errorWrapper = row.findViewById(R.id.errorMessageRequestSpinner);

        label.setText(labelText);

        setUpCommonSpinnerForUpdate(
                spinner,
                items,
                dynamicContentPlaceholderHeight,
                spinnerItemTextSizePercentage,
                currentValue,
                allowEmpty,
                true,
                label,
                type
        );
        attachSpinnerClearErrorListener(spinner, errorWrapper);

        return row;
    }

    private <T extends RecyclerViewItemPositionable> void setUpCommonSpinnerForUpdate(
            CustomSpinner spinner,
            List<T> itemList,
            double dynamicHeight,
            double textSizePct,
            String currentValue,
            boolean allowEmpty,
            boolean showAddItem,
            TextView labelTextView,
            SpinnerType spinnerType
    ) {
        // use trimmed strings for comparison.
        String trimmedValue = currentValue != null ? currentValue.trim() : "";
        Integer matchedId = null;
        for (T item : itemList) {
            if (item != null && trimmedValue.equals(item.getItemName().trim())) {
                matchedId = item.getItemId();
                break;
            }
        }

        CommonSpinnerAdapter<T> adapter = new CommonSpinnerAdapter<>(
                this,
                itemList,
                dynamicHeight,
                textSizePct,
                this,  // SpecialSpinnerItemClickListener
                spinner,
                allowEmpty,
                showAddItem,
                matchedId
        );
        spinner.setAdapter(adapter);

        int position = adapter.getPositionOfSelectedItem();
        if (position >= 0) {
            spinner.setSelection(position);
        } else if (allowEmpty) {
            spinner.setSelection(0);
        }

        spinner.setTag(spinnerType);
        setSpinnerEmptyListener(spinner, itemList, spinnerType, allowEmpty);

        // if no label was passed, try to retrieve it from the spinner's parent.
        if (labelTextView == null && spinner.getParent() instanceof ViewGroup) {
            labelTextView = (TextView) ((ViewGroup) spinner.getParent()).findViewById(R.id.requestSpinnerLabelTextView);
        }
        setUpBaseSpinner(spinner, labelTextView);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void updateUserItemField(String labelText, String currentValue) {
        // If a dialog is already open, update its spinner content while preserving the previously selected value.
        if (currentUpdateDialog != null && currentUpdateDialog.isShowing()) {
            if (labelText.equals(SUB_GROUP_LABEL) && spinnerSubGroups != null) {
                setUpCommonSpinnerForUpdate(
                        spinnerSubGroups,
                        subGroupsList,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        true,  // sub group can be empty
                        true,
                        null,  // let helper obtain the label from the spinner's parent
                        SpinnerType.SUB_GROUPS
                );
            } else if (labelText.equals(GROUP_LABEL) && spinnerGroups != null) {
                setUpCommonSpinnerForUpdate(
                        spinnerGroups,
                        groupsList,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        true,  // groups can be empty
                        true,
                        null,
                        SpinnerType.STUDENT_GROUPS
                );
            } else if (labelText.equals(STUDENT_TYPE_LABEL) && spinnerStudentTypes != null) {
                if (studentTypesList == null || studentTypesList.isEmpty()) {
                    spinnerStudentTypes.postDelayed(() -> spinnerStudentTypes.dismissDropdown(), 100);
                    showEmptySpinnerDialog(SpinnerType.STUDENT_TYPES);
                }
                setUpCommonSpinnerForUpdate(
                        spinnerStudentTypes,
                        studentTypesList,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        false, // student type is required. do not allow empty option
                        true,
                        null,
                        SpinnerType.STUDENT_TYPES
                );
            } else if (labelText.equals(CATEGORY_LABEL) && spinnerCategories != null) {
                if (categoriesList == null || categoriesList.isEmpty()) {
                    spinnerCategories.postDelayed(() -> spinnerCategories.dismissDropdown(), 100);
                    showEmptySpinnerDialog(SpinnerType.STUDENT_CATEGORIES);
                }
                setUpCommonSpinnerForUpdate(
                        spinnerCategories,
                        categoriesList,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        false,
                        true,
                        null,
                        SpinnerType.STUDENT_CATEGORIES
                );
            } else if (labelText.equals(SECTION_LABEL) && spinnerSections != null) {
                if (sectionsList == null || sectionsList.isEmpty()) {
                    spinnerSections.postDelayed(() -> spinnerSections.dismissDropdown(), 100);
                    showEmptySpinnerDialog(SpinnerType.STUDENT_SECTIONS);
                }
                setUpCommonSpinnerForUpdate(
                        spinnerSections,
                        sectionsList,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        false,
                        true,
                        null,
                        SpinnerType.STUDENT_SECTIONS
                );
            } else if (labelText.equals(FOREIGN_LANGUAGE_LABEL) && spinnerForeignLanguages != null) {
                if (foreignLanguagesList == null || foreignLanguagesList.isEmpty()) {
                    spinnerForeignLanguages.postDelayed(() -> spinnerForeignLanguages.dismissDropdown(), 100);
                    showEmptySpinnerDialog(SpinnerType.STUDENT_FOREIGN_LANGUAGES);
                }
                setUpCommonSpinnerForUpdate(
                        spinnerForeignLanguages,
                        foreignLanguagesList,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        false,
                        true,
                        null,
                        SpinnerType.STUDENT_FOREIGN_LANGUAGES
                );
            } else if (labelText.equals(CLASS_NUMBER_LABEL) && spinnerClassNumbers != null) {
                if (classNumberList == null || classNumberList.isEmpty()) {
                    spinnerClassNumbers.postDelayed(() -> spinnerClassNumbers.dismissDropdown(), 100);
                    showEmptySpinnerDialog(SpinnerType.CLASS_NUMBERS);
                }
                setUpCommonSpinnerForUpdate(
                        spinnerClassNumbers,
                        classNumberList,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        false,
                        true,
                        null,
                        SpinnerType.CLASS_NUMBERS
                );
            } else if (labelText.equals(CLASS_LETTER_LABEL) && spinnerClassLetters != null) {
                setUpCommonSpinnerForUpdate(
                        spinnerClassLetters,
                        classLettersList,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        true,
                        true,
                        null,
                        SpinnerType.CLASS_LETTERS
                );
            } else if (labelText.equals(STATUS_LABEL) && spinnerStatuses != null) {
                // for user status (belongs to the User)
                setUpStatusSpinnerForUpdate(
                        spinnerStatuses,
                        userStatuses,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        null
                );
            } else if (labelText.equals(COMMUNICATION_STATUS_LABEL) && spinnerCommunicationSenderStatuses != null) {
                // for student's communication status
                setUpStatusSpinnerForUpdate(
                        spinnerCommunicationSenderStatuses,
                        studentCommunicationSenderStatuses,
                        dynamicContentPlaceholderHeight,
                        spinnerItemTextSizePercentage,
                        currentValue,
                        null
                );
            }
            return;
        }

        // create a new update dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update_item, null);
        builder.setView(dialogView);

        dynamicContentPlaceholderHeight = getWindow().getDecorView().getRootView().getHeight();
        int marginTop = ScreenUtils.calculateHeightWithPercentage(this, 0.02);
        int marginBottom = ScreenUtils.calculateHeightWithPercentage(this, 0.02);
        int marginLeft = ScreenUtils.calculateWidthWithPercentage(this, 0.02);
        int marginRight = ScreenUtils.calculateWidthWithPercentage(this, 0.02);

        // set up the footer (Cancel & Update buttons)
        ConstraintLayout footerLayout = dialogView.findViewById(R.id.footerLayoutDialogUpdateItem);
        Button btnCancel = footerLayout.findViewById(R.id.btnCancel);
        Button btnUpdate = footerLayout.findViewById(R.id.btnUpdate);
        ViewStyler.setButtonSize(btnCancel, this, 0.15, 0.035, 0.27);
        ViewStyler.setButtonSize(btnUpdate, this, 0.15, 0.035, 0.27);
        ViewGroup.MarginLayoutParams layoutParamsFooter = (ViewGroup.MarginLayoutParams) footerLayout.getLayoutParams();
        layoutParamsFooter.topMargin = marginTop;
        layoutParamsFooter.bottomMargin = marginBottom;

        ConstraintLayout customDialogLayout = dialogView.findViewById(R.id.custom_dialog_constraintLayout);
        if (customDialogLayout != null) {
            customDialogLayout.setPadding(0, 0, 0, marginBottom);
        }

        LinearLayout dynamicRequestItemContainer = dialogView.findViewById(R.id.dynamicFieldsContainerUpdate);
        ViewGroup.MarginLayoutParams containerParams = (ViewGroup.MarginLayoutParams) dynamicRequestItemContainer.getLayoutParams();
        containerParams.leftMargin = marginLeft;
        containerParams.rightMargin = marginRight;
        dynamicRequestItemContainer.setLayoutParams(containerParams);

        currentUpdateDialog = builder.create();

        // set up input view based on labelText
        final EditText requestValueEditTextLocal; // for text input fields (non-spinner)
        final TextView requestLabelTextViewLocal;   // for text input fields
        View addRequestView;

        if (labelText.equals(CLASS_LETTER_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerClassLetters = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select class letter");
            setUpCommonSpinnerForUpdate(
                    spinnerClassLetters,
                    classLettersList,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    true,
                    true,
                    spinnerLabel,
                    SpinnerType.CLASS_LETTERS
            );
            attachSpinnerClearErrorListener(spinnerClassLetters, dialogFieldErrorMessage);
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(STATUS_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerStatuses = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select status");
            setUpStatusSpinnerForUpdate(
                    spinnerStatuses,
                    userStatuses,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    spinnerLabel
            );
            attachSpinnerClearErrorListener(spinnerStatuses, dialogFieldErrorMessage);
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(COMMUNICATION_STATUS_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerCommunicationSenderStatuses = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Message sending permission");
            setUpStatusSpinnerForUpdate(
                    spinnerCommunicationSenderStatuses,
                    studentCommunicationSenderStatuses,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    spinnerLabel
            );
            attachSpinnerClearErrorListener(spinnerCommunicationSenderStatuses, dialogFieldErrorMessage);
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(STUDENT_TYPE_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerStudentTypes = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select Student Type");
            setUpCommonSpinnerForUpdate(
                    spinnerStudentTypes,
                    studentTypesList,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    false,
                    true,
                    spinnerLabel,
                    SpinnerType.STUDENT_TYPES
            );
            attachSpinnerClearErrorListener(spinnerStudentTypes, dialogFieldErrorMessage);
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(GROUP_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerGroups = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select group");
            setUpCommonSpinnerForUpdate(
                    spinnerGroups,
                    groupsList,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    true,
                    true,
                    spinnerLabel,
                    SpinnerType.STUDENT_GROUPS
            );
            // to clear error view after picking another element from drop down
            attachSpinnerClearErrorListener(spinnerGroups, dialogFieldErrorMessage);
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(SUB_GROUP_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerSubGroups = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select sub group");
            setUpCommonSpinnerForUpdate(
                    spinnerSubGroups,
                    subGroupsList,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    true,
                    true,
                    spinnerLabel,
                    SpinnerType.SUB_GROUPS
            );
            // to clear error view after picking another element from drop down
            attachSpinnerClearErrorListener(spinnerSubGroups, dialogFieldErrorMessage);
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(CATEGORY_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerCategories = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select category");
            setUpCommonSpinnerForUpdate(
                    spinnerCategories,
                    categoriesList,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    false,
                    true,
                    spinnerLabel,
                    SpinnerType.STUDENT_CATEGORIES
            );
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(SECTION_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerSections = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select section");
            setUpCommonSpinnerForUpdate(
                    spinnerSections,
                    sectionsList,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    false,
                    true,
                    spinnerLabel,
                    SpinnerType.STUDENT_SECTIONS
            );
            // to clear error view after picking another element from drop down
            attachSpinnerClearErrorListener(spinnerSections, dialogFieldErrorMessage);
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(FOREIGN_LANGUAGE_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerForeignLanguages = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select foreign language");
            setUpCommonSpinnerForUpdate(
                    spinnerForeignLanguages,
                    foreignLanguagesList,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    false,
                    true,
                    spinnerLabel,
                    SpinnerType.STUDENT_FOREIGN_LANGUAGES
            );
            // to clear error view after picking another element from drop down
            attachSpinnerClearErrorListener(spinnerForeignLanguages, dialogFieldErrorMessage);
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else if (labelText.equals(CLASS_NUMBER_LABEL)) {
            addRequestView = LayoutInflater.from(this).inflate(R.layout.request_spinner, dynamicRequestItemContainer, false);
            spinnerClassNumbers = addRequestView.findViewById(R.id.requestSpinner);
            TextView spinnerLabel = addRequestView.findViewById(R.id.requestSpinnerLabelTextView);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageRequestSpinner);
            spinnerLabel.setText("Select class number");
            setUpCommonSpinnerForUpdate(
                    spinnerClassNumbers,
                    classNumberList,
                    dynamicContentPlaceholderHeight,
                    spinnerItemTextSizePercentage,
                    currentValue,
                    false,
                    true,
                    spinnerLabel,
                    SpinnerType.CLASS_NUMBERS
            );
            requestValueEditTextLocal = null;
            requestLabelTextViewLocal = null;
        } else {
            // for all other fields, use a text input layout.
            addRequestView = LayoutInflater.from(this)
                    .inflate(R.layout.request_item, dynamicRequestItemContainer, false);
            requestLabelTextViewLocal = addRequestView.findViewById(R.id.textViewAddItem);
            requestValueEditTextLocal = addRequestView.findViewById(R.id.editTextItem);
            dialogFieldErrorMessage = addRequestView.findViewById(R.id.errorMessageAddItem);

            requestLabelTextViewLocal.setText(labelText);
            requestLabelTextViewLocal.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    ScreenUtils.calculateHeightWithPercentage(this, 0.03f));
            requestValueEditTextLocal.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    ScreenUtils.calculateHeightWithPercentage(this, 0.03f));

            // set initial text or hint based on whether it is an email field.
            if (labelText.equals(EMAIL_LABEL)) {
                if (currentValue.equals(emptyEmailTextMessage)) {
                    requestValueEditTextLocal.setHint(emptyEmailHintMessage);
                } else {
                    requestValueEditTextLocal.setText(currentValue);
                }
            } else {
                requestValueEditTextLocal.setText(currentValue);
            }

            requestValueEditTextLocal.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = s.toString().trim();

                    if (labelText.equals(NAME_LABEL) || labelText.equals(SURNAME_LABEL)) {
                        if (text.isEmpty()) {
                            showErrorMessage(dialogFieldErrorMessage, labelText + " cannot be empty.");
                        } else {
                            try {
                                ValidationUtils.validateNameOrSurname(text, labelText);
                                hideErrorMessage(dialogFieldErrorMessage);
                            } catch (RequestValidationException ex) {
                                showErrorMessage(dialogFieldErrorMessage, ex.getMessage());
                            }
                        }
                        return;
                    }

                    // mobile phone live-validation
                    if (labelText.equals(MOBILE_PHONE_LABEL)) {
                        if (text.isEmpty()) {
                            // optional field → hide any prior error
                            hideErrorMessage(dialogFieldErrorMessage);
                        } else {
                            try {
                                ValidationUtils.validateMobileNumber(text);
                                hideErrorMessage(dialogFieldErrorMessage);
                            } catch (RequestValidationException ex) {
                                showErrorMessage(dialogFieldErrorMessage, ex.getMessage());
                            }
                        }
                        return;
                    }

                    // student code live-validation
                    if (labelText.equals(STUDENT_CODE_LABEL)) {
                        if (text.isEmpty()) {
                            // required field → show “can’t be empty”
                            showErrorMessage(dialogFieldErrorMessage, labelText + " cannot be empty.");
                        } else {
                            try {
                                ValidationUtils.validateStudentCode(text);
                                hideErrorMessage(dialogFieldErrorMessage);
                            } catch (RequestValidationException ex) {
                                showErrorMessage(dialogFieldErrorMessage, ex.getMessage());
                            }
                        }
                        return;
                    }

                    // define which fields are optional
                    boolean isOptionalField = labelText.equals(EMAIL_LABEL) ||
                            labelText.equals(ADDRESS_LABEL) ||
                            labelText.equals(MOBILE_PHONE_LABEL) ||
                            labelText.equals(SCHOOL_CLASS_CODE_LABEL);

                    if (labelText.equals(EMAIL_LABEL)) {
                        // for email, validate format only if not empty
                        if (!text.isEmpty() && !EmailValidator.validateEmail(text)) {
                            showErrorMessage(dialogFieldErrorMessage, "Email is not in a valid format.");
                        } else {
                            hideErrorMessage(dialogFieldErrorMessage);
                            if (text.isEmpty()) {
                                requestValueEditTextLocal.setHint(emptyEmailHintMessage);
                            }
                        }
                    } else if (isOptionalField) {
                        // for other optional fields, don't show error when empty
                        hideErrorMessage(dialogFieldErrorMessage);
                    } else {
                        // for required fields, show error if empty
                        if (!text.isEmpty()) {
                            hideErrorMessage(dialogFieldErrorMessage);
                        } else {
                            showErrorMessage(dialogFieldErrorMessage, labelText + " cannot be empty.");
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }


        if (addRequestView.getParent() != null) {
            ((ViewGroup) addRequestView.getParent()).removeView(addRequestView);
        }
        dynamicRequestItemContainer.addView(addRequestView);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) dynamicRequestItemContainer.getLayoutParams();
        layoutParams.leftMargin = marginRight;
        layoutParams.rightMargin = marginRight;
        dynamicRequestItemContainer.setLayoutParams(layoutParams);

        btnUpdate.setOnClickListener(v -> {
            performFieldUpdate(labelText, requestValueEditTextLocal);
        });

        btnCancel.setOnClickListener(v -> {
            isStudentRequest = false;
            currentUpdateDialog.dismiss();
            currentUpdateDialog = null;
        });

        currentUpdateDialog.setOnDismissListener(dialog -> currentUpdateDialog = null);
        currentUpdateDialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void performFieldUpdate(String labelText, @Nullable EditText requestValueEditTextLocal) {
        UserRequest userRequest = new UserRequest();
        StudentRequest studentRequest = new StudentRequest();

        studentRequest.setUserId(userId);

        boolean hasErrors = dialogFieldErrorMessage != null &&
                dialogFieldErrorMessage.getVisibility() == View.VISIBLE;

        if (requestValueEditTextLocal != null) {
            String updatedValue = requestValueEditTextLocal.getText().toString().trim();
            boolean isOptional =
                    labelText.equals(EMAIL_LABEL)
                            || labelText.equals(ADDRESS_LABEL)
                            || labelText.equals(MOBILE_PHONE_LABEL)
                            || labelText.equals(SCHOOL_CLASS_CODE_LABEL);

            if (updatedValue.isEmpty() && !isOptional) {
                return;
            }

            // additional validation for specific fields
            if (labelText.equals(MOBILE_PHONE_LABEL) && !updatedValue.isEmpty()) {
                try {
                    ValidationUtils.validateMobileNumber(updatedValue);
                } catch (RequestValidationException ex) {
                    showErrorMessage(dialogFieldErrorMessage, ex.getMessage());
                    return;
                }
            }

            if (labelText.equals(STUDENT_CODE_LABEL)) {
                try {
                    ValidationUtils.validateStudentCode(updatedValue);
                } catch (RequestValidationException ex) {
                    showErrorMessage(dialogFieldErrorMessage, ex.getMessage());
                    return;
                }
            }

            if (labelText.equals(EMAIL_LABEL) && !updatedValue.isEmpty() && !EmailValidator.validateEmail(updatedValue)) {
                showErrorMessage(dialogFieldErrorMessage, "Email is not in a valid format.");
                return;
            }

            if (isStudentRequest) {
                switch (labelText) {
                    case NAME_LABEL:
                        studentRequest.setName(updatedValue);
                        break;
                    case SURNAME_LABEL:
                        studentRequest.setSurname(updatedValue);
                        break;
                    case STUDENT_CODE_LABEL:
                        studentRequest.setStudentCode(updatedValue);
                        break;
                    case FATHER_NAME_LABEL:
                        studentRequest.setFatherName(updatedValue);
                        break;
                    case MOBILE_PHONE_LABEL:
                        // For MOBILE_PHONE, send null when empty
                        // send empty when no text... in backend it will get it as empty and store null in database.
                        studentRequest.setMobilePhone(updatedValue.isEmpty() ? "" : updatedValue);
                        break;
                    case SCHOOL_CLASS_CODE_LABEL:
                        // For SCHOOL_CLASS_CODE, send null when empty
                        studentRequest.setSchoolClassCode(updatedValue.isEmpty() ? "" : updatedValue);
                        break;
                    case ADDRESS_LABEL:
                        // For ADDRESS, send null when empty
                        studentRequest.setAddress(updatedValue.isEmpty() ? "" : updatedValue);
                        break;
                }
            } else {
                switch (labelText) {
                    case USERNAME_LABEL:
                        userRequest.setUsername(updatedValue);
                        break;
                    case EMAIL_LABEL:
                        userRequest.setEmail(updatedValue);
                        break;
                }
            }
        } else if (labelText.equals(CATEGORY_CLASS_NUMBER_LABEL)) {
            // read both spinners
            Category c = (Category) spinnerCategories.getSelectedItem();
            if (c != null) studentRequest.setCategoryId(c.getItemId());

            ClassNumber cn = (ClassNumber) spinnerClassNumbers.getSelectedItem();
            if (cn != null) studentRequest.setClassNumberId(cn.getItemId());
        } else {
            // handle spinner‑based updates:
            if (labelText.equals(STATUS_LABEL)) {
                UserStatus s = (UserStatus) spinnerStatuses.getSelectedItem();
                if (s != null) userRequest.setStatusId(s.getStatusId());
            } else if (labelText.equals(COMMUNICATION_STATUS_LABEL)) {
                StudentCommunicationSenderStatus cs =
                        (StudentCommunicationSenderStatus) spinnerCommunicationSenderStatuses.getSelectedItem();
                if (cs != null) studentRequest.setCommunicationSenderStatusId(cs.getStatusId());
            } else if (labelText.equals(STUDENT_TYPE_LABEL)) {
                StudentType t = (StudentType) spinnerStudentTypes.getSelectedItem();
                if (t != null) studentRequest.setStudentTypeId(t.getItemId());
            }
            if (labelText.equals(GROUP_LABEL)) {
                int pos = spinnerGroups.getSelectedItemPosition();
                if (pos == 0) {
                    // first item is your “Empty” placeholder
                    studentRequest.setGroupId(0);
                } else {
                    Group g = (Group) spinnerGroups.getSelectedItem();
                    studentRequest.setGroupId(g.getItemId());
                }
            } else if (labelText.equals(SUB_GROUP_LABEL)) {
                int pos = spinnerSubGroups.getSelectedItemPosition();
                if (pos == 0) {
                    studentRequest.setSubGroupId(0);
                } else {
                    SubGroup ag = (SubGroup) spinnerSubGroups.getSelectedItem();
                    studentRequest.setSubGroupId(ag.getItemId());
                }
            }

            else if (labelText.equals(CATEGORY_LABEL)) {
                Category c = (Category) spinnerCategories.getSelectedItem();
                if (c != null) studentRequest.setCategoryId(c.getItemId());
            } else if (labelText.equals(SECTION_LABEL)) {
                Section b = (Section) spinnerSections.getSelectedItem();
                if (b != null) studentRequest.setSectionId(b.getItemId());
            } else if (labelText.equals(FOREIGN_LANGUAGE_LABEL)) {
                ForeignLanguage fl = (ForeignLanguage) spinnerForeignLanguages.getSelectedItem();
                if (fl != null) studentRequest.setForeignLanguageId(fl.getItemId());
            } else if (labelText.equals(CLASS_NUMBER_LABEL)) {
                ClassNumber cn = (ClassNumber) spinnerClassNumbers.getSelectedItem();
                if (cn != null) studentRequest.setClassNumberId(cn.getItemId());
            } else if (labelText.equals(CLASS_LETTER_LABEL)) {
                int pos = spinnerClassLetters.getSelectedItemPosition();
                if (pos == 0) {
                    // empty row selected → explicit CLEAR
                    studentRequest.setClassLetterId(0);
                } else {
                    ClassLetter cl = (ClassLetter) spinnerClassLetters.getSelectedItem();
                    if (cl != null) {
                        studentRequest.setClassLetterId(cl.getItemId());
                    }
                }
            }
        }

        // build and fire “full” request
        FullStudentRequest fullStudentRequest =
                new FullStudentRequest(userRequest, studentRequest);

        StudentApi studentApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(StudentApi.class);
        StudentApiImpl studentApiImpl = new StudentApiImpl(studentApi);
        Call<Student> call = studentApiImpl.updateItem(studentId, fullStudentRequest);

        setBlockingLoading(true);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                setBlockingLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    Student updatedStudent = response.body();
                    Log.d(TAG, "=== updatedStudent ===");
                    Log.d(TAG, "id: " + updatedStudent.getId());
                    Log.d(TAG, "userId: " + updatedStudent.getUser().getId());
                    Log.d(TAG, "username: " + updatedStudent.getUser().getUsername());
                    Log.d(TAG, "email: " + updatedStudent.getUser().getEmail());
                    Log.d(TAG, "status: " + updatedStudent.getUser().getStatus().getStatusText());
                    Log.d(TAG, "name: " + updatedStudent.getName());
                    Log.d(TAG, "surname: " + updatedStudent.getSurname());
                    Log.d(TAG, "studentCode: " + updatedStudent.getStudentCode());
                    Log.d(TAG, "studentType: " +
                            (updatedStudent.getStudentType() != null
                                    ? updatedStudent.getStudentType().getType()
                                    : "null"));
                    Log.d(TAG, "group: " +
                            (updatedStudent.getGroup() != null
                                    ? updatedStudent.getGroup().getItemName()
                                    : "null"));
                    Log.d(TAG, "subGroup: " +
                            (updatedStudent.getSubGroup() != null
                                    ? updatedStudent.getSubGroup().getItemName()
                                    : "null"));
                    Log.d(TAG, "category: " +
                            (updatedStudent.getCategory() != null
                                    ? updatedStudent.getCategory().getItemName()
                                    : "null"));
                    Log.d(TAG, "section: " +
                            (updatedStudent.getSection() != null
                                    ? updatedStudent.getSection().getItemName()
                                    : "null"));
                    Log.d(TAG, "foreignLanguage: " +
                            (updatedStudent.getForeignLanguage() != null
                                    ? updatedStudent.getForeignLanguage().getItemName()
                                    : "null"));
                    Log.d(TAG, "fatherName: " + updatedStudent.getFatherName());
                    Log.d(TAG, "mobilePhone: " + updatedStudent.getMobilePhone());
                    Log.d(TAG, "schoolClassCode: " + updatedStudent.getSchoolClassCode());
                    Log.d(TAG, "address: " + updatedStudent.getAddress());
                    Log.d(TAG, "classNumber: " +
                            (updatedStudent.getClassNumber() != null
                                    ? updatedStudent.getClassNumber().getNumberValue()
                                    : "null"));
                    Log.d(TAG, "classLetter: " +
                            (updatedStudent.getClassLetter() != null
                                    ? updatedStudent.getClassLetter().getLetterValue()
                                    : "null"));
                    Log.d(TAG, "communicationStatus: " +
                            (updatedStudent.getCommunicationSenderStatus() != null
                                    ? updatedStudent.getCommunicationSenderStatus().getStatusText()
                                    : "null"));
//                    currentUpdateDialog.dismiss();
                    if (currentUpdateDialog != null && currentUpdateDialog.isShowing()) {
                        currentUpdateDialog.dismiss();
                    }

                    // update all the UI fields from updatedStudent
                    updatedAtValueTextView.setText(updatedStudent.getUser().getUpdatedAt());
                    usernameValueTextView.setText(updatedStudent.getUser().getUsername());
                    emailValueTextView.setText(updatedStudent.getUser().getEmail() != null ? updatedStudent.getUser().getEmail() : "");
                    statusValueTextView.setText(updatedStudent.getUser().getStatus().getStatusText());
                    nameValueTextView.setText(updatedStudent.getName());
                    surnameValueTextView.setText(updatedStudent.getSurname());
                    studentCodeValueTextView.setText(updatedStudent.getStudentCode());

                    // student type
                    if (updatedStudent.getStudentType() != null) {
                        studentTypeValueTextView.setText(updatedStudent.getStudentType().getType());
                    } else {
                        studentTypeValueTextView.setText("");
                    }

                    // group - properly handle null check
                    if (updatedStudent.getGroup() != null) {
                        groupValueTextView.setText(updatedStudent.getGroup().getItemName());
                    } else {
                        groupValueTextView.setText("");
                    }

                    // sub-group - properly handle null check
                    if (updatedStudent.getSubGroup() != null) {
                        subGroupValueTextView.setText(updatedStudent.getSubGroup().getItemName());
                    } else {
                        subGroupValueTextView.setText("");
                    }

                    // category
                    if (updatedStudent.getCategory() != null) {
                        categoryValueTextView.setText(updatedStudent.getCategory().getItemName());
                    } else {
                        categoryValueTextView.setText("");
                    }

                    // section
                    if (updatedStudent.getSection() != null) {
                        sectionValueTextView.setText(updatedStudent.getSection().getItemName());
                    } else {
                        sectionValueTextView.setText("");
                    }

                    // foreign Language
                    if (updatedStudent.getForeignLanguage() != null) {
                        foreignLanguageValueTextView.setText(updatedStudent.getForeignLanguage().getItemName());
                    } else {
                        foreignLanguageValueTextView.setText("");
                    }

                    // set other fields with proper null checks
                    fatherNameValueTextView.setText(updatedStudent.getFatherName());
                    mobilePhoneValueTextView.setText(updatedStudent.getMobilePhone() != null ? updatedStudent.getMobilePhone() : "");
                    schoolClassCodeValueTextView.setText(updatedStudent.getSchoolClassCode() != null ? updatedStudent.getSchoolClassCode() : "");
                    addressValueTextView.setText(updatedStudent.getAddress() != null ? updatedStudent.getAddress() : "");

                    // class number
                    if (updatedStudent.getClassNumber() != null) {
                        classNumberValueTextView.setText(String.valueOf(updatedStudent.getClassNumber().getNumberValue()));
                    } else {
                        classNumberValueTextView.setText("");
                    }

                    // class letter
                    if (updatedStudent.getClassLetter() != null) {
                        classLetterValueTextView.setText(updatedStudent.getClassLetter().getLetterValue());
                    } else {
                        classLetterValueTextView.setText("");
                    }

                    // communication status
                    if (updatedStudent.getCommunicationSenderStatus() != null) {
                        communicationStatusValueTextView.setText(updatedStudent.getCommunicationSenderStatus().getStatusText());
                    } else {
                        communicationStatusValueTextView.setText("");
                    }

                    Toast.makeText(ViewAndUpdateStudentActivity.this,
                            "Student updated.", Toast.LENGTH_SHORT).show();
                    storeUpdateFieldsForPrevActivity(updatedStudent);

                } else {
                    try {
                        String errorMsg = response.errorBody().string();
                        showErrorMessage(dialogFieldErrorMessage, errorMsg);
//                        Toast.makeText(ViewAndUpdateStudentActivity.this,
//                                errorMsg, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ViewAndUpdateStudentActivity.this,
                                "Update failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                setBlockingLoading(false);
                Toast.makeText(ViewAndUpdateStudentActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEmptySpinnerDialog(SpinnerType spinnerType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Inflate the base dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update_item, null);
        builder.setView(dialogView);

        // Calculate margins using your ScreenUtils methods
        int marginTop = ScreenUtils.calculateHeightWithPercentage(this, 0.015);
        int marginBottom = ScreenUtils.calculateHeightWithPercentage(this, 0.015);
        int marginLeft = ScreenUtils.calculateWidthWithPercentage(this, 0.02);

        // Find the footer and set button sizes and margins using ViewStyler
        ConstraintLayout footerLayout = dialogView.findViewById(R.id.footerLayoutDialogUpdateItem);
        Button btnCancel = footerLayout.findViewById(R.id.btnCancel);
        Button btnNavigate = footerLayout.findViewById(R.id.btnUpdate);

        // Set button sizes
        ViewStyler.setButtonSize(btnCancel, this, 0, 0.045, 0.3);
        ViewStyler.setButtonSize(btnNavigate, this, 0, 0.045, 0.3);

        ViewGroup.MarginLayoutParams footerParams = (ViewGroup.MarginLayoutParams) footerLayout.getLayoutParams();
        footerParams.topMargin = marginTop;
        footerParams.bottomMargin = marginBottom;

        ConstraintLayout.LayoutParams navigateParams = (ConstraintLayout.LayoutParams) btnNavigate.getLayoutParams();
        navigateParams.leftMargin = marginLeft;

        // Set padding on the custom dialog layout (if present)
        ConstraintLayout customDialogLayout = dialogView.findViewById(R.id.custom_dialog_constraintLayout);
        if (customDialogLayout != null) {
            customDialogLayout.setPadding(0, 0, 0, marginBottom);
        }

        // Find the container where the custom message will go
        LinearLayout container = dialogView.findViewById(R.id.dynamicFieldsContainerUpdate);
        // Inflate custom message layout into that container
        View messageView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_message, container, false);
        // Get the TextView from the custom message layout
        TextView dialogMessage = messageView.findViewById(R.id.dialogMessageTextView);

        // set padding inside the custom message layout
        ConstraintLayout customMessageLayout = messageView.findViewById(R.id.custom_dialog_message_constraintLayout);
        double paddingPercentage = 0.02;
        int padding = ScreenUtils.calculatePaddingWithPercentage(this, paddingPercentage);
        customMessageLayout.setPadding(padding, padding, padding, padding);

        // Add the custom message view to the container
        container.addView(messageView);

        // Determine dialog message text and target intent based on spinnerType
        String dialogMessageText;
        Intent targetIntent;
        switch (spinnerType) {
            case STUDENT_TYPES:
                dialogMessageText = "Student type is not available. It must be added for new student registration.";
                targetIntent = new Intent(this, StudentTypesActivity.class);
                break;
            case STUDENT_GROUPS:
                dialogMessageText = "Group is not available. It must be added for new student registration.";
                targetIntent = new Intent(this, GroupsActivity.class);
                break;
            case SUB_GROUPS:
                dialogMessageText = "Subgroup is not available. It must be added for new student registration.";
                targetIntent = new Intent(this, SubGroupsActivity.class);
                break;
            case STUDENT_CATEGORIES:
                dialogMessageText = "Category is not available. It must be added for new student registration.";
                targetIntent = new Intent(this, CategoriesActivity.class);
                break;
            case STUDENT_SECTIONS:
                dialogMessageText = "Section is not available. It must be added for new student registration.";
                targetIntent = new Intent(this, SectionsActivity.class);
                break;
            case STUDENT_FOREIGN_LANGUAGES:
                dialogMessageText = "Foreign language is not available. It must be added for new student registration.";
                targetIntent = new Intent(this, ForeignLanguagesActivity.class);
                break;
            case CLASS_NUMBERS:
                dialogMessageText = "Class number is not available. It must be added for new student registration.";
                targetIntent = new Intent(this, ClassNumbersActivity.class);
                break;
            case CLASS_LETTERS:
                dialogMessageText = "Class letter is not available. It must be added for new student registration.";
                targetIntent = new Intent(this, ClassLettersActivity.class);
                break;
            default:
                dialogMessageText = "No data available.";
                targetIntent = null;
                break;
        }

        dialogMessage.setText(dialogMessageText);
        // Set the text size using your ScreenUtils
        int calculatedTextSize = ScreenUtils.calculateHeightWithPercentage(this, 0.022);
        dialogMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, calculatedTextSize);

        final AlertDialog updateDialog = builder.create();
        emptySpinnerDialog = updateDialog;

        // Set the navigation button text
        btnNavigate.setText("Add");

        // When "Add" is clicked, launch the target activity using the appropriate request code.
        btnNavigate.setOnClickListener(v -> {
            if (targetIntent != null) {
                int requestCode = getRequestCodeForSpinner(spinnerType);
                startActivityForResult(targetIntent, requestCode);
            }
            updateDialog.dismiss();
            emptySpinnerDialog = null;
        });

        btnCancel.setOnClickListener(v -> {
            updateDialog.dismiss();
            emptySpinnerDialog = null;
        });

        updateDialog.show();
    }

    private int getRequestCodeForSpinner(SpinnerType spinnerType) {
        switch (spinnerType) {
            case STUDENT_TYPES:
                return REQUEST_CODE_STUDENT_TYPES;
            case STUDENT_GROUPS:
                return REQUEST_CODE_GROUPS;
            case SUB_GROUPS:
                return REQUEST_CODE_SUB_GROUPS;
            case STUDENT_CATEGORIES:
                return REQUEST_CODE_CATEGORIES;
            case STUDENT_SECTIONS:
                return REQUEST_CODE_SECTIONS;
            case STUDENT_FOREIGN_LANGUAGES:
                return REQUEST_CODE_FOREIGN_LANGUAGES;
            case CLASS_NUMBERS:
                return REQUEST_CODE_CLASS_NUMBERS;
            case CLASS_LETTERS:
                return REQUEST_CODE_CLASS_LETTERS;
            default:
                return REQUEST_CODE_STUDENT_TYPES;
        }
    }

    private void fetchCategoriesAndClassNumbers() {
        final int gen = pageGen;
        // fetch Categories first
        CategoryApi catApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(CategoryApi.class);

        new UserAndExamDetailsDataFetcher<Category, CategoryRequest>(
                this,
                new CategoryApiImpl(catApi)
        ).getAllDataFromDatabase(new DataFetchCallback<Category>() {
            @Override
            public void onDataFetched(List<Category> data) {
                if (!isActiveGen(gen)) return;
                categoriesList = data != null ? data : Collections.emptyList();
                // now fetch ClassNumbers
                fetchClassNumbersOnly();
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response r) {
                if (!isActiveGen(gen)) return;
            }

            @Override
            public void onSingleItemFetched(Category item) {
            }
        });
    }

    private void fetchClassNumbersOnly() {
        final int gen = pageGen;
        ClassNumberApi cnApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(ClassNumberApi.class);

        new UserAndExamDetailsDataFetcher<ClassNumber, ClassNumberRequest>(
                this,
                new ClassNumberApiImpl(cnApi)
        ).getAllDataFromDatabase(new DataFetchCallback<ClassNumber>() {
            @Override
            public void onDataFetched(List<ClassNumber> data) {
                if (!isActiveGen(gen)) { hideLoading(); return; }
                classNumberList = data != null
                        ? SortUtils.sortNumbersInAscOrder(data, ClassNumber::getNumberValue)
                        : Collections.emptyList();
                // both lists are ready: show your combined dialog
                hideLoading();
                showCategoryClassNumberDialog();
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response r) {
                if (!isActiveGen(gen)) return;
                hideLoading();
            }

            @Override
            public void onSingleItemFetched(ClassNumber item) {
            }
        });
    }

    private void showCategoryClassNumberDialog() {
        //  measure available height for your dropdowns
        dynamicContentPlaceholderHeight =
                getWindow().getDecorView().getRootView().getHeight();

        // build the base dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dlg = LayoutInflater.from(this)
                .inflate(R.layout.dialog_update_item, null);
        builder.setView(dlg);
        currentUpdateDialog = builder.create();

        //  calculate margins
        int marginTop = ScreenUtils.calculateHeightWithPercentage(this, 0.02);
        int marginBottom = ScreenUtils.calculateHeightWithPercentage(this, 0.02);
        int marginLeft = ScreenUtils.calculateWidthWithPercentage(this, 0.02);
        int marginRight = ScreenUtils.calculateWidthWithPercentage(this, 0.02);

        // footer buttons container
        ConstraintLayout footerLayout = dlg.findViewById(R.id.footerLayoutDialogUpdateItem);
        ViewGroup.MarginLayoutParams footerParams =
                (ViewGroup.MarginLayoutParams) footerLayout.getLayoutParams();
        footerParams.topMargin = marginTop;
        footerParams.bottomMargin = marginBottom;

        //  dynamic-fields container
        LinearLayout container = dlg.findViewById(R.id.dynamicFieldsContainerUpdate);
        ViewGroup.MarginLayoutParams containerParams =
                (ViewGroup.MarginLayoutParams) container.getLayoutParams();
        containerParams.leftMargin = marginLeft;
        containerParams.rightMargin = marginRight;
        container.setLayoutParams(containerParams);

        // pad bottom if layout has a custom wrapper
        ConstraintLayout customDialogLayout = dlg.findViewById(R.id.custom_dialog_constraintLayout);
        if (customDialogLayout != null) {
            customDialogLayout.setPadding(0, 0, 0, marginBottom);
        }

        // CATEGORY spinner
        View catView = buildSpinnerField(
                container,
                categoriesList,
                CATEGORY_LABEL,
                SpinnerType.STUDENT_CATEGORIES,
                categoriesList,
                categoryValueTextView.getText().toString(),
                /* allowEmpty */ false
        );
        spinnerCategories = catView.findViewById(R.id.requestSpinner);
        container.addView(catView);

        // spacer
        View spacer = new View(this);
        LinearLayout.LayoutParams spacerLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                marginTop
        );
        container.addView(spacer, spacerLp);

        //  CLASS-NUMBER spinner
        View cnView = buildSpinnerField(
                container,
                classNumberList,
                CLASS_NUMBER_LABEL,
                SpinnerType.CLASS_NUMBERS,
                classNumberList,
                classNumberValueTextView.getText().toString(),
                /* allowEmpty */ false
        );
        spinnerClassNumbers = cnView.findViewById(R.id.requestSpinner);
        container.addView(cnView);

        //  bind my single, shared error view to the CLASS-NUMBER wrapper
        dialogFieldErrorMessage = cnView.findViewById(R.id.errorMessageRequestSpinner);

        // clear that error as soon as the user picks anything in either dropdown
        attachSpinnerClearErrorListener(spinnerCategories, dialogFieldErrorMessage);
        attachSpinnerClearErrorListener(spinnerClassNumbers, dialogFieldErrorMessage);

        // footer buttons
        Button btnCancel = dlg.findViewById(R.id.btnCancel);
        Button btnUpdate = dlg.findViewById(R.id.btnUpdate);
        ViewStyler.setButtonSize(btnCancel, this, 0.15, 0.04, 0.3);
        ViewStyler.setButtonSize(btnUpdate, this, 0.15, 0.04, 0.3);

        btnCancel.setOnClickListener(v -> {
            if (currentUpdateDialog != null && currentUpdateDialog.isShowing()) {
                currentUpdateDialog.dismiss();
                currentUpdateDialog = null;
            }
        });

        btnUpdate.setOnClickListener(v -> {
            performFieldUpdate(CATEGORY_CLASS_NUMBER_LABEL, null);
        });

        currentUpdateDialog.show();
    }

    private void storeUpdateFieldsForPrevActivity(Student updatedStudent) {
        if (updatedStudent == null) return;

        // Always include the student ID (primitive type)
        resultIntent.putExtra("studentId", updatedStudent.getId());

        if (updatedStudent.getName() != null) {
            resultIntent.putExtra("updatedStudentName", updatedStudent.getName());
        }

        if (updatedStudent.getSurname() != null) {
            resultIntent.putExtra("updatedStudentSurname", updatedStudent.getSurname());
        }

        if (updatedStudent.getUser() != null && updatedStudent.getUser().getStatus() != null) {
            resultIntent.putExtra("updatedStudentStatus", updatedStudent.getUser().getStatus());
        }

        if (updatedStudent.getStudentType() != null && updatedStudent.getStudentType().getType() != null) {
            resultIntent.putExtra("updatedStudentType", updatedStudent.getStudentType().getType());
        }

        if (updatedStudent.getCategory() != null && updatedStudent.getCategory().getCategory() != null) {
            resultIntent.putExtra("updatedStudentCategory", updatedStudent.getCategory().getCategory());
        }

        if (updatedStudent.getClassNumber() != null) {
            resultIntent.putExtra("updatedStudentClass", updatedStudent.getClassNumber().getNumberValue());
        }

        // always send the extra. because it can be null (value can be null)
        String newLetter =
                updatedStudent.getClassLetter() != null
                        ? updatedStudent.getClassLetter().getLetterValue()
                        : "";
        resultIntent.putExtra("updatedStudentClassLetter", newLetter);


        setResult(Activity.RESULT_OK, resultIntent);
    }

    private String getAccessToken() {
        return new TokenStore(getApplicationContext()).getAccessToken();
    }

    private void setUpBaseSpinner(CustomSpinner spinner, TextView spinnerLabel) {
        spinner.setSpinnerEventsListener(this);
        // Only set text size if spinnerLabel is not null
        if (spinnerLabel != null) {
            spinnerLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogLabelTextSize);
        }
        setDropDownVerticalOffset(spinner);
        setTopMarginForIncludeLayout(spinner, 0.015f);
    }

    private void setTopMarginForIncludeLayout(View includeLayout, float bottomMarginPercentage) {
        // Calculate the bottom margin value
        int marginBottom = (int) (dynamicContentPlaceholderHeight * bottomMarginPercentage);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) includeLayout.getLayoutParams();
        layoutParams.topMargin = marginBottom;
        includeLayout.setLayoutParams(layoutParams);
    }

    private void showLoading() {
        if (loadingOverlayUtils != null && overlayHost != null) {
            loadingOverlayUtils.showLayoutOverlay((ViewGroup) overlayHost);
        }
    }

    private void hideLoading() {
        if (loadingOverlayUtils != null) {
            loadingOverlayUtils.hideLayoutOverlay();
        }
    }

    private void setDropDownVerticalOffset(final CustomSpinner spinner) {
        ViewTreeObserver vto = spinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {
            int spinnerHeight = spinner.getHeight();
            spinner.setDropDownVerticalOffset(spinnerHeight);
            spinner.getViewTreeObserver().removeOnGlobalLayoutListener(() -> {
            });
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private <T> void setSpinnerEmptyListener(CustomSpinner spinner, List<T> detailList, SpinnerType spinnerType, boolean allowEmptyOption) {
        spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (allowEmptyOption || !detailList.isEmpty()) {
                    spinner.performClick();
                    return true;
                } else {
                    showEmptySpinnerDialog(spinnerType);
                    return true;
                }
            }
            return false;
        });
    }

    private void showErrorMessage(View errorView, String message) {
        if (errorView == null) return;
        TextView fieldErrorMessage = errorView.findViewById(R.id.errorMessageTextView);
        if (fieldErrorMessage == null) return;
        fieldErrorMessage.setText(message);
        errorView.setVisibility(View.VISIBLE);
        ViewStyler.setErrorMessageStyle(fieldErrorMessage, this, 0.4f);
    }

    private void hideErrorMessage(View errorView) {
        if (errorView == null) return;
        errorView.setVisibility(View.GONE);
    }

    private void attachSpinnerClearErrorListener(final CustomSpinner spinner, final View errorView) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Clear the error message when an item is selected.
                hideErrorMessage(errorView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optionally hide the error message if nothing is selected.
                hideErrorMessage(errorView);
            }
        });
    }

    private int bumpGen() {
        synchronized (genLock) {
            pageGen++;
            for (Call<?> c : new ArrayList<>(inFlight)) {
                if (c != null && !c.isCanceled()) c.cancel();
            }
            inFlight.clear();
            return pageGen;
        }
    }

    private boolean isActiveGen(int gen) {
        synchronized (genLock) {
            return gen == pageGen && !isFinishing() && !isDestroyed();
        }
    }

    private void setBlockingLoading(boolean show) {
        if (isFinishing() || isDestroyed()) return;
        if (show) {
            // full-screen, above any AlertDialog
            loadingOverlayUtils.showActivityOverlay(this, /*dimBackground=*/true);
        } else {
            loadingOverlayUtils.hideActivityOverlay();
        }
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        if (spinner == spinnerStatuses) {
            spinnerStatuses.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerCommunicationSenderStatuses) {
            spinnerCommunicationSenderStatuses.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerStudentTypes) {
            spinnerStudentTypes.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerGroups) {
            spinnerGroups.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerSubGroups) {
            spinnerSubGroups.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerCategories) {
            spinnerCategories.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerSections) {
            spinnerSections.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerForeignLanguages) {
            spinnerForeignLanguages.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerClassNumbers) {
            spinnerClassNumbers.setBackgroundResource(R.drawable.background_request_spinner_active);
        } else if (spinner == spinnerClassLetters) {
            spinnerClassLetters.setBackgroundResource(R.drawable.background_request_spinner_active);
        }
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        if (spinner == spinnerStatuses) {
            spinnerStatuses.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerCommunicationSenderStatuses) {
            spinnerCommunicationSenderStatuses.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerStudentTypes) {
            spinnerStudentTypes.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerGroups) {
            spinnerGroups.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerSubGroups) {
            spinnerSubGroups.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerCategories) {
            spinnerCategories.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerSections) {
            spinnerSections.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerForeignLanguages) {
            spinnerForeignLanguages.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerClassNumbers) {
            spinnerClassNumbers.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        } else if (spinner == spinnerClassLetters) {
            spinnerClassLetters.setBackgroundResource(R.drawable.background_request_spinner_inactive);
        }
    }

    @Override
    public void onSpecialItemClick(CustomSpinner currentSpinner) {
        if (currentSpinner == spinnerStudentTypes) {
            navigateToStudentTypesPage();
        } else if (currentSpinner == spinnerGroups) {
            navigateToGroupsPage();
        } else if (currentSpinner == spinnerSubGroups) {
            navigateToStudentSubGroupsPage();
        } else if (currentSpinner == spinnerCategories) {
            navigateToCategoriesPage();
        } else if (currentSpinner == spinnerSections) {
            navigateToSectionsPage();
        } else if (currentSpinner == spinnerForeignLanguages) {
            navigateToForeignLanguagesPage();
        } else if (currentSpinner == spinnerClassNumbers) {
            navigateToClassNumbersPage();
        } else if (currentSpinner == spinnerClassLetters) {
            navigateToClassLettersPage();
        }
    }

    private void navigateToStudentTypesPage() {
        bumpGen();
        Intent intent = new Intent(this, StudentTypesActivity.class);
        startActivityForResult(intent, REQUEST_CODE_STUDENT_TYPES);
    }

    private void navigateToGroupsPage() {
        bumpGen();
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_GROUPS);
    }

    private void navigateToStudentSubGroupsPage() {
        bumpGen();
        Intent intent = new Intent(this, SubGroupsActivity.class);
        if (this instanceof Activity) {
            ((Activity) this).startActivityForResult(intent, REQUEST_CODE_SUB_GROUPS);
        } else {
            this.startActivity(intent);
        }
    }

    private void navigateToCategoriesPage() {
        bumpGen();
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CATEGORIES);
    }

    private void navigateToSectionsPage() {
        bumpGen();
        Intent intent = new Intent(this, SectionsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SECTIONS);
    }

    private void navigateToForeignLanguagesPage() {
        bumpGen();
        Intent intent = new Intent(this, ForeignLanguagesActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FOREIGN_LANGUAGES);
    }

    private void navigateToClassNumbersPage() {
        bumpGen();
        Intent intent = new Intent(this, ClassNumbersActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CLASS_NUMBERS);
    }

    private void navigateToClassLettersPage() {
        bumpGen();
        Intent intent = new Intent(this, ClassLettersActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CLASS_LETTERS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Detect if the combined Category & Class‑Number dialog is already showing
        boolean combinedIsUp = currentUpdateDialog != null && currentUpdateDialog.isShowing();

        switch (requestCode) {
            case REQUEST_CODE_SUB_GROUPS:
                fetchSubGroups();
                break;

            case REQUEST_CODE_GROUPS:
                fetchGroups();
                break;

            case REQUEST_CODE_STUDENT_TYPES:
                fetchStudentTypes();
                break;

            case REQUEST_CODE_CATEGORIES:
                if (combinedIsUp) {
                    // just refresh the Category spinner in the existing combined dialog
                    fetchCategories();
                } else {
                    // first time opening: fetch both lists and show the combined dialog
                    fetchCategoriesAndClassNumbers();
                }
                break;

            case REQUEST_CODE_SECTIONS:
                fetchSections();
                break;

            case REQUEST_CODE_FOREIGN_LANGUAGES:
                fetchForeignLanguages();
                break;

            case REQUEST_CODE_CLASS_NUMBERS:
                if (combinedIsUp) {
                    // just refresh the Class‑Number spinner in the existing combined dialog
                    fetchClassNumbers();
                } else {
                    //first time opening: fetch both lists and show the combined dialog
                    fetchCategoriesAndClassNumbers();
                }
                break;

            case REQUEST_CODE_CLASS_LETTERS:
                fetchClassLetters();
                break;

            default:
                // No action needed for other codes
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (emptySpinnerDialog != null && emptySpinnerDialog.isShowing()) {
            emptySpinnerDialog.dismiss();
            emptySpinnerDialog = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        bumpGen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bumpGen();
    }
}