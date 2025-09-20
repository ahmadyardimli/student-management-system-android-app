package com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.students;

import static com.example.studentmanagementsystemandroidapp.utils.ButtonUtils.disableButtons;
import static com.example.studentmanagementsystemandroidapp.utils.ButtonUtils.enableButtons;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.SubGroupsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.SectionsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.CategoriesActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.ClassLettersActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.ClassNumbersActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.ForeignLanguagesActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.GroupsActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.StudentTypesActivity;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails.UserTypesActivity;
import com.example.studentmanagementsystemandroidapp.adapters.CommonSpinnerAdapter;
import com.example.studentmanagementsystemandroidapp.adapters.StatusAdapter;
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
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentCommunicationSenderStatusApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.students.StudentCommunicationSenderStatusApiImpl;
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
import com.example.studentmanagementsystemandroidapp.requests.users.StudentRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.UserRequest;
import com.example.studentmanagementsystemandroidapp.utils.ClassNumberAndLetterSorter;
import com.example.studentmanagementsystemandroidapp.utils.EmailValidator;
import com.example.studentmanagementsystemandroidapp.utils.KeyboardUtils;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ScreenUtils;
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

public class CreateStudentRequestViewManager implements CustomSpinner.OnSpinnerEventsListener, SpecialSpinnerItemClickListener {
    private Context context;
    private Button btnExistingStudents;
    private Button btnAddStudents;
    private ViewGroup dynamicContentPlaceholder;

    private int dynamicContentPlaceholderHeight;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText studentNameEditText;
    private EditText studentSurnameEditText;
    private EditText studentCodeEditText;
    private EditText studentFatherNameEditText;
    private EditText studentMobilePhoneNumberEditText;
    private EditText studentSchoolClassCodeEditText;
    private EditText studentAddressEditText;
    private CustomSpinner spinnerUserTypes;
    private CustomSpinner spinnerStatuses;
    private CustomSpinner spinnerStudentTypes;
    private CustomSpinner spinnerStudentGroups;
    private CustomSpinner spinnerStudentSubGroups;
    private CustomSpinner spinnerStudentCategories;
    private CustomSpinner spinnerStudentSections;
    private CustomSpinner spinnerStudentForeignLanguages;
    private CustomSpinner spinnerStudentCommunicationSenderStatuses;
    private CustomSpinner spinnerStudentClassNumbers;
    private CustomSpinner spinnerStudentClassLetters;

    private View usernameErrorMessageView;
    private View passwordErrorMessageView;
    private View emailErrorMessageView;
    private View userTypeErrorMessageView;
    private View userStatusErrorMessageView;
    private View studentNameErrorMessageView;
    private View studentSurnameErrorMessageView;
    private View studentCodeErrorMessageView;
    private View studentFatherNameErrorMessageView;
    private View studentMobilePhoneErrorMessageView;
    private View studentTypeErrorMessageView;
    private View studentGroupErrorMessageView;
    private View studentSubGroupErrorMessageView;
    private View studentCategoryErrorMessageView;
    private View studentSectionErrorMessageView;
    private View studentForeignLanguageErrorMessageView;
    private View studentSchoolClassCodeErrorMessageView;
    private View studentAddressErrorMessageView;
    private View spinnerStudentCommunicationStatusErrorMessageView;
    private View studentClassNumberErrorMessageView;
    private View studentClassLetterErrorMessageView;

    private Button btnAddItem;

    private List<UserType> userTypes;
    private List<UserStatus> userStatuses;
    private List<StudentType> studentTypes;
    private List<Group> groups;
    private List<SubGroup> studentSubGroups;
    private List<Category> studentCategories;
    private List<Section> studentSections;
    private List<ForeignLanguage> studentForeignLanguages;
    private List<StudentCommunicationSenderStatus> studentCommunicationSenderStatuses;
    private List<ClassNumber> classNumbers;
    private List<ClassLetter> classLetters;
    private List<View> errorFields = new ArrayList<>();

    private boolean userTypesAreFetched = false;
    private boolean userStatusesAreFetched = false;
    private boolean studentTypesAreFetched = false;
    private boolean groupsAreFetched = false;
    private boolean studentSubGroupsAreFetched = false;
    private boolean studentCategoriesAreFetched = false;
    private boolean studentSectionsAreFetched = false;
    private boolean studentForeignLanguagesAreFetched = false;
    private boolean studentCommunicationSenderStatusesAreFetched = false;
    private boolean classNumbersAreFetched = false;
    private boolean classLettersAreFetched = false;

    private double spinnerItemTextSizePercentage = 0.03;
    private double spinnerHeightPercentage = 0.035;
    private int dialogLabelTextSize;

    private static final int REQUEST_CODE_STUDENT_TYPE = 1002;
    private static final int REQUEST_CODE_GROUPS = 1003;
    private static final int REQUEST_CODE_USER_TYPES = 1001;
    private static final int REQUEST_CODE_SUB_GROUPS = 1004;
    private static final int REQUEST_CODE_STUDENT_CATEGORIES = 1005;
    private static final int REQUEST_CODE_STUDENT_SECTIONS = 1006;
    private static final int REQUEST_CODE_STUDENT_FOREIGN_LANGUAGES = 1007;
    private static final int REQUEST_CODE_CLASS_NUMBERS = 1008;
    private static final int REQUEST_CODE_CLASS_LETTERS = 1009;
    private boolean hasError = false;
    private Map<String, Boolean> fieldCanBeEmptyMap = new HashMap<>();
    private View firstErrorField;
    private ScrollView addItemScrollView;
    private LoadingOverlayUtils loadingOverlayUtils;
    private List<SpinnerInfo> requiredSpinners;
    private volatile int pageGen = 0;

    public CreateStudentRequestViewManager(Context context, Button btnExistingStudents, Button btnAddStudents, ViewGroup dynamicContentPlaceholder) {
        this.context = context;
        this.btnExistingStudents = btnExistingStudents;
        this.btnAddStudents = btnAddStudents;
        this.dynamicContentPlaceholder = dynamicContentPlaceholder;
        this.userTypes = new ArrayList<>();
        this.userStatuses = new ArrayList<>();
        this.studentTypes = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.studentSubGroups = new ArrayList<>();
        this.studentCategories = new ArrayList<>();
        this.studentSections = new ArrayList<>();
        this.studentForeignLanguages = new ArrayList<>();
        this.studentCommunicationSenderStatuses = new ArrayList<>();
        this.classNumbers = new ArrayList<>();
        this.classLetters = new ArrayList<>();
        this.loadingOverlayUtils = new LoadingOverlayUtils(context);
    }

    public void fetchDataAndShowAddStudent() {
        final int guard = ++pageGen;
        resetFetchedFlags();
        removeExistingViewAndShowLoadingState();

        fetchUserTypes(guard);
        fetchUserStatuses(guard);
        fetchStudentTypes(guard);
        fetchStudentGroups(guard);
        fetchStudentSubGroups(guard);
        fetchStudentCategories(guard);
        fetchStudentSections(guard);
        fetchStudentForeignLanguages(guard);
        fetchStudentCommunicationSenderStatuses(guard);
        fetchClassNumbers(guard);
        fetchClassLetters(guard);
    }
    private boolean isAlive(int guard) { return guard == pageGen; }

    private void resetFetchedFlags() {
        userTypesAreFetched = false;
        userStatusesAreFetched = false;
        studentTypesAreFetched = false;
        groupsAreFetched = false;
        studentSubGroupsAreFetched = false;
        studentCategoriesAreFetched = false;
        studentSectionsAreFetched = false;
        studentForeignLanguagesAreFetched = false;
        studentCommunicationSenderStatusesAreFetched = false;
        classNumbersAreFetched = false;
        classLettersAreFetched = false;
    }

    public void cancelOngoingWork() {
        // invalidate everything that started earlier
        pageGen++;
        loadingOverlayUtils.hideLayoutOverlay();
    }

    private void checkAndShowAddItemView(int guard) {
        if (!isAlive(guard)) return;

        if (userTypesAreFetched
                && userStatusesAreFetched
                && studentTypesAreFetched
                && groupsAreFetched
                && studentSubGroupsAreFetched
                && studentCategoriesAreFetched
                && studentSectionsAreFetched
                && studentForeignLanguagesAreFetched
                && studentCommunicationSenderStatusesAreFetched
                && classNumbersAreFetched
                && classLettersAreFetched) {
            loadingOverlayUtils.hideLayoutOverlay();
            if (!isAlive(guard)) return;
            showAddItemView();
        }
    }

    private void removeExistingViewAndShowLoadingState() {
        // Clear existing views and show loading state
        dynamicContentPlaceholder.removeAllViews();
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);
    }

    private void fetchUserTypes(final int guard) {
        UserTypeApi userTypeApi = RetrofitInstance.getRetrofitInstance(context).create(UserTypeApi.class);
        UserAndExamDetailsDataFetcher<UserType, UserTypeRequest> userTypesDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new UserTypeApiImpl(userTypeApi));
        userTypesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<UserType>() {
            @Override
            public void onDataFetched(List<UserType> data) {
                if (!isAlive(guard)) return;
                userTypes.clear();
                if (data != null) {
                    userTypes.addAll(data);
                }
                userTypesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(UserType item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                userTypesAreFetched = true; // let the screen still progress
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                userTypesAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchUserStatuses(final int guard) {
        UserStatusApi userStatusApi = RetrofitInstance.getRetrofitInstance(context).create(UserStatusApi.class);
        StatusDataFetcher<UserStatus> userStatusDataFetcher = new StatusDataFetcher<>(context);
        userStatusDataFetcher.getAllStatusesFromDatabase(new UserStatusApiImpl(userStatusApi), new DataFetchCallback<UserStatus>() {
            @Override
            public void onDataFetched(List<UserStatus> data) {
                if (!isAlive(guard)) return;
                userStatuses.clear();
                if (data != null) {
                    userStatuses.addAll(data);
                }
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
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                userStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchStudentTypes(final int guard) {
        StudentTypeApi studentTypeApi = RetrofitInstance.getRetrofitInstance(context).create(StudentTypeApi.class);
        UserAndExamDetailsDataFetcher<StudentType, StudentTypeRequest> studentTypesDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new StudentTypeApiImpl(studentTypeApi));
        studentTypesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<StudentType>() {
            @Override
            public void onDataFetched(List<StudentType> data) {
                if (!isAlive(guard)) return;
                studentTypes.clear();
                if (data != null) {
                    studentTypes.addAll(data);
                }
                studentTypesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(StudentType item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                studentTypesAreFetched = true;
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                studentTypesAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchStudentGroups(final int guard) {
        GroupApi groupApi = RetrofitInstance.getRetrofitInstance(context).create(GroupApi.class);
        UserAndExamDetailsDataFetcher<Group, GroupRequest> groupsDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new GroupApiImpl(groupApi));
        groupsDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Group>() {
            @Override
            public void onDataFetched(List<Group> data) {
                if (!isAlive(guard)) return;
                groups.clear();
                if (data != null) {
                    groups.addAll(data);
                }
                groupsAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(Group item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                groupsAreFetched = true;
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                groupsAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchStudentCategories(final int guard) {
        CategoryApi categoryApi = RetrofitInstance.getRetrofitInstance(context).create(CategoryApi.class);
        UserAndExamDetailsDataFetcher<Category, CategoryRequest> studentCategoriesDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new CategoryApiImpl(categoryApi));
        studentCategoriesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Category>() {
            @Override
            public void onDataFetched(List<Category> data) {
                if (!isAlive(guard)) return;
                studentCategories.clear();
                if (data != null) {
                    studentCategories.addAll(data);
                }
                studentCategoriesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(Category item) {

            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                studentCategoriesAreFetched = true;
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                studentCategoriesAreFetched = true;
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchStudentSections(final int guard) {
        SectionApi sectionApi = RetrofitInstance.getRetrofitInstance(context).create(SectionApi.class);
        UserAndExamDetailsDataFetcher<Section, SectionRequest> sectionDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new SectionApiImpl(sectionApi));
        sectionDataFetcher.getAllDataFromDatabase(new DataFetchCallback<Section>() {
            @Override
            public void onDataFetched(List<Section> data) {
                if (!isAlive(guard)) return;
                studentSections.clear();
                if (data != null) {
                    studentSections.addAll(data);
                }
                studentSectionsAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(Section item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                studentSectionsAreFetched = true;
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                studentSectionsAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchStudentSubGroups(final int guard) {
        SubGroupApi subGroupApi = RetrofitInstance.getRetrofitInstance(context).create(SubGroupApi.class);
        UserAndExamDetailsDataFetcher<SubGroup, SubGroupRequest> studentSubGroupsDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new SubGroupApiImpl(subGroupApi));
        studentSubGroupsDataFetcher.getAllDataFromDatabase(new DataFetchCallback<SubGroup>() {
            @Override
            public void onDataFetched(List<SubGroup> data) {
                if (!isAlive(guard)) return;
                studentSubGroups.clear();
                if (data != null) {
                    studentSubGroups.addAll(data);
                }
                studentSubGroupsAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(SubGroup item) {

            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                studentSubGroupsAreFetched = true;
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                studentSubGroupsAreFetched = true;
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchStudentForeignLanguages(final int guard) {
        ForeignLanguageApi foreignLanguageApi = RetrofitInstance.getRetrofitInstance(context).create(ForeignLanguageApi.class);
        UserAndExamDetailsDataFetcher<ForeignLanguage, ForeignLanguageRequest> foreignLanguageDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new ForeignLanguageApiImpl(foreignLanguageApi));
        foreignLanguageDataFetcher.getAllDataFromDatabase(new DataFetchCallback<ForeignLanguage>() {
            @Override
            public void onDataFetched(List<ForeignLanguage> data) {
                if (!isAlive(guard)) return;
                studentForeignLanguages.clear();
                if (data != null) {
                    studentForeignLanguages.addAll(data);
                }
                studentForeignLanguagesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(ForeignLanguage item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                studentForeignLanguagesAreFetched = true;
                checkAndShowAddItemView(guard);
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                studentForeignLanguagesAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchStudentCommunicationSenderStatuses(final int guard) {
        StudentCommunicationSenderStatusApi studentCommunicationSenderStatusApi = RetrofitInstance.getRetrofitInstance(context).create(StudentCommunicationSenderStatusApi.class);
        StatusDataFetcher<StudentCommunicationSenderStatus> studentStatusDataFetcher = new StatusDataFetcher<>(context);

        studentStatusDataFetcher.getAllStatusesFromDatabase(new StudentCommunicationSenderStatusApiImpl(studentCommunicationSenderStatusApi), new DataFetchCallback<StudentCommunicationSenderStatus>() {
            @Override
            public void onDataFetched(List<StudentCommunicationSenderStatus> data) {
                if (!isAlive(guard)) return;
                studentCommunicationSenderStatuses.clear();
                if (data != null) {
                    studentCommunicationSenderStatuses.addAll(data);
                }
                studentCommunicationSenderStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(StudentCommunicationSenderStatus item) {
                // Not needed
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                studentCommunicationSenderStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                studentCommunicationSenderStatusesAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchClassNumbers(final int guard) {
        ClassNumberApi classNumberApi = RetrofitInstance.getRetrofitInstance(context).create(ClassNumberApi.class);
        UserAndExamDetailsDataFetcher<ClassNumber, ClassNumberRequest> classNumberDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new ClassNumberApiImpl(classNumberApi));

        classNumberDataFetcher.getAllDataFromDatabase(new DataFetchCallback<ClassNumber>() {
            @Override
            public void onDataFetched(List<ClassNumber> data) {
                if (!isAlive(guard)) return;
                classNumbers.clear();
                if (data != null) {
                    // first sort numbers
                    data = ClassNumberAndLetterSorter.sortClassNumbers(data);
                    classNumbers.addAll(data);
                }
                classNumbersAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(ClassNumber item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                classNumbersAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                classNumbersAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    private void fetchClassLetters(final int guard) {
        ClassLetterApi classLetterApi = RetrofitInstance.getRetrofitInstance(context).create(ClassLetterApi.class);
        UserAndExamDetailsDataFetcher<ClassLetter, ClassLetterRequest> classLetterDataFetcher = new UserAndExamDetailsDataFetcher<>(context, new ClassLetterApiImpl(classLetterApi));

        classLetterDataFetcher.getAllDataFromDatabase(new DataFetchCallback<ClassLetter>() {
            @Override
            public void onDataFetched(List<ClassLetter> data) {
                if (!isAlive(guard)) return;
                classLetters.clear();
                if (data != null) {
                    // first sort letters
                    data = ClassNumberAndLetterSorter.sortClassLetters(data);
                    classLetters.addAll(data);
                }
                classLettersAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onSingleItemFetched(ClassLetter item) {
            }

            @Override
            public void onDataFetchFailed(Throwable t) {
                if (!isAlive(guard)) return;
                classLettersAreFetched = true;
                checkAndShowAddItemView(guard);
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isAlive(guard)) return;
                classLettersAreFetched = true;
                checkAndShowAddItemView(guard);
            }
        });
    }

    public void updateUserTypes(List<UserType> userTypes) {
        this.userTypes.clear();
        if (userTypes != null) {
            this.userTypes.addAll(userTypes);
        }
        updateSpinnerAdapter(spinnerUserTypes, this.userTypes, false);
    }

    public void updateStudentTypes(List<StudentType> studentTypes) {
        this.studentTypes.clear();
        if (studentTypes != null) {
            this.studentTypes.addAll(studentTypes);
        }
        updateSpinnerAdapter(spinnerStudentTypes, this.studentTypes, false);
    }

    public void updateStudentCategories(List<Category> categories) {
        this.studentCategories.clear();
        if (categories != null) {
            this.studentCategories.addAll(categories);
        }
        updateSpinnerAdapter(spinnerStudentCategories, this.studentCategories, false);
    }

    public void updateGroups(List<Group> groups) {
        this.groups.clear();
        if (groups != null) {
            this.groups.addAll(groups);
        }
        updateSpinnerAdapter(spinnerStudentGroups, this.groups, true);
    }

    public void updateStudentSubGroups(List<SubGroup> subGroups) {
        this.studentSubGroups.clear();
        if (subGroups != null) {
            this.studentSubGroups.addAll(subGroups);
        }
        updateSpinnerAdapter(spinnerStudentSubGroups, this.studentSubGroups, true);
    }

    public void updateStudentSections(List<Section> sectionList) {
        this.studentSections.clear();
        if (sectionList != null) {
            this.studentSections.addAll(sectionList);
        }
        updateSpinnerAdapter(spinnerStudentSections, this.studentSections, false);
    }

    public void updateStudentForeignLanguages(List<ForeignLanguage> foreignLanguageList) {
        this.studentForeignLanguages.clear();
        if (foreignLanguageList != null) {
            this.studentForeignLanguages.addAll(foreignLanguageList);
        }
        updateSpinnerAdapter(spinnerStudentForeignLanguages, this.studentForeignLanguages, false);
    }

    public void updateClassNumbers(List<ClassNumber> classNumbers) {
        this.classNumbers.clear();
        if (classNumbers != null) {
            this.classNumbers.addAll(classNumbers);
        }
        updateSpinnerAdapter(spinnerStudentClassNumbers, this.classNumbers, false);
    }

    public void updateClassLetters(List<ClassLetter> classLetters) {
        this.classLetters.clear();
        if (classLetters != null) {
            this.classLetters.addAll(classLetters);
        }
        updateSpinnerAdapter(spinnerStudentClassLetters, this.classLetters, true);
    }


    private <T extends RecyclerViewItemPositionable> void updateSpinnerAdapter(CustomSpinner spinner, List<T> items, boolean allowEmptyOption) {
        // Save the previously selected item's ID
        T previousSelectedItem = (T) spinner.getSelectedItem();
        Integer previousSelectedItemId = null;
        if (previousSelectedItem != null) {
            previousSelectedItemId = previousSelectedItem.getItemId();
        }

        // Update the adapter
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

        // Restore the previous selection using the adapter's method
        int position = adapter.getPositionOfSelectedItem();
        if (position >= 0) {
            spinner.setSelection(position);
        } else if (allowEmptyOption) {
            spinner.setSelection(0); // Select the empty option if allowed
        }

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

    private void resetSpinnerInitializationFlags() {
        for (SpinnerInfo spinnerInfo : requiredSpinners) {
            spinnerInfo.setInitialized(false);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private <T> void setSpinnerEmptyListener(CustomSpinner spinner, List<T> detailList, SpinnerType spinnerType, boolean allowEmptyOption) {
        spinner.setOnTouchListener((v, event) -> {
            // If the spinner allows empty option or contains items, do not show dialog. just open the spinner
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (allowEmptyOption || !detailList.isEmpty()) {
                    spinner.performClick();
                    return true;
                } else {
                    // Otherwise, show dialog if no items exist and empty option is not allowed
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

        String dialogMessageText;
        Intent intent;

        if (spinnerType == SpinnerType.STUDENT_TYPES) {
            dialogMessageText = "Student type is not available. It must be added for new student registration. Tap 'Add' to go to the 'Student Type' page and add one.";
            intent = new Intent(context, StudentTypesActivity.class);
        } else if (spinnerType == SpinnerType.STUDENT_GROUPS) {
            dialogMessageText = "Group is not available. It must be added for new student registration. Tap 'Add' to go to the 'Group' page and add one.";
            intent = new Intent(context, GroupsActivity.class);
        } else if (spinnerType == SpinnerType.USER_TYPES) {
            dialogMessageText = "User type is not available. It must be added for new student registration. Tap 'Add' to go to the 'User Type' page and add one.";
            intent = new Intent(context, UserTypesActivity.class);
        } else if (spinnerType == SpinnerType.SUB_GROUPS) {
            dialogMessageText = "Subgroup is not available. It must be added for new student registration. Tap 'Add' to go to the 'Subgroup' page and add one.";
            intent = new Intent(context, SubGroupsActivity.class);
        } else if (spinnerType == SpinnerType.STUDENT_CATEGORIES) {
            dialogMessageText = "Student category is not available. It must be added for new student registration. Tap 'Add' to go to the 'Category' page and add one.";
            intent = new Intent(context, CategoriesActivity.class);
        } else if (spinnerType == SpinnerType.STUDENT_SECTIONS) {
            dialogMessageText = "Section is not available. It must be added for new student registration. Tap 'Add' to go to the 'Section' page and add one.";
            intent = new Intent(context, SectionsActivity.class);
        } else if (spinnerType == SpinnerType.STUDENT_FOREIGN_LANGUAGES) {
            dialogMessageText = "Foreign language is not available. It must be added for new student registration. Tap 'Add' to go to the 'Foreign Language' page and add one.";
            intent = new Intent(context, ForeignLanguagesActivity.class);
        } else if (spinnerType == SpinnerType.CLASS_NUMBERS) {
            dialogMessageText = "Class number is not available. It must be added for new student registration. Tap 'Add' to go to the 'Class Number' page and add one.";
            intent = new Intent(context, ClassNumbersActivity.class);
        } else {
            dialogMessageText = "No data available.";
            intent = null;
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
                int requestCode;
                if (spinnerType == SpinnerType.STUDENT_TYPES) {
                    requestCode = REQUEST_CODE_STUDENT_TYPE;
                } else if (spinnerType == SpinnerType.STUDENT_GROUPS) {
                    requestCode = REQUEST_CODE_GROUPS;
                } else if (spinnerType == SpinnerType.SUB_GROUPS) {
                    requestCode = REQUEST_CODE_SUB_GROUPS;
                } else if (spinnerType == SpinnerType.STUDENT_CATEGORIES) {
                    requestCode = REQUEST_CODE_STUDENT_CATEGORIES;
                } else if(spinnerType == SpinnerType.STUDENT_SECTIONS){
                    requestCode = REQUEST_CODE_STUDENT_SECTIONS;
                } else if(spinnerType == SpinnerType.STUDENT_FOREIGN_LANGUAGES) {
                   requestCode = REQUEST_CODE_STUDENT_FOREIGN_LANGUAGES;
                } else if(spinnerType == SpinnerType.CLASS_NUMBERS) {
                    requestCode = REQUEST_CODE_CLASS_NUMBERS;
                } else if(spinnerType == SpinnerType.CLASS_LETTERS) {
                    requestCode = REQUEST_CODE_CLASS_LETTERS ;
                } else {
                    requestCode = REQUEST_CODE_USER_TYPES;
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

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility"})
    private void showAddItemView() {
        KeyboardUtils.hideKeyboard(context);
        dynamicContentPlaceholder.removeAllViews();
        View addItemView = LayoutInflater.from(context).inflate(R.layout.layout_add_item, dynamicContentPlaceholder, false);
        addItemScrollView = addItemView.findViewById(R.id.addItemScrollView);
        LinearLayout dynamicRequestItemContainer = addItemView.findViewById(R.id.dynamicFieldsContainer);
        View addRequestView = LayoutInflater.from(dynamicRequestItemContainer.getContext()).inflate(R.layout.layout_student_request, dynamicRequestItemContainer, false);
        dynamicContentPlaceholderHeight = dynamicContentPlaceholder.getHeight();

        // Set starting point of add view item (e.g., 5% from top of dynamicContentPlaceholder)
        float topMarginPercentage = 0.05f; // Adjust the percentage as needed
        int topMargin = (int) (dynamicContentPlaceholderHeight * topMarginPercentage);
        ViewStyler.setAddItemViewStartWithPercentage(topMargin, addItemView);

        // set up username field for student
        setUpField(addRequestView, R.id.studentUsernameInclude, "Create Username *", "Username", 0, "Username", false);
        usernameEditText = addRequestView.findViewById(R.id.studentUsernameInclude).findViewById(R.id.editTextItem);
        usernameErrorMessageView = addRequestView.findViewById(R.id.studentUsernameInclude).findViewById(R.id.errorMessageAddItem);

        // set up password field for student
        setUpField(addRequestView, R.id.studentPasswordInclude, "Create password *", "Password", 0.03f, "Password", false);
        passwordEditText = addRequestView.findViewById(R.id.studentPasswordInclude).findViewById(R.id.editTextItem);
        passwordErrorMessageView = addRequestView.findViewById(R.id.studentPasswordInclude).findViewById(R.id.errorMessageAddItem);

        // set up email field for student
        setUpField(addRequestView, R.id.studentEmailInclude, "Email (optional)", "Email", 0.03f, "Email", true);
        emailEditText = addRequestView.findViewById(R.id.studentEmailInclude).findViewById(R.id.editTextItem);
        emailErrorMessageView = addRequestView.findViewById(R.id.studentEmailInclude).findViewById(R.id.errorMessageAddItem);

        // set up user type for student
        View userTypeInclude = addRequestView.findViewById(R.id.userTypeInclude);
        spinnerUserTypes = userTypeInclude.findViewById(R.id.requestSpinner);
        userTypeErrorMessageView = userTypeInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerUserTypes, userTypeInclude, userTypes, "User Type (select 'Student') *", SpinnerType.USER_TYPES, false);
        setSpinnerEmptyListener(spinnerUserTypes, userTypes, SpinnerType.USER_TYPES, false);

        // set up user status for student
        View userStatusInclude = addRequestView.findViewById(R.id.userStatusInclude);
        spinnerStatuses = userStatusInclude.findViewById(R.id.requestSpinner);
        userStatusErrorMessageView = userStatusInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpStatusSpinner(spinnerStatuses, userStatusInclude, userStatuses, "Status *");

        // set up student communication status for student
        View studentCommunicationSenderStatusInclude = addRequestView.findViewById(R.id.studentCommunicationSenderStatusInclude);
        spinnerStudentCommunicationSenderStatuses = studentCommunicationSenderStatusInclude.findViewById(R.id.requestSpinner);
        spinnerStudentCommunicationStatusErrorMessageView = studentCommunicationSenderStatusInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpStatusSpinner(spinnerStudentCommunicationSenderStatuses, studentCommunicationSenderStatusInclude, studentCommunicationSenderStatuses, "Message Sending Permission *");

        // set up student name
        setUpField(addRequestView, R.id.studentNameInclude, "Name *", "Student name", 0.03f, "Name", false);
        studentNameEditText = addRequestView.findViewById(R.id.studentNameInclude).findViewById(R.id.editTextItem);
        studentNameErrorMessageView = addRequestView.findViewById(R.id.studentNameInclude).findViewById(R.id.errorMessageAddItem);

        // set up student surname
        setUpField(addRequestView, R.id.studentSurnameInclude, "Surname *", "Student Surname", 0.03f, "Surname", false);
        studentSurnameEditText = addRequestView.findViewById(R.id.studentSurnameInclude).findViewById(R.id.editTextItem);
        studentSurnameErrorMessageView = addRequestView.findViewById(R.id.studentSurnameInclude).findViewById(R.id.errorMessageAddItem);

        // set up student code
        setUpField(addRequestView, R.id.studentCodeInclude, "Student Code *", "Student code", 0.03f, "Student code", false);
        studentCodeEditText = addRequestView.findViewById(R.id.studentCodeInclude).findViewById(R.id.editTextItem);
        studentCodeErrorMessageView = addRequestView.findViewById(R.id.studentCodeInclude).findViewById(R.id.errorMessageAddItem);

        // set up student father name
        setUpField(addRequestView, R.id.studentFatherNameInclude, "Father's Name *", "Father's name", 0.03f, "Father's name", false);
        studentFatherNameEditText = addRequestView.findViewById(R.id.studentFatherNameInclude).findViewById(R.id.editTextItem);
        studentFatherNameErrorMessageView = addRequestView.findViewById(R.id.studentFatherNameInclude).findViewById(R.id.errorMessageAddItem);

        // set up mobile phone number
        setUpField(addRequestView, R.id.studentMobilePhoneInclude, "Mobile Number", "Mobile Number", 0.03f, "Mobile number", true);
        studentMobilePhoneNumberEditText = addRequestView.findViewById(R.id.studentMobilePhoneInclude).findViewById(R.id.editTextItem);
        studentMobilePhoneErrorMessageView = addRequestView.findViewById(R.id.studentMobilePhoneInclude).findViewById(R.id.errorMessageAddItem);

        // set up student school class code
        setUpField(addRequestView, R.id.studentSchoolClassCodeInclude, "School Class Code", "code", 0.03f, "School class code", true);
        studentSchoolClassCodeEditText = addRequestView.findViewById(R.id.studentSchoolClassCodeInclude).findViewById(R.id.editTextItem);
        studentSchoolClassCodeErrorMessageView = addRequestView.findViewById(R.id.studentSchoolClassCodeInclude).findViewById(R.id.errorMessageAddItem);

        // set up student address
        setUpField(addRequestView, R.id.studentAddressInclude, "Address", "Address", 0.03f, "Address", true);
        studentAddressEditText = addRequestView.findViewById(R.id.studentAddressInclude).findViewById(R.id.editTextItem);
        studentAddressErrorMessageView = addRequestView.findViewById(R.id.studentAddressInclude).findViewById(R.id.errorMessageAddItem);

        // set up student class number spinner
        View studentClassNumberInclude = addRequestView.findViewById(R.id.studentClassNumberInclude);
        spinnerStudentClassNumbers = studentClassNumberInclude.findViewById(R.id.requestSpinner);
        studentClassNumberErrorMessageView = studentClassNumberInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerStudentClassNumbers, studentClassNumberInclude, classNumbers, "Select Class Number *", SpinnerType.CLASS_NUMBERS, false);
        setSpinnerEmptyListener(spinnerStudentClassNumbers, classNumbers, SpinnerType.CLASS_NUMBERS, false);

        // set up student class letter spinner
        View studentClassLetterInclude = addRequestView.findViewById(R.id.studentClassLetterInclude);
        spinnerStudentClassLetters = studentClassLetterInclude.findViewById(R.id.requestSpinner);
        studentClassLetterErrorMessageView = studentClassLetterInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerStudentClassLetters, studentClassLetterInclude, classLetters, "Select Class Letter", SpinnerType.CLASS_LETTERS, true);
        setSpinnerEmptyListener(spinnerStudentClassLetters, classLetters, SpinnerType.CLASS_LETTERS, true);

        // set up student type
        View studentTypeInclude = addRequestView.findViewById(R.id.studentTypeInclude);
        spinnerStudentTypes = studentTypeInclude.findViewById(R.id.requestSpinner);
        studentTypeErrorMessageView = studentTypeInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerStudentTypes, studentTypeInclude, studentTypes, "Select Student Type *", SpinnerType.STUDENT_TYPES, false);
        setSpinnerEmptyListener(spinnerStudentTypes, studentTypes, SpinnerType.STUDENT_TYPES, false);

        // set up student group
        View studentGroupInclude = addRequestView.findViewById(R.id.studentGroupInclude);
        spinnerStudentGroups = studentGroupInclude.findViewById(R.id.requestSpinner);
        studentGroupErrorMessageView = studentGroupInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerStudentGroups, studentGroupInclude, groups, "Add Group", SpinnerType.STUDENT_GROUPS, true);
        setSpinnerEmptyListener(spinnerStudentGroups, groups, SpinnerType.STUDENT_GROUPS, true);

        // set up student sub group
        View studentSubGroupInclude = addRequestView.findViewById(R.id.studentSubGroupInclude);
        spinnerStudentSubGroups = studentSubGroupInclude.findViewById(R.id.requestSpinner);
        studentSubGroupErrorMessageView = studentSubGroupInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerStudentSubGroups, studentSubGroupInclude, studentSubGroups, "Add Sub Group", SpinnerType.SUB_GROUPS, true);
        setSpinnerEmptyListener(spinnerStudentSubGroups, studentSubGroups, SpinnerType.SUB_GROUPS, true);

        // set up student category
        View studentCategoryInclude = addRequestView.findViewById(R.id.studentCategoryInclude);
        spinnerStudentCategories = studentCategoryInclude.findViewById(R.id.requestSpinner);
        studentCategoryErrorMessageView = studentCategoryInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerStudentCategories, studentCategoryInclude, studentCategories, "Add Category *", SpinnerType.STUDENT_CATEGORIES, false);
        setSpinnerEmptyListener(spinnerStudentCategories, studentCategories, SpinnerType.STUDENT_CATEGORIES, false);

        // set up sections
        View studentSectionInclude = addRequestView.findViewById(R.id.studentSectionInclude);
        spinnerStudentSections = studentSectionInclude.findViewById(R.id.requestSpinner);
        studentSectionErrorMessageView = studentSectionInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerStudentSections, studentSectionInclude, studentSections, "Add Section *", SpinnerType.STUDENT_SECTIONS, false);
        setSpinnerEmptyListener(spinnerStudentSections, studentSections, SpinnerType.STUDENT_SECTIONS, false);

        // set up foreign language spinner
        View studentForeignLanguageInclude = addRequestView.findViewById(R.id.studentForeignLanguageInclude);
        spinnerStudentForeignLanguages = studentForeignLanguageInclude.findViewById(R.id.requestSpinner);
        studentForeignLanguageErrorMessageView = studentForeignLanguageInclude.findViewById(R.id.errorMessageRequestSpinner);
        setUpSpinner(spinnerStudentForeignLanguages, studentForeignLanguageInclude, studentForeignLanguages, "Add Foreign Language *", SpinnerType.STUDENT_FOREIGN_LANGUAGES, false);
        setSpinnerEmptyListener(spinnerStudentForeignLanguages, studentForeignLanguages, SpinnerType.STUDENT_FOREIGN_LANGUAGES, false);

        // Initialize the list
        requiredSpinners = new ArrayList<>();

        // For spinnerUserTypes
        requiredSpinners.add(new SpinnerInfo(spinnerUserTypes, userTypeErrorMessageView, "User type must be selected"));
        // For spinnerStudentTypes
        requiredSpinners.add(new SpinnerInfo(spinnerStudentTypes, studentTypeErrorMessageView, "Student type must be selected"));
        // For spinnerStudentCategories
        requiredSpinners.add(new SpinnerInfo(spinnerStudentCategories, studentCategoryErrorMessageView, "Category must be selected"));
        // For spinnerStudentSections
        requiredSpinners.add(new SpinnerInfo(spinnerStudentSections, studentSectionErrorMessageView, "Section must be selected"));
        // For spinnerStudentForeignLanguages
        requiredSpinners.add(new SpinnerInfo(spinnerStudentForeignLanguages, studentForeignLanguageErrorMessageView, "Foreign language must be selected"));
        // For spinnerStatuses
        requiredSpinners.add(new SpinnerInfo(spinnerStatuses, userStatusErrorMessageView, "Status must be selected"));
        // For spinnerStudentCommunicationSenderStatuses
        requiredSpinners.add(new SpinnerInfo(spinnerStudentCommunicationSenderStatuses, spinnerStudentCommunicationStatusErrorMessageView, "Message sending status must be selected"));
        requiredSpinners.add(new SpinnerInfo(spinnerStudentClassNumbers, studentClassNumberErrorMessageView, "Class number must be selected"));


        // Set up listeners for each required spinner
        for (SpinnerInfo spinnerInfo : requiredSpinners) {
            setUpSpinnerValidation(spinnerInfo);
        }

        // hide the keyboard when touched outside EditText fields
        addItemScrollView.setOnTouchListener((v, event) -> {
            KeyboardUtils.hideKeyboard(context);
            return false;
        });

        btnAddItem = addItemView.findViewById(R.id.buttonAddItem);
        dynamicRequestItemContainer.addView(addRequestView);
        dynamicContentPlaceholder.addView(addItemView);
        ViewStyler.setButtonSize(btnAddItem, context, 0.43, 0.06, 0.27, 0.025, 0.025);


        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBtnAddItem();
            }
        });
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
//            if (firstErrorField == null) {
//                firstErrorField = spinnerInfo.getSpinner();
//            }
        } else {
            // Special validation for spinnerUserTypes needing to be "Şagird"
            if (spinnerInfo.getSpinner() == spinnerUserTypes) {
                UserType selectedUserType = (UserType) selectedItem;
                if (!selectedUserType.getType().trim().equalsIgnoreCase("Student")) {
                    showErrorMessage(spinnerInfo.getErrorMessageView(), "User type must be \"Student\"");
                    hasError = true;
                    errorFields.add(spinnerInfo.getSpinner());
//                    if (firstErrorField == null) {
//                        firstErrorField = spinnerInfo.getSpinner();
//                    }
                } else {
                    hideErrorMessage(spinnerInfo.getErrorMessageView());
                }
            } else {
                hideErrorMessage(spinnerInfo.getErrorMessageView());
            }
        }
    }

    private <T extends RecyclerViewItemPositionable> void setUpSpinner(CustomSpinner spinner, View includeLayout, List<T> itemList, String spinnerLabelText, SpinnerType spinnerType, boolean allowEmptyOption) {
        CommonSpinnerAdapter<T> adapter = new CommonSpinnerAdapter<>(
                context,
                itemList,
                dynamicContentPlaceholderHeight,
                spinnerItemTextSizePercentage,
                this,
                spinner,
                allowEmptyOption,
                true,
                null
        );
        spinner.setAdapter(adapter);
        setUpBaseSpinnerStyle(spinner, includeLayout, spinnerLabelText);
        spinner.setTag(spinnerType);

        // Set up listener to handle empty option and prevent dialog from opening when allowed
        setSpinnerEmptyListener(spinner, itemList, spinnerType, allowEmptyOption);
    }

    private <T extends Status> void setUpStatusSpinner(CustomSpinner spinner, View includeLayout, List<T> statusList, String spinnerLabelText) {
        StatusAdapter<T> statusAdapter = new StatusAdapter<>(
                context,
                statusList,
                dynamicContentPlaceholderHeight,
                spinnerItemTextSizePercentage,
                null,
                spinner
        );
        spinner.setAdapter(statusAdapter);
        setUpBaseSpinnerStyle(spinner, includeLayout, spinnerLabelText);
    }

    private void setUpBaseSpinnerStyle(CustomSpinner spinner, View includeLayout, String spinnerLabelText) {
        spinner.setSpinnerEventsListener(this);
        ViewStyler.setSpinnerStyle(spinner, context, 0.001, 0.43, spinnerHeightPercentage);
        TextView spinnerLabel = includeLayout.findViewById(R.id.requestSpinnerLabelTextView);
        ViewStyler.setTextViewStyle(spinnerLabel, context, 0, 0.03, 0.43, spinnerLabelText, dynamicContentPlaceholder);

        setDropDownVerticalOffset(spinner);

        setTopMarginForIncludeLayout(includeLayout, 0.03f);
        setTopMarginForIncludeLayout(spinner, 0.015f);
    }

    private void setDropDownVerticalOffset(final CustomSpinner spinner) {
        ViewTreeObserver vto = spinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {
            int spinnerHeight = spinner.getHeight();
            spinner.setDropDownVerticalOffset(spinnerHeight);
            spinner.getViewTreeObserver().removeOnGlobalLayoutListener(() -> {});
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
                    validateFieldAndShowError(fieldValue, errorMessageView,  fieldName);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed
            }
        });
    }

    private boolean shouldValidate(String fieldName) {
        return fieldName.equals("Email") || fieldName.equals("Mobile number");
    }


    private void setTopMarginForIncludeLayout(View includeLayout, float topMarginPercentage) {
        int marginTop = (int) (dynamicContentPlaceholderHeight * topMarginPercentage);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) includeLayout.getLayoutParams();
        layoutParams.topMargin = marginTop;
        includeLayout.setLayoutParams(layoutParams);
    }

    private void validateFieldAndShowError(EditText fieldValue, View errorMessageView, String fieldName) {
        String fieldText = fieldValue.getText().toString().trim();

        // Get the canBeEmpty status for the field
        boolean canBeEmpty = Boolean.TRUE.equals(fieldCanBeEmptyMap.getOrDefault(fieldName, true));

        // Check if the field is empty and cannot be empty
        if (fieldText.isEmpty() && !canBeEmpty) {
            showErrorMessage(errorMessageView, "Enter " + fieldName + ".");
            hasError = true;
            errorFields.add(fieldValue);
            return; // Skip further validation for this field
        } else {
            hideErrorMessage(errorMessageView);
        }

        switch (fieldName) {
            case "Name":
            case "Surname":
            case "Father's name":
                try {
                    ValidationUtils.validateNameOrSurname(fieldText, fieldName);
                    hideErrorMessage(errorMessageView);
                } catch (RequestValidationException e) {
                    showErrorMessage(errorMessageView, e.getMessage());
                    hasError = true;
                    errorFields.add(fieldValue);
                }
                break;
            case "Student code":
                if (fieldText.isEmpty() || !fieldText.matches("\\d{7}")) {
                    showErrorMessage(errorMessageView, "Student code must be exactly 7 digits.");
                    hasError = true;
                    errorFields.add(fieldValue);
                } else {
                    hideErrorMessage(errorMessageView);
                }
                break;

            case "Mobile number":
                if (!fieldText.isEmpty() && !fieldText.matches("[\\d+]+")) {
                    showErrorMessage(errorMessageView, "Mobil nömrə yalnız rəqəmlər və '+' simvolu qəbul edir.");
                    hasError = true;
                    errorFields.add(fieldValue);
                } else {
                    hideErrorMessage(errorMessageView);
                }
                break;

            case "Email": // Email validation
                if (!fieldText.isEmpty() && !EmailValidator.validateEmail(fieldText)) {
                    showErrorMessage(errorMessageView, fieldName + " is not in a valid format.");
                    hasError = true;
                    errorFields.add(fieldValue);
                } else {
                    hideErrorMessage(errorMessageView);
                }
                break;

            default:
                break;
        }
    }

    private void showErrorMessage(View errorView, String message) {
        TextView fieldErrorMessage = errorView.findViewById(R.id.errorMessageTextView);
        fieldErrorMessage.setText(message);
        errorView.setVisibility(View.VISIBLE);
        ViewStyler.setErrorMessageStyle(fieldErrorMessage, context, 0.4);
    }
    private void hideErrorMessage(View errorView) {
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {

    }
    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        // Not used
    }

    private void handleBtnAddItem() {
        final int guard = pageGen;
        disableButtons(btnAddItem);
        hasError = false; // Reset at the beginning
        firstErrorField = null;

        // Perform all validations (both user and student fields)
        performAllValidations();

        // If any errors were detected during validation, exit early
        if (hasError) {
            hasError = false; // Reset hasError for the next attempt
            enableButtons(btnAddItem);

            // Scroll to the first error field
            if (firstErrorField != null) {
                addItemScrollView.post(() -> {
                    // Calculate the scroll position
                    int scrollToY = getRelativeTop(firstErrorField) - addItemScrollView.getHeight() / 2;
                    if (scrollToY < 0) scrollToY = 0;
                    addItemScrollView.smoothScrollTo(0, scrollToY);
                });
                firstErrorField.requestFocus();
            }
            return;
        }

        // Show loading overlay
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);

        // collect user data
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(usernameEditText.getText().toString().trim());
        userRequest.setPassword(passwordEditText.getText().toString().trim());
        userRequest.setEmail(emailEditText.getText().toString().trim());

        // get user type ID (Required)
        UserType selectedUserType = (UserType) spinnerUserTypes.getSelectedItem();
        int userTypeId = selectedUserType.getItemId(); // Cannot be null
        userRequest.setUserTypeId(userTypeId);

        // Get status ID (Required)
        UserStatus selectedStatus = (UserStatus) spinnerStatuses.getSelectedItem();
        int statusId = selectedStatus.getStatusId(); // Cannot be null
        userRequest.setStatusId(statusId);

        // get student data
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setName(studentNameEditText.getText().toString().trim());
        studentRequest.setSurname(studentSurnameEditText.getText().toString().trim());
        studentRequest.setStudentCode(studentCodeEditText.getText().toString().trim());

        // get student type ID (Required)
        StudentType selectedStudentType = (StudentType) spinnerStudentTypes.getSelectedItem();
        int studentTypeId = selectedStudentType.getItemId(); // Cannot be null
        studentRequest.setStudentTypeId(studentTypeId);

        // get category ID (Required)
        Category selectedCategory = (Category) spinnerStudentCategories.getSelectedItem();
        int categoryId = selectedCategory.getItemId(); // Cannot be null
        studentRequest.setCategoryId(categoryId);

        // get section ID (Required)
        Section selectedSection = (Section) spinnerStudentSections.getSelectedItem();
        int sectionId = selectedSection.getItemId(); // Cannot be null
        studentRequest.setSectionId(sectionId);

        // get foreign language ID (Required)
        ForeignLanguage selectedForeignLanguage = (ForeignLanguage) spinnerStudentForeignLanguages.getSelectedItem();
        int foreignLanguageId = selectedForeignLanguage.getItemId(); // Cannot be null
        studentRequest.setForeignLanguageId(foreignLanguageId);

        // get class number ID (required)
        ClassNumber selectedClassNumber = (ClassNumber) spinnerStudentClassNumbers.getSelectedItem();
        int classNumberId = selectedClassNumber.getItemId(); // Can not be null
        studentRequest.setClassNumberId(classNumberId);


        studentRequest.setFatherName(studentFatherNameEditText.getText().toString().trim());
        studentRequest.setMobilePhone(studentMobilePhoneNumberEditText.getText().toString().trim());
        studentRequest.setSchoolClassCode(studentSchoolClassCodeEditText.getText().toString().trim());
        studentRequest.setAddress(studentAddressEditText.getText().toString().trim());

        // get communication sender status ID (Required)
        StudentCommunicationSenderStatus selectedCommunicationStatus = (StudentCommunicationSenderStatus) spinnerStudentCommunicationSenderStatuses.getSelectedItem();
        int communicationStatusId = selectedCommunicationStatus.getStatusId(); // Cannot be null
        studentRequest.setCommunicationSenderStatusId(communicationStatusId);

        // handle nullable fields for group IDs (Group and Sub Group and Class Letter can be empty)
        Group selectedGroup = (Group) spinnerStudentGroups.getSelectedItem();
        if (selectedGroup != null) {
            studentRequest.setGroupId(selectedGroup.getItemId()); // Optional, can be null
        } else {
            studentRequest.setGroupId(null);
        }

        SubGroup selectedSubGroup = (SubGroup) spinnerStudentSubGroups.getSelectedItem();
        if (selectedSubGroup != null) {
            studentRequest.setSubGroupId(selectedSubGroup.getItemId()); // Optional, can be null
        } else {
            studentRequest.setSubGroupId(null);
        }

        ClassLetter selectedClassLetter = (ClassLetter) spinnerStudentClassLetters.getSelectedItem();
        if (selectedClassLetter != null) {
            Log.d("CLASS LETTER", String.valueOf(selectedClassLetter.getItemId()));
            studentRequest.setClassLetterId(selectedClassLetter.getItemId()); // optional
        } else {
            studentRequest.setClassLetterId(null);
        }

        // create RegisterFullStudentRequest
        FullStudentRequest registerFullStudentRequest = new FullStudentRequest(userRequest, studentRequest);

        // make the API Call
        StudentApi studentApi = RetrofitInstance.getRetrofitInstance(context).create(StudentApi.class);
        StudentApiImpl studentApiImpl = new StudentApiImpl(studentApi);
        Call<Student> call = studentApiImpl.createItem(registerFullStudentRequest);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (!isAlive(guard)) return;
                enableButtons(btnAddItem);
                // Hide loading overlay after operation completes
                loadingOverlayUtils.hideLayoutOverlay();
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Student registration is successful.", Toast.LENGTH_SHORT).show();
                    clearNewUserAndStudentRequestFields();
                } else {
                    try {
                        assert response.errorBody() != null;
                        String errorMessage = response.errorBody().string();
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "An unexpected error occurred.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                if (!isAlive(guard)) return;
                enableButtons(btnAddItem);
                // Hide loading overlay after operation completes
                loadingOverlayUtils.hideLayoutOverlay();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performAllValidations() {
        hasError = false;
        errorFields.clear();

        // Validate user fields
        validateFieldAndShowError(usernameEditText, usernameErrorMessageView, "Username");
        validateFieldAndShowError(passwordEditText, passwordErrorMessageView, "Password");
        validateFieldAndShowError(emailEditText, emailErrorMessageView, "Email");

        // Validate spinners using the new approach
        for (SpinnerInfo spinnerInfo : requiredSpinners) {
            validateSpinner(spinnerInfo);
        }

        // Validate student fields
        validateFieldAndShowError(studentNameEditText, studentNameErrorMessageView, "Name");
        validateFieldAndShowError(studentSurnameEditText, studentSurnameErrorMessageView, "Surname");
        validateFieldAndShowError(studentCodeEditText, studentCodeErrorMessageView, "Student code");
        validateFieldAndShowError(studentFatherNameEditText, studentFatherNameErrorMessageView, "Father's name");
        validateFieldAndShowError(studentMobilePhoneNumberEditText, studentMobilePhoneErrorMessageView, "Mobile phone");
        validateFieldAndShowError(studentSchoolClassCodeEditText, studentSchoolClassCodeErrorMessageView, "School class code");
        validateFieldAndShowError(studentAddressEditText, studentAddressErrorMessageView, "Address");

        // Determine the topmost error field
        if (!errorFields.isEmpty()) {
            firstErrorField = getTopMostView(errorFields);
        } else {
            firstErrorField = null;
        }
    }

    private void clearNewUserAndStudentRequestFields() {
        enableButtons(btnAddItem);
        // Clear text fields
        usernameEditText.setText("");
        passwordEditText.setText("");
        emailEditText.setText("");
        studentNameEditText.setText("");
        studentSurnameEditText.setText("");
        studentCodeEditText.setText("");
        studentFatherNameEditText.setText("");
        studentMobilePhoneNumberEditText.setText("");
        studentSchoolClassCodeEditText.setText("");
        studentAddressEditText.setText("");

        // Reset required spinners and hide their error messages
        for (SpinnerInfo spinnerInfo : requiredSpinners) {
            resetSpinnerToDefault(spinnerInfo.getSpinner(), false); // Assuming all required spinners don't allow empty option
            hideErrorMessage(spinnerInfo.getErrorMessageView());
            spinnerInfo.setInitialized(false); // Reset initialization flag
        }

        // Reset optional spinners individually (e.g., those not in requiredSpinners)
        resetSpinnerToDefault(spinnerStudentGroups, true);
        resetSpinnerToDefault(spinnerStudentSubGroups, true);
        resetSpinnerToDefault(spinnerStudentClassLetters, true);

        // Hide error messages for optional spinners if they have any
        hideErrorMessage(studentGroupErrorMessageView);
        hideErrorMessage(studentSubGroupErrorMessageView);
        hideErrorMessage(studentClassLetterErrorMessageView);

        // Hide error messages for text fields
        hideErrorMessage(usernameErrorMessageView);
        hideErrorMessage(passwordErrorMessageView);
        hideErrorMessage(emailErrorMessageView);
        hideErrorMessage(studentNameErrorMessageView);
        hideErrorMessage(studentSurnameErrorMessageView);
        hideErrorMessage(studentCodeErrorMessageView);
        hideErrorMessage(studentFatherNameErrorMessageView);
        hideErrorMessage(studentMobilePhoneErrorMessageView);
        hideErrorMessage(studentSchoolClassCodeErrorMessageView);
        hideErrorMessage(studentAddressErrorMessageView);
        hideErrorMessage(studentClassNumberErrorMessageView);

        resetSpinnerInitializationFlags();

        // Scroll to the top of the form
        addItemScrollView.post(() -> addItemScrollView.smoothScrollTo(0, 0));
    }

    private void resetSpinnerToDefault(CustomSpinner spinner, boolean allowEmptyOption) {
        if (spinner.getAdapter() == null || spinner.getAdapter().getCount() == 0) {
            // If the spinner has no items, set selection to -1 (no selection)
            spinner.setSelection(-1);
        } else {
            if (allowEmptyOption) {
                spinner.setSelection(0); // Select the empty option
            } else {
                spinner.setSelection(0); // Select the first item
            }
        }
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

    @Override
    public void onSpecialItemClick(CustomSpinner currentSpinner) {
        SpinnerType spinnerType = (SpinnerType) currentSpinner.getTag();
        if (spinnerType != null) {
            switch (spinnerType) {
                case STUDENT_TYPES:
                    navigateToStudentTypesPage();
                    break;
                case STUDENT_GROUPS:
                    navigateToStudentGroupsPage();
                    break;
                case USER_TYPES:
                    navigateToUserTypesPage();
                    break;
                case SUB_GROUPS:
                    navigateToStudentSubGroupsPage();
                    break;
                case STUDENT_CATEGORIES:
                    navigateToStudentCategoriesPage();
                    break;
                case STUDENT_SECTIONS:
                    navigateToSectionPage();
                    break;
                case STUDENT_FOREIGN_LANGUAGES:
                    navigateToForeignLanguagePage();
                    break;
                case CLASS_NUMBERS:
                    navigateToClassNumbersPage();
                    break;
                case CLASS_LETTERS:
                    navigateToClassLettersPage();
                    break;
                default:
                    break;
            }
        }
    }

    private void navigateToStudentCategoriesPage() {
        Intent intent = new Intent(context, CategoriesActivity.class);
        if (context instanceof Activity){
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_STUDENT_CATEGORIES);
        } else {
            context.startActivity(intent);
        }
    }

    private void navigateToStudentTypesPage() {
        Intent intent = new Intent(context, StudentTypesActivity.class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_STUDENT_TYPE);
        } else {
            context.startActivity(intent);
        }
    }

    private void navigateToStudentGroupsPage() {
        Intent intent = new Intent(context, GroupsActivity.class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_GROUPS);
        } else {
            context.startActivity(intent);
        }
    }

    private void navigateToUserTypesPage() {
        Intent intent = new Intent(context, UserTypesActivity.class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_USER_TYPES);
        } else {
            context.startActivity(intent);
        }
    }

    private void navigateToStudentSubGroupsPage() {
        Intent intent = new Intent(context, SubGroupsActivity.class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_SUB_GROUPS);
        } else {
            context.startActivity(intent);
        }
    }

    private void navigateToSectionPage() {
        Intent intent = new Intent(context, SectionsActivity.class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_STUDENT_SECTIONS);
        } else {
            context.startActivity(intent);
        }
    }

    private void navigateToForeignLanguagePage() {
        Intent intent = new Intent(context, ForeignLanguagesActivity.class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_STUDENT_FOREIGN_LANGUAGES);
        } else {
            context.startActivity(intent);
        }
    }

    private void navigateToClassNumbersPage() {
        Intent intent = new Intent(context, ClassNumbersActivity.class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_CLASS_NUMBERS);
        } else {
            context.startActivity(intent);
        }
    }

    private void navigateToClassLettersPage() {
        Intent intent = new Intent(context, ClassLettersActivity.class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_CLASS_LETTERS);
        } else {
            context.startActivity(intent);
        }
    }
}
