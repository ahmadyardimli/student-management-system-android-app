package com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.students;

import static com.example.studentmanagementsystemandroidapp.utils.ButtonUtils.disableButtons;
import static com.example.studentmanagementsystemandroidapp.utils.ButtonUtils.enableButtons;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.BaseAdminActivity;
import com.example.studentmanagementsystemandroidapp.adapters.PlaceholderSpinnerAdapter;
import com.example.studentmanagementsystemandroidapp.adapters.UserAndExamDetailsCommonAdapter;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubGroupApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SubGroupApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SectionApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.SectionApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.CategoryApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.CategoryApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassLetterApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassLetterApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassNumberApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ClassNumberApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ForeignLanguageApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.ForeignLanguageApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.GroupApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.GroupApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.StudentTypeApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.StudentTypeApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentCommunicationSenderStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentCommunicationSenderStatusApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserStatusApiImpl;
import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.data.StatusDataFetcher;
import com.example.studentmanagementsystemandroidapp.data.UserAndExamDetailsDataFetcher;
import com.example.studentmanagementsystemandroidapp.enums.SpinnerType;
import com.example.studentmanagementsystemandroidapp.interfaces.ItemNameProvider;
import com.example.studentmanagementsystemandroidapp.interfaces.data.DataFetchCallback;
import com.example.studentmanagementsystemandroidapp.managers.userandexamdetails.UserAndExamDetailsCommonManager;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Section;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.SubGroup;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Category;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassLetter;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassNumber;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ForeignLanguage;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Group;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.StudentType;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.UserType;
import com.example.studentmanagementsystemandroidapp.models.users.Student;
import com.example.studentmanagementsystemandroidapp.models.users.StudentCommunicationSenderStatus;
import com.example.studentmanagementsystemandroidapp.models.users.UserStatus;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SubGroupRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.SectionRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.CategoryRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassLetterRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ClassNumberRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.ForeignLanguageRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.GroupRequest;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.StudentTypeRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.UserTypeRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.FullStudentRequest;
import com.example.studentmanagementsystemandroidapp.utils.ClassNumberAndLetterSorter;
import com.example.studentmanagementsystemandroidapp.utils.KeyboardUtils;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsActivity extends BaseAdminActivity implements UserAndExamDetailsCommonAdapter.OnUpdateClickListener, CustomSpinner.OnSpinnerEventsListener {
    private Button btnExistingStudents;
    private Button btnAddStudents;
    private FrameLayout dynamicContentPlaceholder;
    private RecyclerView recyclerView;
    private UserAndExamDetailsCommonManager<Student, FullStudentRequest> studentItemManager;
    private CreateStudentRequestViewManager createStudentRequestViewManager;
    private LoadingOverlayUtils loadingOverlayUtils;
    private static final int REQUEST_CODE_STUDENT_TYPE = 1002;
    private static final int REQUEST_CODE_STUDENT_GROUP = 1003;
    private static final int REQUEST_CODE_USER_TYPE = 1001;
    private static final int REQUEST_CODE_SUB_GROUP = 1004;
    private static final int REQUEST_CODE_STUDENT_CATEGORIES = 1005;
    private static final int REQUEST_CODE_STUDENT_SECTIONS = 1006;
    private static final int REQUEST_CODE_STUDENT_FOREIGN_LANGUAGES = 1007;
    private static final int REQUEST_CODE_CLASS_NUMBERS = 1008;
    private static final int REQUEST_CODE_CLASS_LETTERS = 1009;
    private static final int REQUEST_CODE_VIEW_AND_UPDATE_STUDENT = 1010;
    private List<Student> students;
    private final double spinnerItemTextSizePercentage = 0.025;
    private double spinnerHeightPercentage = 0.03;
    private int dynamicContentPlaceholderHeight;
    private boolean studentTypesFetched = false;
    private boolean categoriesFetched = false;
    private boolean foreignLanguagesFetched = false;
    private boolean sectionsFetched = false;
    private boolean groupsFetched = false;
    private boolean subGroupsFetched = false;
    private boolean statusesFetched = false;
    private boolean communicationStatusesFetched = false;
    private boolean classNumbersFetched = false;
    private boolean classLettersFetched = false;
    private boolean isFilterVisible = true;
    private boolean isResettingFilters = false;
    private Integer selectedStudentTypeId = null;
    private Integer selectedCategoryId = null;
    private Integer selectedForeignLanguageId = null;
    private Integer selectedSectionId = null;
    private Integer selectedGroupId = null;
    private Integer selectedSubGroupId = null;
    private Integer selectedCommunicationStatusId = null;
    private Integer selectedClassNumberId = null;
    private Integer selectedClassLetterId = null;
    private Integer selectedUserStatusId = null;
    private Integer selectedUserTypeId = null;
    private FrameLayout recyclerContainer;
    private Call<List<Student>> currentFilterCall;
    private int currentFilterRequestId = 0;
    private Handler filterHandler = new Handler(Looper.getMainLooper());
    private Runnable filterRunnable;
    private static final long FILTER_DELAY_MS = 300; // 300 milliseconds delay
    private final Object genLock = new Object();
    private int pageGen = 0;
    private final List<Call<?>> inFlight = new ArrayList<>();

    // Filter views
    private CustomSpinner spinnerStudentType, spinnerCategory, spinnerForeignLanguage, spinnerSection, spinnerGroup, spinnerSubGroup, spinnerStatus, spinnerCommunicationStatus, spinnerClassNumber, spinnerClassLetter;
    private Button buttonResetFilters;
    private List<StudentType> studentTypes = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<ForeignLanguage> foreignLanguages = new ArrayList<>();
    private List<Section> sections = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<SubGroup> subGroups = new ArrayList<>();
    private List<UserStatus> statuses = new ArrayList<>();
    private List<StudentCommunicationSenderStatus> communicationStatuses = new ArrayList<>();
    private List<ClassNumber> classNumbers = new ArrayList<>();
    private List<ClassLetter> classLetters = new ArrayList<>();
    private View filterView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        setupNavigationAndActionBar("Students", this, "Admins", "Users", "User Details");
        initializeViews();
        studentItemManager = new UserAndExamDetailsCommonManager<>(createStudentApi(), this);
        // Show existing students view initially
        showExistingItemsView();
        initializeButtons();
    }

    private void initializeViews() {
        btnExistingStudents = findViewById(R.id.btnExistingItems);
        btnAddStudents = findViewById(R.id.btnAddItem);
        dynamicContentPlaceholder = findViewById(R.id.dynamicContentPlaceholderLayout);
        loadingOverlayUtils = new LoadingOverlayUtils(this);
        createStudentRequestViewManager = new CreateStudentRequestViewManager(this, btnExistingStudents, btnAddStudents, dynamicContentPlaceholder);
        btnExistingStudents.setText("Existing students");
        btnAddStudents.setText("Add new student");
        ViewStyler.setButtonSize(btnExistingStudents, this, 0.4, 0.07, 0.25);
        ViewStyler.setButtonSize(btnAddStudents, this, 0.4, 0.07, 0.25);
    }

    private void initializeButtons() {
        btnExistingStudents.setOnClickListener(v -> {
            if (createStudentRequestViewManager != null) {
                createStudentRequestViewManager.cancelOngoingWork();
            }
            showExistingItemsView();
        });

        btnAddStudents.setOnClickListener(v -> {
            btnAddStudents.setBackgroundResource(R.drawable.button_category_pressed);
            btnExistingStudents.setBackgroundResource(R.drawable.button_category_default);

            btnExistingStudents.setEnabled(true);
            btnAddStudents.setEnabled(true);

            // kill pending/debounced filter triggers
            if (filterRunnable != null) {
                filterHandler.removeCallbacks(filterRunnable);
                filterRunnable = null;
            }
            currentFilterRequestId++;

            if (currentFilterCall != null && !currentFilterCall.isCanceled()) {
                currentFilterCall.cancel();
                untrack(currentFilterCall);
                currentFilterCall = null;
            }

            bumpGen();
            loadingOverlayUtils.hideLayoutOverlay();

            createStudentRequestViewManager.fetchDataAndShowAddStudent();
        });
    }

    private void showExistingItemsView() {
        bumpGen();
        if (createStudentRequestViewManager != null) {
            createStudentRequestViewManager.cancelOngoingWork();
        }

        KeyboardUtils.hideKeyboard(this);
        btnExistingStudents.setBackgroundResource(R.drawable.button_category_pressed);
        btnAddStudents.setBackgroundResource(R.drawable.button_category_default);

        // clear the placeholder layout
        dynamicContentPlaceholder.removeAllViews();

        // create a vertical LinearLayout as the main container
        LinearLayout mainContainer = new LinearLayout(this);
        mainContainer.setOrientation(LinearLayout.VERTICAL);
        mainContainer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // Inflate the filter layout
        filterView = LayoutInflater.from(this).inflate(R.layout.layout_student_filter, mainContainer, false);
        mainContainer.addView(filterView); // Add filter view at the top

        // Create and configure RecyclerView within a FrameLayout
        recyclerContainer = new FrameLayout(this); // Assign to member variable
        recyclerContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        recyclerContainer.addView(recyclerView);
        mainContainer.addView(recyclerContainer);

        // Add the entire layout to dynamicContentPlaceholder
        dynamicContentPlaceholder.addView(mainContainer);

        // Wait for layout to be measured
        dynamicContentPlaceholder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Now the height is known
                dynamicContentPlaceholderHeight = dynamicContentPlaceholder.getHeight();
                dynamicContentPlaceholder.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                initializeFilterComponents(filterView);
                loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder); // Full-screen overlay during initial load

                // Fetch data for filters here
                fetchStudentTypesForFilter();
                fetchStudentCategoriesForFilter();
                fetchForeignLanguagesForFilter();
                fetchSectionsForFilter();
                fetchGroupsForFilter();
                fetchSubGroupsForFilter();
                fetchStatusesForFilter();
                fetchCommunicationStatusesForFilter();
                fetchClassNumbersForFilter();
                fetchClassLettersForFilter();
            }
        });
    }

    private void checkAndInitializeFiltersIfReady() {
        if (studentTypesFetched && categoriesFetched && foreignLanguagesFetched && sectionsFetched && groupsFetched && subGroupsFetched && statusesFetched && communicationStatusesFetched && classNumbersFetched
                && classLettersFetched ) {
            // Prevent repeated calls and trembling on recycler view to this method by resetting the data-fetched booleans
            reinitializeFilterDataBooleans();
            // initialize filter components since data is ready
            initializeFilterComponents(filterView);
            // Load the main student data
            loadStudentData();
        }
    }

    private void reinitializeFilterDataBooleans(){
        studentTypesFetched = false;
        categoriesFetched = false;
        foreignLanguagesFetched = false;
        sectionsFetched = false;
        groupsFetched = false;
        subGroupsFetched = false;
        statusesFetched = false;
        communicationStatusesFetched = false;
        classNumbersFetched = false;
        classLettersFetched = false;
    }

    private void initializeFilterComponents(View filterView) {
        Button buttonToggleFilters = filterView.findViewById(R.id.buttonToggleFilters);
        ViewStyler.setButtonSize(buttonToggleFilters, this,0.25,0.028, 0.4, 0.01, 0.01);
        View filterContainer = filterView.findViewById(R.id.filterContainer);

        buttonToggleFilters.setOnClickListener(v -> {
            // flip the state
            isFilterVisible = !isFilterVisible;

            // show or hide the container
            filterContainer.setVisibility(isFilterVisible ? View.VISIBLE : View.GONE);

            // update the text
            buttonToggleFilters.setText(isFilterVisible ? "Hide filters" : "Show filters");
        });

        View studentTypeInclude = filterView.findViewById(R.id.spinnerStudentType);
        spinnerStudentType = studentTypeInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(spinnerStudentType, studentTypeInclude, studentTypes, "Student Type", SpinnerType.STUDENT_TYPES, StudentType::getItemName);

        View categoryInclude = filterView.findViewById(R.id.spinnerCategory);
        spinnerCategory = categoryInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(spinnerCategory, categoryInclude, categories, "Category", SpinnerType.STUDENT_CATEGORIES, Category::getItemName);

        View foreignLanguageInclude = filterView.findViewById(R.id.spinnerForeignLanguage);
        spinnerForeignLanguage = foreignLanguageInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(spinnerForeignLanguage, foreignLanguageInclude, foreignLanguages, "Foreign Language", SpinnerType.STUDENT_FOREIGN_LANGUAGES, ForeignLanguage::getItemName);

        View sectionInclude = filterView.findViewById(R.id.spinnerSection);
        spinnerSection = sectionInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(spinnerSection, sectionInclude, sections, "Section", SpinnerType.STUDENT_SECTIONS, Section::getItemName);

        View groupInclude = filterView.findViewById(R.id.spinnerGroup);
        spinnerGroup = groupInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(spinnerGroup, groupInclude, groups, "Group", SpinnerType.STUDENT_GROUPS, Group::getItemName);

        View subGroupInclude = filterView.findViewById(R.id.spinnerSubGroup);
        spinnerSubGroup = subGroupInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(spinnerSubGroup, subGroupInclude, subGroups, "Sub Group", SpinnerType.SUB_GROUPS, SubGroup::getItemName);

        View statusInclude = filterView.findViewById(R.id.spinnerStatus);
        spinnerStatus = statusInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(spinnerStatus, statusInclude, statuses, "Status", SpinnerType.STATUS, UserStatus::getStatusText);

        View communicationStatusInclude = filterView.findViewById(R.id.spinnerCommunicationStatus);
        spinnerCommunicationStatus = communicationStatusInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(spinnerCommunicationStatus, communicationStatusInclude, communicationStatuses, "Message Permission", SpinnerType.COMMUNICATION_STATUS, StudentCommunicationSenderStatus::getStatusText);

        // Class Number
        View classNumberInclude = filterView.findViewById(R.id.spinnerClassNumber);
        spinnerClassNumber = classNumberInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(
                spinnerClassNumber,
                classNumberInclude,
                classNumbers,
                "Class",
                SpinnerType.CLASS_NUMBERS,
                ClassNumber::getItemName
        );

        // Class Letter
        View classLetterInclude = filterView.findViewById(R.id.spinnerClassLetter);
        spinnerClassLetter = classLetterInclude.findViewById(R.id.requestSpinner);
        setUpSpinner(
                spinnerClassLetter,
                classLetterInclude,
                classLetters,
                "Class letter",
                SpinnerType.CLASS_LETTERS,
                ClassLetter::getItemName
        );

        // Dynamically sizing 2 invisible spinners for last single class letter spinner to appear at the beginning
        // not in the center of the line.
        // emptySpinner1 and emptySpinner2 are not used. Just created for UI view.
        View emptySpinnerInclude1 = filterView.findViewById(R.id.noUseSpinner);
        CustomSpinner emptySpinner1 = emptySpinnerInclude1.findViewById(R.id.requestSpinner);
        setUpSpinner(emptySpinner1, emptySpinnerInclude1, new ArrayList<>(), null, null, null) ;

        View emptySpinnerInclude2 = filterView.findViewById(R.id.noUseSpinner2);
        CustomSpinner emptySpinner2 = emptySpinnerInclude2.findViewById(R.id.requestSpinner);
        setUpSpinner(emptySpinner2, emptySpinnerInclude2, new ArrayList<>(), null, null, null) ;

        buttonResetFilters = filterView.findViewById(R.id.buttonResetFilters);
        ViewStyler.setButtonSize(buttonResetFilters, this,0.3,0.04, 0.3, 0.015, 0.015);
        buttonResetFilters.setOnClickListener(v -> resetFilters());
    }

    private void fetchStudentTypesForFilter() {
        StudentTypeApi studentTypeApi = RetrofitInstance.getRetrofitInstance(this).create(StudentTypeApi.class);
        UserAndExamDetailsDataFetcher<StudentType, StudentTypeRequest> dataFetcher = new UserAndExamDetailsDataFetcher<>(this, new StudentTypeApiImpl(studentTypeApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<StudentType>() {
            @Override
            public void onDataFetched(List<StudentType> data) {
                studentTypes.clear();
                if (data != null) {
                    studentTypes.addAll(data);
                }
                studentTypesFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(StudentType item) {}

            @Override
            public void onDataFetchFailed(Throwable t) {
                studentTypesFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch student types: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                studentTypesFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch student types: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchStudentCategoriesForFilter() {
        CategoryApi categoryApi = RetrofitInstance.getRetrofitInstance(this).create(CategoryApi.class);
        UserAndExamDetailsDataFetcher<Category, CategoryRequest> categoriesDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new CategoryApiImpl(categoryApi));
        categoriesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Category>() {
            @Override
            public void onDataFetched(List<Category> data) {
                categories.clear();
                if (data != null) {
                    categories.addAll(data);
                }
                categoriesFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(Category item) {}

            @Override
            public void onDataFetchFailed(Throwable t) {
                categoriesFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch categories: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                categoriesFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch categories: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchForeignLanguagesForFilter() {
        ForeignLanguageApi foreignLanguageApi = RetrofitInstance.getRetrofitInstance(this).create(ForeignLanguageApi.class);
        UserAndExamDetailsDataFetcher<ForeignLanguage, ForeignLanguageRequest> dataFetcher = new UserAndExamDetailsDataFetcher<>(this, new ForeignLanguageApiImpl(foreignLanguageApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<ForeignLanguage>() {
            @Override
            public void onDataFetched(List<ForeignLanguage> data) {
                foreignLanguages.clear();
                if (data != null) {
                    foreignLanguages.addAll(data);
                }
                foreignLanguagesFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(ForeignLanguage item) {}

            @Override
            public void onDataFetchFailed(Throwable t) {
                foreignLanguagesFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch foreign languages: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                foreignLanguagesFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch foreign languages: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSectionsForFilter() {
        SectionApi sectionApi = RetrofitInstance.getRetrofitInstance(this).create(SectionApi.class);
        UserAndExamDetailsDataFetcher<Section, SectionRequest> dataFetcher = new UserAndExamDetailsDataFetcher<>(this, new SectionApiImpl(sectionApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<Section>() {
            @Override
            public void onDataFetched(List<Section> data) {
                sections.clear();
                if (data != null) {
                    sections.addAll(data);
                }
                sectionsFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(Section item) {}

            @Override
            public void onDataFetchFailed(Throwable t) {
                sectionsFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch sections: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                sectionsFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch sections: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchGroupsForFilter() {
        GroupApi groupApi = RetrofitInstance.getRetrofitInstance(this).create(GroupApi.class);
        UserAndExamDetailsDataFetcher<Group, GroupRequest> dataFetcher = new UserAndExamDetailsDataFetcher<>(this, new GroupApiImpl(groupApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<Group>() {
            @Override
            public void onDataFetched(List<Group> data) {
                groups.clear();
                if (data != null) {
                    groups.addAll(data);
                }
                groupsFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(Group item) {}

            @Override
            public void onDataFetchFailed(Throwable t) {
                groupsFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch groups: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                groupsFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch groups: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSubGroupsForFilter() {
        SubGroupApi subGroupApi = RetrofitInstance.getRetrofitInstance(this).create(SubGroupApi.class);
        UserAndExamDetailsDataFetcher<SubGroup, SubGroupRequest> dataFetcher = new UserAndExamDetailsDataFetcher<>(this, new SubGroupApiImpl(subGroupApi));
        dataFetcher.getAllDataFromDatabase(new DataFetchCallback<SubGroup>() {
            @Override
            public void onDataFetched(List<SubGroup> data) {
                subGroups.clear();
                if (data != null) {
                    subGroups.addAll(data);
                }
                subGroupsFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(SubGroup item) {}

            @Override
            public void onDataFetchFailed(Throwable t) {
                subGroupsFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch sub groups: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                subGroupsFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch sub groups: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchStatusesForFilter() {
        UserStatusApi userStatusApi = RetrofitInstance.getRetrofitInstance(this).create(UserStatusApi.class);
        StatusDataFetcher<UserStatus> userStatusDataFetcher = new StatusDataFetcher<>(this);
        userStatusDataFetcher.getAllStatusesFromDatabase(new UserStatusApiImpl(userStatusApi), new DataFetchCallback<UserStatus>() {
            @Override
            public void onDataFetched(List<UserStatus> data) {
                statuses.clear();
                if (data != null) {
                    statuses.addAll(data);
                }
                statusesFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(UserStatus item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                statusesFetched = true;
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                statusesFetched = true;
                // Handle unsuccessful response
            }
        });
    }

    private void fetchCommunicationStatusesForFilter() {
        StudentCommunicationSenderStatusApi studentCommunicationSenderStatusApi = RetrofitInstance.getRetrofitInstance(this).create(StudentCommunicationSenderStatusApi.class);
        StatusDataFetcher<StudentCommunicationSenderStatus> studentStatusDataFetcher = new StatusDataFetcher<>(this);

        studentStatusDataFetcher.getAllStatusesFromDatabase(new StudentCommunicationSenderStatusApiImpl(studentCommunicationSenderStatusApi), new DataFetchCallback<StudentCommunicationSenderStatus>() {
            @Override
            public void onDataFetched(List<StudentCommunicationSenderStatus> data) {
                communicationStatuses.clear();
                if (data != null) {
                    communicationStatuses.addAll(data);
                }
                communicationStatusesFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(StudentCommunicationSenderStatus item) {
                // Not needed
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                communicationStatusesFetched = true;
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                communicationStatusesFetched = true;
                // Handle unsuccessful response
            }
        });
    }

    private void fetchClassNumbersForFilter() {
        ClassNumberApi classNumberApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(ClassNumberApi.class);

        UserAndExamDetailsDataFetcher<ClassNumber, ClassNumberRequest> classNumberDataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new ClassNumberApiImpl(classNumberApi));

        classNumberDataFetcher.getAllDataFromDatabase(new DataFetchCallback<ClassNumber>() {
            @Override
            public void onDataFetched(List<ClassNumber> data) {
                classNumbers.clear();
                if (data != null) {
                    // first sort numbers
                    data = ClassNumberAndLetterSorter.sortClassNumbers(data);
                    classNumbers.addAll(data);
                }
                classNumbersFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(ClassNumber item) { }

            @Override
            public void onDataFetchFailed(Throwable t) {
                classNumbersFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch class numbers: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                classNumbersFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch class numbers: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchClassLettersForFilter() {
        ClassLetterApi classLetterApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(ClassLetterApi.class);

        UserAndExamDetailsDataFetcher<ClassLetter, ClassLetterRequest> classLetterDataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new ClassLetterApiImpl(classLetterApi));

        classLetterDataFetcher.getAllDataFromDatabase(new DataFetchCallback<ClassLetter>() {
            @Override
            public void onDataFetched(List<ClassLetter> data) {
                classLetters.clear();
                if (data != null) {
                    // first sort letters
                    data = ClassNumberAndLetterSorter.sortClassLetters(data);
                    classLetters.addAll(data);
                }
                classLettersFetched = true;
                checkAndInitializeFiltersIfReady();
            }

            @Override
            public void onSingleItemFetched(ClassLetter item) { }

            @Override
            public void onDataFetchFailed(Throwable t) {
                classLettersFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch class letters: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                classLettersFetched = true;
                Toast.makeText(StudentsActivity.this, "Failed to fetch class letters: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private <T> void setUpSpinner(
            CustomSpinner spinner,
            View includeLayout,
            List<T> itemList,
            String placeholderText,
            SpinnerType spinnerType,
            ItemNameProvider<T> itemNameProvider
    ) {
        // Declare the adapter as final to access it inside the listener
        final PlaceholderSpinnerAdapter<T> adapter = new PlaceholderSpinnerAdapter<>(
                this,
                itemList,
                dynamicContentPlaceholderHeight,
                spinnerItemTextSizePercentage,
                spinner,
                placeholderText,
                itemNameProvider
        );

        spinner.setAdapter(adapter);
        setUpBaseSpinnerStyle(spinner, includeLayout, "", false);
        spinner.setTag(spinnerType);

        // Set the initial selection to "No selection"
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirstSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isResettingFilters) return;
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }

                if (position > 0) {
                    T selectedItem = (T) parent.getItemAtPosition(position);
                    Log.d("SpinnerSelection", "Selected " + spinnerType.name() + ": " + itemNameProvider.getItemName(selectedItem));
                    handleSpinnerSelection(spinnerType, selectedItem);
                } else {
                    handleNoSelection(spinnerType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private <T> void handleSpinnerSelection(SpinnerType spinnerType, T selectedItem) {
        switch (spinnerType) {
            case STUDENT_TYPES:
                selectedStudentTypeId = ((StudentType) selectedItem).getId();
                break;
            case STUDENT_CATEGORIES:
                selectedCategoryId = ((Category) selectedItem).getId();
                break;
            case STUDENT_FOREIGN_LANGUAGES:
                selectedForeignLanguageId = ((ForeignLanguage) selectedItem).getId();
                break;
            case STUDENT_SECTIONS:
                selectedSectionId = ((Section) selectedItem).getId();
                break;
            case STUDENT_GROUPS:
                selectedGroupId = ((Group) selectedItem).getId();
                break;
            case SUB_GROUPS:
                selectedSubGroupId = ((SubGroup) selectedItem).getId();
                break;
            case COMMUNICATION_STATUS:
                selectedCommunicationStatusId = ((StudentCommunicationSenderStatus) selectedItem).getId();
                break;
            case CLASS_NUMBERS:
                selectedClassNumberId = ((ClassNumber) selectedItem).getId();
                break;
            case CLASS_LETTERS:
                selectedClassLetterId = ((ClassLetter) selectedItem).getId();
                break;
            case STATUS:
                // This is user status or "UserStatus"
                selectedUserStatusId = ((UserStatus) selectedItem).getId();
                break;
            case USER_TYPES:
                selectedUserTypeId = ((UserType) selectedItem).getId();
                break;
        }

        // Debounce: Remove any pending runnables
        if (filterRunnable != null) {
            filterHandler.removeCallbacks(filterRunnable);
        }

        // Create a new runnable to fetch
        filterRunnable = this::fetchFilteredStudents;

        // Post the runnable with delay
        filterHandler.postDelayed(filterRunnable, FILTER_DELAY_MS);

    }

    private void handleNoSelection(SpinnerType spinnerType) {
        // If user selected "No selection," set to null
        switch (spinnerType) {
            case STUDENT_TYPES:
                selectedStudentTypeId = null;
                break;
            case STUDENT_CATEGORIES:
                selectedCategoryId = null;
                break;
            case STUDENT_FOREIGN_LANGUAGES:
                selectedForeignLanguageId = null;
                break;
            case STUDENT_SECTIONS:
                selectedSectionId = null;
                break;
            case STUDENT_GROUPS:
                selectedGroupId = null;
                break;
            case SUB_GROUPS:
                selectedSubGroupId = null;
                break;
            case COMMUNICATION_STATUS:
                selectedCommunicationStatusId = null;
                break;
            case CLASS_NUMBERS:
                selectedClassNumberId = null;
                break;
            case CLASS_LETTERS:
                selectedClassLetterId = null;
                break;
            case STATUS:
                selectedUserStatusId = null;
                break;
            case USER_TYPES:
                selectedUserTypeId = null;
                break;
        }

        if (filterRunnable != null) {
            filterHandler.removeCallbacks(filterRunnable);
        }

        // Create a new runnable to fetch
        filterRunnable = this::fetchFilteredStudents;

        // Post the runnable with delay
        filterHandler.postDelayed(filterRunnable, FILTER_DELAY_MS);
    }

    private void fetchFilteredStudents() {
        // Increment the request ID for the new request
        currentFilterRequestId++;
        final int requestId = currentFilterRequestId;
        final int gen = pageGen;

        // Show the loading overlay
        loadingOverlayUtils.showLayoutOverlay(recyclerContainer); // Overlay only on RecyclerView

        // Cancel any ongoing filter request
        if (currentFilterCall != null && !currentFilterCall.isCanceled()) {
            currentFilterCall.cancel();
            untrack(currentFilterCall);
        }

        // Initialize the API
        StudentApiImpl studentApiImpl = new StudentApiImpl(
                RetrofitInstance.getRetrofitInstance(this).create(StudentApi.class)
        );

        // Make the new filter call
        currentFilterCall = studentApiImpl.filterStudents(
                selectedStudentTypeId,
                selectedCategoryId,
                selectedForeignLanguageId,
                selectedSectionId,
                selectedGroupId,
                selectedSubGroupId,
                selectedCommunicationStatusId,
                selectedClassNumberId,
                selectedClassLetterId,
                selectedUserStatusId,
                selectedUserTypeId
        );

        track(currentFilterCall);

        // Enqueue the call with callbacks
        currentFilterCall.enqueue(new retrofit2.Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                untrack(call);
                if (!isActiveGen(gen) || requestId != currentFilterRequestId) return;

                // Hide the loading overlay as the latest request has completed
                loadingOverlayUtils.hideLayoutOverlay(); // Hide RecyclerView overlay

                if (!response.isSuccessful()) {
                    if (response.code() == 404) {
                        String errorMsg = null;
                        try {
                            errorMsg = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (errorMsg == null || errorMsg.trim().isEmpty()) {
                            errorMsg = "No students found for the selected criteria.";
                        }
                        showNoDataInRecyclerView(errorMsg);
                    } else {
                        Toast.makeText(StudentsActivity.this,
                                "Filter failed: " + response.message(),
                                Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                // If 200 OK
                List<Student> filteredStudents = response.body();
                if (filteredStudents != null && !filteredStudents.isEmpty()) {
                    students = filteredStudents;
                    setupRecyclerView();
                } else {
                    // 200 but empty list
                    showNoDataInRecyclerView("No students found for the selected criteria.");
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                untrack(call);

                if (!isActiveGen(gen) || requestId != currentFilterRequestId) return;

                // Hide the loading overlay as the latest request has completed
                loadingOverlayUtils.hideLayoutOverlay(); // Hide RecyclerView overlay

                if (call.isCanceled()) {
                    // Do not show a toast if the call was canceled
                    return;
                }
                Toast.makeText(StudentsActivity.this,
                        "Filter failed: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNoDataInRecyclerView(String message) {
        if (students == null) {
            students = new ArrayList<>();
        } else {
            students.clear();
        }

        // If no adapter yet, create one
        if (recyclerView.getAdapter() == null) {
            UserAndExamDetailsCommonAdapter<Student> adapter =
                    new UserAndExamDetailsCommonAdapter<>(students, R.layout.user_exam_details_adapter_item_design);
            adapter.setOnUpdateClickListener(this);
            adapter.setUpdateButtonText("Details");
            recyclerView.setAdapter(adapter);
        }

        // Set the "no data" message
        UserAndExamDetailsCommonAdapter<Student> adapter =
                (UserAndExamDetailsCommonAdapter<Student>) recyclerView.getAdapter();
        adapter.setUpdateButtonText("Details");
        adapter.setNoDataMessage(message);
    }

    private void setUpBaseSpinnerStyle(CustomSpinner spinner, View includeLayout, String spinnerLabelText, boolean showLabel) {
        spinner.setSpinnerEventsListener(this);
        ViewStyler.setSpinnerStyle(spinner, this, 0, 0.29, spinnerHeightPercentage);
        TextView spinnerLabel = includeLayout.findViewById(R.id.requestSpinnerLabelTextView);

        if (showLabel) {
            ViewStyler.setTextViewStyle(spinnerLabel, this, 0, 0.02, 0.29, spinnerLabelText, dynamicContentPlaceholder);
        } else {
            spinnerLabel.setVisibility(View.GONE);
        }

        setDropDownVerticalOffset(spinner);
        setTopMarginForIncludeLayout(includeLayout, 0.02f);
        setTopMarginForIncludeLayout(spinner, 0.005f);
    }

    private void setDropDownVerticalOffset(final CustomSpinner spinner) {
        final ViewTreeObserver.OnGlobalLayoutListener listener =
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        spinner.setDropDownVerticalOffset(spinner.getHeight());
                        ViewTreeObserver vto = spinner.getViewTreeObserver();
                        if (vto.isAlive()) {
                            vto.removeOnGlobalLayoutListener(this);
                        }
                    }
                };

        spinner.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    private void setTopMarginForIncludeLayout(View includeLayout, float topMarginPercentage) {
        int marginTop = (int) (dynamicContentPlaceholderHeight * topMarginPercentage);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) includeLayout.getLayoutParams();
        layoutParams.topMargin = marginTop;
        includeLayout.setLayoutParams(layoutParams);
    }

    private void loadStudentData() {
        final int gen = pageGen;
        StudentApi studentApi = RetrofitInstance
                .getRetrofitInstance(this)
                .create(StudentApi.class);
        UserAndExamDetailsDataFetcher<Student, FullStudentRequest> studentsDataFetcher =
                new UserAndExamDetailsDataFetcher<>(this, new StudentApiImpl(studentApi));

        studentsDataFetcher.getDataResponseFromDatabase(new DataFetchCallback<Student>() {
            @Override
            public void onDataFetched(List<Student> data) {
                if (!isActiveGen(gen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                if (data != null && !data.isEmpty()) {
                    students = data;
                    setupRecyclerView();
                } else {
                    showNoDataInRecyclerView(
                            "Failed to retrieve students."
                                    + getNoItemExistsMessage()
                    );
                    enableButtons(btnAddStudents, btnExistingStudents);
                }
            }

            @Override public void onSingleItemFetched(Student item) { }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                if (!isActiveGen(gen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                // network failure – also show the "no-data" UI
                showNoDataInRecyclerView(
                        "Failed to retrieve students."
                                + getNoItemExistsMessage()
                );
                enableButtons(btnAddStudents, btnExistingStudents);
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                // HTTP error – same treatment
                showNoDataInRecyclerView(
                        "Failed to retrieve students."
                                + getNoItemExistsMessage()
                );
                enableButtons(btnAddStudents, btnExistingStudents);
            }
        });
    }

    private void setupRecyclerView() {
        // Use the existing RecyclerView to set up the adapter
        UserAndExamDetailsCommonAdapter<Student> adapter = new UserAndExamDetailsCommonAdapter<>(students, R.layout.user_exam_details_adapter_item_design);
        adapter.setUpdateButtonText("Details");
        adapter.setOnUpdateClickListener(this);
        recyclerView.setAdapter(adapter);
        enableButtons(btnAddStudents, btnExistingStudents);
    }


    private void handleFetchError(View existingItemsView, Throwable throwable) {
        loadingOverlayUtils.hideLayoutOverlay();
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        enableButtons(btnAddStudents, btnExistingStudents);
    }

    private void handleUnsuccessfulResponse(View existingItemsView, Response response) {
        loadingOverlayUtils.hideLayoutOverlay();
        TextView noItemMessage = existingItemsView.findViewById(R.id.noItemMessage);
        try {
            noItemMessage.setText(response.errorBody().string() + getNoItemExistsMessage());
        } catch (IOException e) {
            Toast.makeText(this, getIOExceptionErrorMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        noItemMessage.setVisibility(View.VISIBLE);
        noItemMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, dynamicContentPlaceholder.getHeight() * 0.05f);
        dynamicContentPlaceholder.addView(existingItemsView);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void resetFilters() {
        // block all spinner callbacks
        isResettingFilters = true;

        // clear any pending debounce callbacks & cancel the Runnable
        filterHandler.removeCallbacksAndMessages(null);
        filterRunnable = null;

        // cancel any in-flight filter API call
        if (currentFilterCall != null && !currentFilterCall.isCanceled()) {
            currentFilterCall.cancel();
        }
        currentFilterCall = null;

        //  clear all stored filter IDs
        selectedStudentTypeId = null;
        selectedCategoryId = null;
        selectedForeignLanguageId = null;
        selectedSectionId = null;
        selectedGroupId = null;
        selectedSubGroupId = null;
        selectedCommunicationStatusId = null;
        selectedClassNumberId = null;
        selectedClassLetterId = null;
        selectedUserStatusId = null;
        selectedUserTypeId = null;

        // reset every spinner back to “position 0” WITHOUT firing its listener
        spinnerStudentType.setSelection(0, false);
        spinnerCategory.setSelection(0, false);
        spinnerForeignLanguage.setSelection(0, false);
        spinnerSection.setSelection(0, false);
        spinnerGroup.setSelection(0, false);
        spinnerSubGroup.setSelection(0, false);
        spinnerStatus.setSelection(0, false);
        spinnerCommunicationStatus.setSelection(0, false);
        spinnerClassNumber.setSelection(0, false);
        spinnerClassLetter.setSelection(0, false);

        // delay re-enabling normal callbacks until *after* all spinner events have flushed
        filterHandler.post(() -> isResettingFilters = false);

        // show the loading overlay and reload the full, unfiltered student list
        loadingOverlayUtils.showLayoutOverlay(recyclerContainer);
        loadStudentData();
    }

    protected String getNoItemExistsMessage() {
        return "You can add a new student by going to the 'Add Student' section.";
    }

    private String getIOExceptionErrorMessage() {
        return "Failed to retrieve students.";
    }

    @Override
    public void onUpdateClick(int position) {
        final int gen = pageGen;
        btnAddStudents.setEnabled(true);
        btnExistingStudents.setEnabled(false);
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);
        // get the clicked student from my list
        Student clickedStudent = students.get(position);
        if (clickedStudent == null) {
            loadingOverlayUtils.hideLayoutOverlay();
            btnExistingStudents.setEnabled(true);
            Toast.makeText(this, "Selected student does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        // fetch the fresh student data from the backend using getItemById
        studentItemManager.getItemById(clickedStudent.getItemId(), new retrofit2.Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (!isActiveGen(gen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                btnExistingStudents.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    Student updatedStudent = response.body();

                    // build an Intent and put extra data (using updatedStudent data)
                    Intent intent = new Intent(StudentsActivity.this, ViewAndUpdateStudentActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("studentId", updatedStudent.getId());
                    intent.putExtra("username", updatedStudent.getUser() != null ? updatedStudent.getUser().getUsername() : null);
                    intent.putExtra("email", updatedStudent.getUser() != null ? updatedStudent.getUser().getEmail() : null);
                    intent.putExtra("status", updatedStudent.getUser() != null && updatedStudent.getUser().getStatus() != null
                            ? updatedStudent.getUser().getStatus().getStatusText() : null);
                    intent.putExtra("createdAt", updatedStudent.getUser() != null ? updatedStudent.getUser().getCreatedAt() : null);
                    intent.putExtra("updatedAt", updatedStudent.getUser() != null ? updatedStudent.getUser().getUpdatedAt() : null);
                    intent.putExtra("name", updatedStudent.getName());
                    intent.putExtra("surname", updatedStudent.getSurname());
                    intent.putExtra("studentCode", updatedStudent.getStudentCode());
                    intent.putExtra("fatherName", updatedStudent.getFatherName());
                    intent.putExtra("mobilePhone", updatedStudent.getMobilePhone());
                    intent.putExtra("schoolClassCode", updatedStudent.getSchoolClassCode());
                    intent.putExtra("address", updatedStudent.getAddress());
                    intent.putExtra("studentType", updatedStudent.getStudentType() != null ? updatedStudent.getStudentType().getType() : null);
                    intent.putExtra("group", updatedStudent.getGroup() != null ? updatedStudent.getGroup().getItemName() : null);
                    intent.putExtra("subGroup", updatedStudent.getSubGroup() != null ? updatedStudent.getSubGroup().getItemName() : null);
                    intent.putExtra("category", updatedStudent.getCategory() != null ? updatedStudent.getCategory().getItemName() : null);
                    intent.putExtra("section", updatedStudent.getSection() != null ? updatedStudent.getSection().getItemName() : null);
                    intent.putExtra("foreignLanguage", updatedStudent.getForeignLanguage() != null ? updatedStudent.getForeignLanguage().getItemName() : null);
                    intent.putExtra("classNumber", updatedStudent.getClassNumber() != null ? String.valueOf(updatedStudent.getClassNumber().getNumberValue()) : null);
                    intent.putExtra("classLetter", updatedStudent.getClassLetter() != null ? updatedStudent.getClassLetter().getItemName() : null);
                    intent.putExtra("communicationStatus", updatedStudent.getCommunicationSenderStatus() != null
                            ? updatedStudent.getCommunicationSenderStatus().getStatusText() : null);

                    // launch the update activity with the refreshed data
                    startActivityForResult(intent, REQUEST_CODE_VIEW_AND_UPDATE_STUDENT);
                } else {
                    try {
                        Toast.makeText(StudentsActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                if (!isActiveGen(gen)) return; // user navigated away

                loadingOverlayUtils.hideLayoutOverlay();
                btnExistingStudents.setEnabled(true);

                if (!call.isCanceled()) {
                    Toast.makeText(StudentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String itemDeletedMessage() {
        return "Student deleted successfully.";
    }

    private UserAndExamDetailsCommonApi<Student, FullStudentRequest> createStudentApi() {
        StudentApi studentApi = RetrofitInstance.getRetrofitInstance(this).create(StudentApi.class);
        return new StudentApiImpl(studentApi);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_STUDENT_TYPE) {
            fetchUpdatedStudentTypes();
        } else if (requestCode == REQUEST_CODE_STUDENT_GROUP) {
            fetchUpdatedGroups();
        } else if (requestCode == REQUEST_CODE_USER_TYPE) {
            fetchUpdatedUserTypes();
        } else if (requestCode == REQUEST_CODE_SUB_GROUP) {
            fetchUpdatedStudentSubGroups();
        } else if (requestCode == REQUEST_CODE_STUDENT_CATEGORIES) {
            fetchUpdatedStudentCategories();
        } else if(requestCode == REQUEST_CODE_STUDENT_SECTIONS) {
            fetchUpdatedStudentSections();
        } else if(requestCode == REQUEST_CODE_STUDENT_FOREIGN_LANGUAGES){
            fetchUpdatedStudentForeignLanguages();
        } else if(requestCode == REQUEST_CODE_CLASS_NUMBERS){
            fetchUpdatedClassNumbers();
        } else if (requestCode == REQUEST_CODE_CLASS_LETTERS){
            fetchUpdatedClassLetters();
        }

        if (requestCode == REQUEST_CODE_VIEW_AND_UPDATE_STUDENT
                && resultCode == Activity.RESULT_OK
                && data != null) {
            int studentId = data.getIntExtra("studentId", -1);

            if (data.hasExtra("updatedStudentName")){
                String updatedStudentName = data.getStringExtra("updatedStudentName");
                updateStudentName(studentId, updatedStudentName);
            }

            if (data.hasExtra("updatedStudentSurname")){
                String updatedStudentSurname = data.getStringExtra("updatedStudentSurname");
                updateStudentSurname(studentId, updatedStudentSurname);
            }

            if (data.hasExtra("updatedStudentStatus")) {
                UserStatus updatedStudentStatus = (UserStatus) data.getSerializableExtra("updatedStudentStatus");
                updateStudentStatus(studentId, updatedStudentStatus);
            }

            if (data.hasExtra("updatedStudentType")){
                String updatedStudentType = data.getStringExtra("updatedStudentType");
                updateStudentType(studentId, updatedStudentType);
            }

            if (data.hasExtra("updatedStudentCategory")){
                String updatedStudentCategory = data.getStringExtra("updatedStudentCategory");
                updateStudentCategory(studentId, updatedStudentCategory);
            }

            if (data.hasExtra("updatedStudentClass")){
                int updatedStudentClassNumber = data.getIntExtra("updatedStudentClass", -1);
                updateStudentClassNumber(studentId, updatedStudentClassNumber);
            }

            if (data.hasExtra("updatedStudentClassLetter")){
                String updatedStudentClassLetter = data.getStringExtra("updatedStudentClassLetter");
                updateStudentClassLetter(studentId, updatedStudentClassLetter);
            }
        }
    }

    private void fetchUpdatedStudentCategories() {
        CategoryApi categoryApi = RetrofitInstance.getRetrofitInstance(this).create(CategoryApi.class);
        UserAndExamDetailsDataFetcher<Category, CategoryRequest> categoriesDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new CategoryApiImpl(categoryApi));
        categoriesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Category>() {
            @Override
            public void onDataFetched(List<Category> data) {
                createStudentRequestViewManager.updateStudentCategories(data);
            }

            @Override
            public void onSingleItemFetched(Category item) {

            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch student categories: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch student categories: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUpdatedStudentTypes() {
        StudentTypeApi studentTypeApi = RetrofitInstance.getRetrofitInstance(this).create(StudentTypeApi.class);
        UserAndExamDetailsDataFetcher<StudentType, StudentTypeRequest> studentTypesDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new StudentTypeApiImpl(studentTypeApi));
        studentTypesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<StudentType>() {
            @Override
            public void onDataFetched(List<StudentType> data) {
                createStudentRequestViewManager.updateStudentTypes(data);
            }

            @Override
            public void onSingleItemFetched(StudentType item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch student types: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch student types: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUpdatedGroups() {
        GroupApi groupApi = RetrofitInstance.getRetrofitInstance(this).create(GroupApi.class);
        UserAndExamDetailsDataFetcher<Group, GroupRequest> groupsDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new GroupApiImpl(groupApi));

        groupsDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Group>() {
            @Override
            public void onDataFetched(List<Group> data) {
                createStudentRequestViewManager.updateGroups(data);
            }

            @Override
            public void onSingleItemFetched(Group item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch groups: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch groups: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUpdatedClassNumbers() {
        ClassNumberApi classNumberApi = RetrofitInstance.getRetrofitInstance(this).create(ClassNumberApi.class);
        UserAndExamDetailsDataFetcher<ClassNumber, ClassNumberRequest> classNumberDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new ClassNumberApiImpl(classNumberApi));

        classNumberDataFetcher.getAllDataFromDatabase(new DataFetchCallback<ClassNumber>() {
            @Override

            public void onDataFetched(List<ClassNumber> data) {
                // first sort numbers
                if (data != null) {
                    data = ClassNumberAndLetterSorter.sortClassNumbers(data);
                }
                createStudentRequestViewManager.updateClassNumbers(data);
            }

            @Override
            public void onSingleItemFetched(ClassNumber item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
            }
        });
    }

    private void fetchUpdatedClassLetters() {
        ClassLetterApi classLetterApi = RetrofitInstance.getRetrofitInstance(this).create(ClassLetterApi.class);
        UserAndExamDetailsDataFetcher<ClassLetter, ClassLetterRequest> classLetterDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new ClassLetterApiImpl(classLetterApi));

        classLetterDataFetcher.getAllDataFromDatabase(new DataFetchCallback<ClassLetter>() {
            @Override
            public void onDataFetched(List<ClassLetter> data) {
                // first sort letters
                if (data != null) {
                    data = ClassNumberAndLetterSorter.sortClassLetters(data);
                }
                createStudentRequestViewManager.updateClassLetters(data);
            }

            @Override
            public void onSingleItemFetched(ClassLetter item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
            }
        });
    }

    private void fetchUpdatedUserTypes() {
        UserTypeApi userTypeApi = RetrofitInstance.getRetrofitInstance(this).create(UserTypeApi.class);
        UserAndExamDetailsDataFetcher<UserType, UserTypeRequest> userTypesDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new UserTypeApiImpl(userTypeApi));
        userTypesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<UserType>() {
            @Override
            public void onDataFetched(List<UserType> data) {
                createStudentRequestViewManager.updateUserTypes(data);
            }

            @Override
            public void onSingleItemFetched(UserType item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch user types: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch user types: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUpdatedStudentSubGroups() {
        SubGroupApi subGroupApi = RetrofitInstance.getRetrofitInstance(this).create(SubGroupApi.class);
        UserAndExamDetailsDataFetcher<SubGroup, SubGroupRequest> studentSubGroupsDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new SubGroupApiImpl(subGroupApi));
        studentSubGroupsDataFetcher.getAllDataFromDatabase(new DataFetchCallback<SubGroup>() {
            @Override
            public void onDataFetched(List<SubGroup> data) {
                createStudentRequestViewManager.updateStudentSubGroups(data);
            }

            @Override
            public void onSingleItemFetched(SubGroup item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch student sub groups: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch student sub groups: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUpdatedStudentSections() {
        SectionApi sectionApi = RetrofitInstance.getRetrofitInstance(this).create(SectionApi.class);
        UserAndExamDetailsDataFetcher<Section, SectionRequest> sectionsDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new SectionApiImpl(sectionApi));

        sectionsDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Section>() {
            @Override
            public void onDataFetched(List<Section> data) {
                createStudentRequestViewManager.updateStudentSections(data);
            }

            @Override
            public void onSingleItemFetched(Section item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
//                Toast.makeText(StudentsActivity.this, "Failed to fetch Sections: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
//                Toast.makeText(StudentsActivity.this, "Failed to fetch Sections: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUpdatedStudentForeignLanguages() {
        ForeignLanguageApi foreignLanguageApi = RetrofitInstance.getRetrofitInstance(this).create(ForeignLanguageApi.class);
        UserAndExamDetailsDataFetcher<ForeignLanguage, ForeignLanguageRequest> foreignLanguagesDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new ForeignLanguageApiImpl(foreignLanguageApi));

        foreignLanguagesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<ForeignLanguage>() {
            @Override
            public void onDataFetched(List<ForeignLanguage> data) {
                createStudentRequestViewManager.updateStudentForeignLanguages(data);
            }

            @Override
            public void onSingleItemFetched(ForeignLanguage item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch foreign languages: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                Toast.makeText(StudentsActivity.this, "Failed to fetch foreign languages: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateStudentName(int studentId, @Nullable String updatedStudentName) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() == studentId) {
                // If incoming is null or empty, clear the name; otherwise set it
                if (updatedStudentName == null || updatedStudentName.isEmpty()) {
                    student.setName(null);
                } else {
                    student.setName(updatedStudentName);
                }
                // Refresh just this row
                recyclerView.getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateStudentSurname(int studentId, @Nullable String updatedStudentSurname) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() == studentId) {
                // If incoming is null or empty, clear the surname; otherwise set it
                if (updatedStudentSurname == null || updatedStudentSurname.isEmpty()) {
                    student.setSurname(null);
                } else {
                    student.setSurname(updatedStudentSurname);
                }
                // Refresh just this row
                recyclerView.getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void updateStudentStatus(int studentId, @Nullable UserStatus updatedStudentStatus) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() == studentId) {
                // Ensure there’s a User
                if (student.getUser() == null) {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                // If incoming status is null, clear it, otherwise set the new status
                student.getUser().setStatus(updatedStudentStatus);

                // Refresh the item in the RecyclerView
                recyclerView.getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void updateStudentCategory(int studentId, String updatedStudentCategory) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() == studentId) {
                // If incoming is empty/null, clear the category
                if (updatedStudentCategory == null || updatedStudentCategory.isEmpty()) {
                    student.setCategory(null);
                } else {
                    // ensure there's a Category object and update it
                    if (student.getCategory() == null) {
                        student.setCategory(new Category());
                    }
                    student.getCategory().setCategory(updatedStudentCategory);
                }

                recyclerView.getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateStudentClassNumber(int studentId, int updatedStudentClassNumber) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() == studentId) {
                // If the incoming number is negative, clear it
                if (updatedStudentClassNumber < 0) {
                    student.setClassNumber(null);
                } else {
                    // ensure there's a ClassNumber instance and set it
                    if (student.getClassNumber() == null) {
                        student.setClassNumber(new ClassNumber());
                    }
                    student.getClassNumber().setNumberValue(updatedStudentClassNumber);
                }
                // this row to refresh
                recyclerView.getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void updateStudentType(int studentId, @Nullable String updatedStudentType) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() == studentId) {
                // If the incoming type is null or empty, clear it
                if (updatedStudentType == null || updatedStudentType.isEmpty()) {
                    student.setStudentType(null);
                } else {
                    // ensure there's a StudentType instance and update it
                    if (student.getStudentType() == null) {
                        student.setStudentType(new StudentType());
                    }
                    student.getStudentType().setType(updatedStudentType);
                }
                // Refresh just this row in the RecyclerView
                recyclerView.getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateStudentClassLetter(int studentId, String updatedStudentClassLetter) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() == studentId) {
                // If the new value is null or empty, remove the letter entirely
                if (updatedStudentClassLetter == null || updatedStudentClassLetter.isEmpty()) {
                    student.setClassLetter(null);
                } else {
                    if (student.getClassLetter() == null) {
                        student.setClassLetter(new ClassLetter());
                    }
                    student.getClassLetter().setLetterValue(updatedStudentClassLetter);
                }
                recyclerView.getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onDeleteClick(int position) {
        final int gen = pageGen;
        disableButtons(btnAddStudents, btnExistingStudents);

        // show a blocking overlay over the list while deleting
        loadingOverlayUtils.showLayoutOverlay(recyclerContainer);

        Student clickedStudentItem = students.get(position);
        if (clickedStudentItem == null) {
            loadingOverlayUtils.hideLayoutOverlay();
            enableButtons(btnAddStudents, btnExistingStudents);
            Toast.makeText(this, "Selected student does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        studentItemManager.deleteItem(getClickedItemId(clickedStudentItem), new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!isActiveGen(gen)) return;

                loadingOverlayUtils.hideLayoutOverlay();
                enableButtons(btnAddStudents, btnExistingStudents);

                if (response.isSuccessful()) {
                    students.remove(position);
                    recyclerView.getAdapter().notifyItemRemoved(position);
                    recyclerView.getAdapter().notifyItemRangeChanged(position, students.size() - position);

                    Toast.makeText(StudentsActivity.this, itemDeletedMessage(), Toast.LENGTH_SHORT).show();

                    if (students.isEmpty()) {
                        showExistingItemsView();
                    }
                } else {
                    String msg = "Deletion failed.";
                    try {
                        if (response.errorBody() != null) msg = response.errorBody().string();
                    } catch (IOException ignored) {}
                    Toast.makeText(StudentsActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (!isActiveGen(gen)) return;

                loadingOverlayUtils.hideLayoutOverlay();
                enableButtons(btnAddStudents, btnExistingStudents);

                if (!call.isCanceled() && t.getMessage() != null) {
                    Toast.makeText(StudentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updatePositions() {
        for (int i = 0; i < students.size(); i++) {
            // update the position of each item in the list
            students.get(i).setPosition(i);
            // notify the adapter about the change in item positions
            recyclerView.getAdapter().notifyItemChanged(i);
        }
    }

    private int getClickedItemId(Student item) {
        return item.getId();
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {

    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {

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

    private void track(Call<?> call) {
        if (call == null) return;
        synchronized (genLock) {
            inFlight.add(call);
        }
    }

    private void untrack(Call<?> call) {
        if (call == null) return;
        synchronized (genLock) {
            inFlight.remove(call);
        }
    }

    @Override protected void onStop() {
        super.onStop();
        bumpGen();
        if (currentFilterCall != null && !currentFilterCall.isCanceled()) currentFilterCall.cancel();
        if (filterRunnable != null) filterHandler.removeCallbacks(filterRunnable);
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        bumpGen();
        filterHandler.removeCallbacksAndMessages(null);
    }
}