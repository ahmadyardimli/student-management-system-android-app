package com.example.studentmanagementsystemandroidapp.models.users;

import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Section;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.SubGroup;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Category;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassLetter;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassNumber;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ForeignLanguage;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Group;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.StudentType;

import java.util.Arrays;
import java.util.List;

public class Student implements RecyclerViewItemPositionable {
    private int id;
    private int userId;
    private String name;
    private String surname;
    private String studentCode;
    private StudentType studentType;
    private Group group;
    private SubGroup subGroup;
    private Category category;
    private Section section;
    private ForeignLanguage foreignLanguage;
    private String fatherName;
    private String mobilePhone;
    private String schoolClassCode;
    private String address;
    private ClassNumber classNumber;
    private ClassLetter classLetter;
    private StudentCommunicationSenderStatus communicationSenderStatus;

    //    @SerializedName("user")
    private User user;
    private int position;

    public Student(int id, int userId, String name, String surname, String studentCode,
                   StudentType studentType, Group group, SubGroup subGroup,
                   Category category, Section section, ForeignLanguage foreignLanguage,
                   String fatherName, String mobilePhone, String schoolClassCode,
                   String address, ClassNumber classNumber, ClassLetter classLetter, StudentCommunicationSenderStatus communicationSenderStatus, User user) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.studentCode = studentCode;
        this.studentType = studentType;
        this.group = group;
        this.subGroup = subGroup;
        this.category = category;
        this.section = section;
        this.foreignLanguage = foreignLanguage;
        this.fatherName = fatherName;
        this.mobilePhone = mobilePhone;
        this.schoolClassCode = schoolClassCode;
        this.address = address;
        this.classNumber = classNumber;
        this.classLetter = classLetter;
        this.communicationSenderStatus = communicationSenderStatus;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public StudentType getStudentType() {
        return studentType;
    }

    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public SubGroup getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(SubGroup subGroup) {
        this.subGroup = subGroup;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public ForeignLanguage getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(ForeignLanguage foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSchoolClassCode() {
        return schoolClassCode;
    }

    public void setSchoolClassCode(String schoolClassCode) {
        this.schoolClassCode = schoolClassCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClassNumber getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(ClassNumber classNumber) {
        this.classNumber = classNumber;
    }

    public StudentCommunicationSenderStatus getCommunicationSenderStatus() {
        return communicationSenderStatus;
    }

    public void setCommunicationSenderStatus(StudentCommunicationSenderStatus communicationSenderStatus) {
        this.communicationSenderStatus = communicationSenderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getItemId() {
        return id;
    }

    @Override
    public String getItemName() {
        return name;
    }

    private String getStudentDetails() {
        return name + " " + surname;
    }

    private String getStudentFullClass() {
        if (classNumber != null){
            if (classLetter != null){
                return classNumber.getNumberValue() + classLetter.getLetterValue();
            }
            else return String.valueOf(classNumber.getNumberValue());
        } else return  "not found";
    }

    private String getStudentTypeString() {
        return studentType != null ? studentType.getItemName() : "Not available";
    }

    private String getStudentStatus() {
        return user != null ? user.getStatus().getStatusText() : "Not available";
    }

    private String getStudentCategoryString() {
        return category != null ? category.getItemName() : "Not available";
    }

    private String getStudentClassNumber() {
        if (classNumber != null) {
            int numberValue = classNumber.getNumberValue();
            return numberValue != 0 ? String.valueOf(numberValue) : "Not available";
        } else {
            return "Not available";
        }
    }

    private String getStudentUsername() {
        return user != null ? user.getUsername() : "Not available";
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfo() {
        return Arrays.asList(
                new RequestFieldInfo("Full name", getStudentDetails(), null),
                new RequestFieldInfo("Student type", getStudentTypeString(), null),
                new RequestFieldInfo("Category", getStudentCategoryString(), null),
                new RequestFieldInfo("Class", getStudentFullClass(), null),
                new RequestFieldInfo("Status", getStudentStatus(), null)
        );
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfoForCreation() {
        return null;
    }

    public ClassLetter getClassLetter() {
        return classLetter;
    }

    public void setClassLetter(ClassLetter classLetter) {
        this.classLetter = classLetter;
    }
}