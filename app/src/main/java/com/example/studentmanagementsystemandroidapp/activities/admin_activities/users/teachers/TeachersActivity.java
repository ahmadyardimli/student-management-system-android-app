package com.example.studentmanagementsystemandroidapp.activities.admin_activities.users.teachers;

import static com.example.studentmanagementsystemandroidapp.utils.ButtonUtils.disableButtons;
import static com.example.studentmanagementsystemandroidapp.utils.ButtonUtils.enableButtons;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.adapters.UserAndExamDetailsCommonAdapter;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.BaseAdminActivity;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails.UserAndExamDetailsCommonApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.commons.UserTypeApiImpl;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherApi;
import com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.teachers.TeacherApiImpl;
import com.example.studentmanagementsystemandroidapp.data.UserAndExamDetailsDataFetcher;
import com.example.studentmanagementsystemandroidapp.interfaces.data.DataFetchCallback;
import com.example.studentmanagementsystemandroidapp.managers.userandexamdetails.UserAndExamDetailsCommonManager;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.UserType;
import com.example.studentmanagementsystemandroidapp.models.users.Teacher;
import com.example.studentmanagementsystemandroidapp.models.users.UserStatus;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;
import com.example.studentmanagementsystemandroidapp.requests.users.UserTypeRequest;
import com.example.studentmanagementsystemandroidapp.requests.users.FullTeacherRequest;
import com.example.studentmanagementsystemandroidapp.utils.KeyboardUtils;
import com.example.studentmanagementsystemandroidapp.utils.LoadingOverlayUtils;
import com.example.studentmanagementsystemandroidapp.utils.SubjectsDisplayUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeachersActivity extends BaseAdminActivity implements UserAndExamDetailsCommonAdapter.OnUpdateClickListener {
    private Button btnExistingTeachers;
    private Button btnAddTeachers;
    private FrameLayout dynamicContentPlaceholder;
    private UserAndExamDetailsCommonManager<Teacher, FullTeacherRequest> teacherItemManager;
    private RecyclerView recyclerView;
    private List<Teacher> teachers;
    private static final int REQUEST_CODE_VIEW_AND_UPDATE_TEACHER = 1008;
    private static final int REQUEST_CODE_USER_TYPE = 1001;
    private static final int REQUEST_CODE_SUBJECTS = 1002;
    private CreateTeacherRequestViewManager createTeacherRequestViewManager;
    private LoadingOverlayUtils loadingOverlayUtils;
    private final Object genLock = new Object();
    private int pageGen = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);
        setupNavigationAndActionBar("Teachers", this, "Admins", "Users", "User Details");
        btnExistingTeachers = findViewById(R.id.btnExistingItems);
        btnAddTeachers = findViewById(R.id.btnAddItem);
        dynamicContentPlaceholder = findViewById(R.id.dynamicContentPlaceholderLayout);
        loadingOverlayUtils = new LoadingOverlayUtils(this);
        btnExistingTeachers.setText("Existing teachers");
        btnAddTeachers.setText("Add new teacher");
        UserAndExamDetailsCommonApi<Teacher, FullTeacherRequest> teacherApi = createTeacherApi();
        teacherItemManager = new UserAndExamDetailsCommonManager<>(teacherApi, this);

        createTeacherRequestViewManager = new CreateTeacherRequestViewManager(this, btnExistingTeachers, btnAddTeachers, dynamicContentPlaceholder);
        ViewStyler.setButtonSize(btnExistingTeachers, this, 0.4, 0.07, 0.25);
        ViewStyler.setButtonSize(btnAddTeachers, this, 0.4, 0.07, 0.25);

        showExistingItemsView();

        btnExistingTeachers.setOnClickListener(v -> {
            if (!btnExistingTeachers.isEnabled()) return;
            if (createTeacherRequestViewManager != null) createTeacherRequestViewManager.cancelOngoingWork();

            showExistingItemsView();
        });

        btnAddTeachers.setOnClickListener(v -> {
            if (!btnAddTeachers.isEnabled()) return;

            if (createTeacherRequestViewManager != null) createTeacherRequestViewManager.cancelOngoingWork();

            // update tab styles immediately
            btnAddTeachers.setBackgroundResource(R.drawable.button_category_pressed);
            btnExistingTeachers.setBackgroundResource(R.drawable.button_category_default);

            final int gen = bumpGen();
            dynamicContentPlaceholder.removeAllViews();
            createTeacherRequestViewManager.fetchDataAndShowAddTeacher(gen);
        });
    }

    private UserAndExamDetailsCommonApi<Teacher, FullTeacherRequest> createTeacherApi() {
        TeacherApi teacherApi = RetrofitInstance.getRetrofitInstance(this).create(TeacherApi.class);
        return new TeacherApiImpl(teacherApi);
    }

    private void showExistingItemsView() {
        final int gen = bumpGen();
        KeyboardUtils.hideKeyboard(this);
        btnExistingTeachers.setBackgroundResource(R.drawable.button_category_pressed); // Update button appearance for pressed state
        btnAddTeachers.setBackgroundResource(R.drawable.button_category_default);
        dynamicContentPlaceholder.removeAllViews();
        // show overlay
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);

        // inflate the layout_existing_items.xml and add it to the dynamic content placeholder
        View existingItemsView = LayoutInflater.from(this).inflate(R.layout.layout_existing_items, dynamicContentPlaceholder, false);
        TeacherApi teacherApi = RetrofitInstance.getRetrofitInstance(this).create(TeacherApi.class);
        UserAndExamDetailsDataFetcher<Teacher, FullTeacherRequest> teachersDataFetcher = new UserAndExamDetailsDataFetcher<>(TeachersActivity.this, new TeacherApiImpl(teacherApi));
        teachersDataFetcher.getDataResponseFromDatabase(new DataFetchCallback<Teacher>() {
            @Override
            public void onDataFetched(List<Teacher> data) {
                if (!isActiveGen(gen)) return;  // ignore stale callback
                loadingOverlayUtils.hideLayoutOverlay();

                if (data != null){
                    teachers = data;
                    // add RecyclerView setup logic here
                    recyclerView = existingItemsView.findViewById(R.id.recyclerViewItems);
                    recyclerView.setLayoutManager(new LinearLayoutManager(TeachersActivity.this));

                    // create and set the adapter
                    UserAndExamDetailsCommonAdapter<Teacher> adapter = new UserAndExamDetailsCommonAdapter<>(teachers, R.layout.user_exam_details_adapter_item_design);
                    adapter.setOnUpdateClickListener(TeachersActivity.this);
                    adapter.setUpdateButtonText("Details");
                    recyclerView.setAdapter(adapter);

                    dynamicContentPlaceholder.addView(existingItemsView);
                }
            }

            @Override
            public void onSingleItemFetched(Teacher item) {

            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                if (!isActiveGen(gen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                Toast.makeText(TeachersActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                if (!isActiveGen(gen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                dynamicContentPlaceholder.removeAllViews();
                TextView noItemMessage = existingItemsView.findViewById(R.id.noItemMessage);
                try {
                    noItemMessage.setText(response.errorBody().string() + getNoItemExistsMessage());
                } catch (IOException e) {
                    Toast.makeText(TeachersActivity.this, getIOExceptionErrorMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                noItemMessage.setVisibility(View.VISIBLE);
                noItemMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, dynamicContentPlaceholder.getHeight() * 0.05f);
                dynamicContentPlaceholder.addView(existingItemsView);
            }
        });

    }

    protected String getNoItemExistsMessage() {
        return "You can add a new teacher by going to the 'Add new Teacher' section.";
    }

    private String getIOExceptionErrorMessage() {
        return "Failed to retrieve teachers.";
    }

    @Override
    public void onUpdateClick(int position) {
        final int gen = pageGen;
        loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);
        Teacher clickedTeacher = teachers.get(position);

        teacherItemManager.getItemById(clickedTeacher.getItemId(), new Callback<Teacher> () {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if (!isActiveGen(gen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
                if (response.isSuccessful()) {
                    Teacher item = response.body();
                    String username = item.getUser().getUsername();
                    String email = item.getUser().getEmail();
                    int teacherId = item.getId();
                    String status = item.getUser().getStatus().getStatus();
                    String createdAt = item.getUser().getCreatedAt();
                    String updatedAt = item.getUser().getUpdatedAt();
                    String name = item.getName();
                    String surname = item.getSurname();
                    List<String> subjects = item.getSubjectNames();
                    String formattedSubjectsList = formatSubjectsList(subjects);
                    String communicationStatus = item.getCommunicationSenderStatus().getStatus();
                    int id = item.getUser().getId();

                    if (id != 0){
                        Log.d("User's id", String.valueOf(id));
                    }

                    navigateToViewAndUpdateTeacherActivity(id, teacherId, username, email, status,
                            createdAt, updatedAt, name, surname,
                            formattedSubjectsList, communicationStatus, subjects);
                } else {
                    try {
                        Toast.makeText(TeachersActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e("Data Fetcher", "Error reading error body");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                if (!isActiveGen(gen)) return;
                loadingOverlayUtils.hideLayoutOverlay();
            }
        });
    }
    private String formatSubjectsList(List<String> subjects) {
        return SubjectsDisplayUtils.joinSortedNames(subjects, ", ");
    }

    private void navigateToViewAndUpdateTeacherActivity(int id, int teacherId, String username, String email, String status,
                                                        String createdAt, String updatedAt, String name, String surname,
                                                        String subjects, String communicationStatus,  List<String> subjectList) {
        Intent intent = new Intent(this, ViewAndUpdateTeacherActivity.class);

        Log.d("ID in method", String.valueOf(id));
        intent.putExtra("id", String.valueOf(id));
        intent.putExtra("teacherId", teacherId);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("status", status);
        intent.putExtra("createdAt", createdAt);
        intent.putExtra("updatedAt", updatedAt);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("subjects", subjects);
        intent.putExtra("communicationStatus", communicationStatus);
        intent.putStringArrayListExtra("subjectList", new ArrayList<>(subjectList));

        startActivityForResult(intent, REQUEST_CODE_VIEW_AND_UPDATE_TEACHER);
    }

    @Override
    public void onDeleteClick(int position) {
        final int gen = pageGen;
        Teacher clickedItem = teachers.get(position);
        setDeleteLoading(true);
        teacherItemManager.deleteItem(getClickedItemId(clickedItem), new Callback<Void>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                setDeleteLoading(false);
                if (!isActiveGen(gen)) return;
                if (response.isSuccessful()) {
                    // handle the successful deletion
                    teachers.remove(position);
                    // update positions in the list
                    updatePositions();
                    // refresh the adapter
                    recyclerView.getAdapter().notifyDataSetChanged();
                    Toast.makeText(TeachersActivity.this, itemDeletedMessage(), Toast.LENGTH_SHORT).show();
                    if (teachers.isEmpty()){
                        showExistingItemsView();
                    }
                } else {
                    // if needed in future
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                setDeleteLoading(false);
                if (!isActiveGen(gen)) return;
                Log.e("Base Activity", "Failed to delete item: " + t.getMessage());
                if (t.getMessage() != null) {
                    Toast.makeText(TeachersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePositions() {
        for (int i = 0; i < teachers.size(); i++) {
            // update the position of each item in the list
            teachers.get(i).setPosition(i);
            // notify the adapter about the change in item positions
            recyclerView.getAdapter().notifyItemChanged(i);
        }
    }

    private String itemDeletedMessage() {
        return "Teacher deleted successfully.";
    }


    private int getClickedItemId(Teacher item) {
        return item.getId();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("On activity result", "inside");

        if(requestCode == REQUEST_CODE_USER_TYPE){
            Log.d("UT On activity UPDATE", "USER TYPES");
            fetchUpdatedUserTypes();
        } else if (requestCode == REQUEST_CODE_SUBJECTS) {
            Log.d("UT On activity UPDATE", "SUBJECTS");
            createTeacherRequestViewManager.refreshSubjectsDialogAsync();
        }
        if (requestCode == REQUEST_CODE_VIEW_AND_UPDATE_TEACHER && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("Received Intent Data", data.toString());
            if (data.hasExtra("teacherId")){
                int teacherId = data.getIntExtra("teacherId", -1);
                // check for status update
                if (data.hasExtra("updatedStatus")) {
                    UserStatus updatedStatus = (UserStatus) data.getSerializableExtra("updatedStatus");
                    Log.d("Updated Status", updatedStatus.getStatus());
                    // update the status in the list of teachers
                    updateTeacherStatus(teacherId, updatedStatus);
                }

                // check for name update
                if (data.hasExtra("updatedTeacherName")) {
                    String updatedTeacherName = data.getStringExtra("updatedTeacherName");
                    // Update the name in the list of teachers
                    updateTeacherName(teacherId, updatedTeacherName);
                }

                // check for surname update
                if (data.hasExtra("updatedTeacherSurname")) {
                    String updatedTeacherSurname = data.getStringExtra("updatedTeacherSurname");
                    // Update the surname in the list of teachers
                    updateTeacherSurname(teacherId, updatedTeacherSurname);
                }

                // check for subjects update
                if (data.hasExtra("updatedTeacherSubjects")){
                    List<String> updatedTeacherSubjects = data.getStringArrayListExtra("updatedTeacherSubjects");
                    updateTeacherSubjects(teacherId, updatedTeacherSubjects);
                }
            }
        }
    }

    private void fetchUpdatedUserTypes() {
        UserTypeApi userTypeApi = RetrofitInstance.getRetrofitInstance(this).create(UserTypeApi.class);
        UserAndExamDetailsDataFetcher<UserType, UserTypeRequest> userTypesDataFetcher = new UserAndExamDetailsDataFetcher<>(this, new UserTypeApiImpl(userTypeApi));
        userTypesDataFetcher.getAllDataFromDatabase(new DataFetchCallback<UserType>() {
            @Override
            public void onDataFetched(List<UserType> data) {
                createTeacherRequestViewManager.updateUserTypes(data);
            }

            @Override
            public void onSingleItemFetched(UserType item) {
                // Not used
            }

            @Override
            public void onDataFetchFailed(Throwable throwable) {
                Toast.makeText(TeachersActivity.this, "Failed to fetch user types: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnsuccessfulResponseFetched(Response response) {
                Toast.makeText(TeachersActivity.this, "Failed to fetch user types: " + response.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDeleteLoading(boolean loading) {
        if (isFinishing() || isDestroyed()) return;

        if (loading) {
            loadingOverlayUtils.showLayoutOverlay(dynamicContentPlaceholder);
            disableButtons(btnAddTeachers, btnExistingTeachers);
            if (recyclerView != null) {
                recyclerView.setEnabled(false);
                recyclerView.setAlpha(0.5f);
            }
        } else {
            loadingOverlayUtils.hideLayoutOverlay();
            enableButtons(btnAddTeachers, btnExistingTeachers);
            if (recyclerView != null) {
                recyclerView.setEnabled(true);
                recyclerView.setAlpha(1f);
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTeacherStatus(int teacherId, UserStatus updatedStatus) {
        Log.d("Updated Status", updatedStatus.getStatus());
        for (Teacher teacher : teachers) {
            if (teacherId == teacher.getId()){
                teacher.getUser().setStatus(updatedStatus);
                Log.d("updated teacher", teacher.getName());
                recyclerView.getAdapter().notifyDataSetChanged();
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTeacherName(int teacherId, String updatedTeacherName) {
        Log.d("Updated Teacher Name", updatedTeacherName);
        for (Teacher teacher : teachers) {
            if (teacherId == teacher.getId()){
                teacher.setName(updatedTeacherName);
                Log.d("updated teacher", teacher.getName());
                recyclerView.getAdapter().notifyDataSetChanged();
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTeacherSurname(int teacherId, String updatedTeacherSurname) {
        Log.d("Updated Teacher Surname", updatedTeacherSurname);
        for (Teacher teacher : teachers) {
            if (teacherId == teacher.getId()){
                teacher.setSurname(updatedTeacherSurname);
                Log.d("updated teacher", teacher.getName());
                recyclerView.getAdapter().notifyDataSetChanged();
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTeacherSubjects(int teacherId, List<String> updatedTeacherSubjects) {
        for (Teacher teacher : teachers) {
            if (teacherId == teacher.getId()){
                teacher.setSubjectNames(updatedTeacherSubjects);
                Log.d("updated teacher", teacher.getName());
                recyclerView.getAdapter().notifyDataSetChanged();
                break;
            }
        }
    }

    private int bumpGen() {
        synchronized (genLock) { return ++pageGen; }
    }
    private boolean isActiveGen(int gen) {
        synchronized (genLock) { return gen == pageGen && !isFinishing() && !isDestroyed(); }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (createTeacherRequestViewManager != null) {
            createTeacherRequestViewManager.cancelOngoingWork();
        }
        bumpGen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (createTeacherRequestViewManager != null) {
            createTeacherRequestViewManager.cancelOngoingWork();
        }
        bumpGen();
    }
}

