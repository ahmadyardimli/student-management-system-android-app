package com.example.studentmanagementsystemandroidapp.activities.admin_activities.userandexamdetails;

import android.os.Bundle;
import android.widget.Button;
import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.activities.admin_activities.admins.BaseAdminActivity;
import com.example.studentmanagementsystemandroidapp.utils.NavigationUtils;
import com.example.studentmanagementsystemandroidapp.utils.ViewStyler;

public class UserAndExamDetailsActivity extends BaseAdminActivity {
    private Button btnForeignLanguages;
    private Button btnBolmes;
    private Button btnCategories;
    private Button btnGroups;
    private Button btnAltGroups;
    private Button btnUserTypes;
    private Button btnSubjects;
    private Button btnStudentTypes;
    private Button btnClassNumbers;
    private Button btnClassLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_and_exam_details);

        // Set up navigation and action bar
        setupNavigationAndActionBar("Details", this, "Admins", "Users", "User Details");

        // Initialize buttons
        btnForeignLanguages = findViewById(R.id.btnForeignLanguages);
        btnBolmes = findViewById(R.id.btnSections);
        btnCategories = findViewById(R.id.btnCategories);
        btnGroups = findViewById(R.id.btnGroups);
        btnAltGroups = findViewById(R.id.btnSubGroups);
        btnUserTypes = findViewById(R.id.btnUserTypes);
        btnSubjects = findViewById(R.id.btnSubjects);
        btnStudentTypes = findViewById(R.id.btnStudentTypes);
        btnClassNumbers = findViewById(R.id.btnClassNumbers);
        btnClassLetters = findViewById(R.id.btnClassLetters);

        // Set the width and height of buttons
        ViewStyler.setButtonSize(btnForeignLanguages,this, 0.6,0.07,  0.3);
        ViewStyler.setButtonSize(btnBolmes, this, 0.6,0.07,  0.3);
        ViewStyler.setButtonSize(btnCategories, this,0.6,0.07, 0.3);
        ViewStyler.setButtonSize(btnGroups, this,0.6, 0.07, 0.3);
        ViewStyler.setButtonSize(btnAltGroups, this,0.6,0.07, 0.3);
        ViewStyler.setButtonSize(btnUserTypes, this,0.6, 0.07, 0.3);
        ViewStyler.setButtonSize(btnSubjects, this,0.6, 0.07, 0.3);
        ViewStyler.setButtonSize(btnStudentTypes, this,0.6, 0.07, 0.3);
        ViewStyler.setButtonSize(btnClassNumbers, this, 0.6, 0.07, 0.3);
        ViewStyler.setButtonSize(btnClassLetters, this, 0.6, 0.07, 0.3);

        btnForeignLanguages.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, ForeignLanguagesActivity.class));
        btnBolmes.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, SectionsActivity.class));
        btnCategories.setOnClickListener(view -> NavigationUtils.navigateToActivity(this,CategoriesActivity.class));
        btnGroups.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, GroupsActivity.class));
        btnAltGroups.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, SubGroupsActivity.class));
        btnUserTypes.setOnClickListener(view -> NavigationUtils.navigateToActivity(this,UserTypesActivity.class));
        btnSubjects.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, SubjectsActivity.class));
        btnStudentTypes.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, StudentTypesActivity.class));
        btnClassNumbers.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, ClassNumbersActivity.class));
        btnClassLetters.setOnClickListener(view -> NavigationUtils.navigateToActivity(this, ClassLettersActivity.class));

    }
}
