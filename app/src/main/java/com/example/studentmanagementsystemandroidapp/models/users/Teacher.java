package com.example.studentmanagementsystemandroidapp.models.users;


import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;

import java.util.Arrays;
import java.util.List;

public class Teacher implements RecyclerViewItemPositionable {
    private int id;
    private String name;
    private String surname;
    private List<Integer> subject_ids;
    private int userId;
    private TeacherCommunicationSenderStatus communicationSenderStatus;
    private int position;
    private List<String> subjectNames;
    private User user;

    public Teacher(int id, String name, String surname, List<Integer> subject_ids, int userId, TeacherCommunicationSenderStatus communicationSenderStatus, User user) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.subject_ids = subject_ids;
        this.userId = userId;
        this.communicationSenderStatus = communicationSenderStatus;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Integer> getSubject_ids() {
        return subject_ids;
    }

    public void setSubject_ids(List<Integer> subject_ids) {
        this.subject_ids = subject_ids;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TeacherCommunicationSenderStatus getCommunicationSenderStatus() {
        return communicationSenderStatus;
    }

    public void setCommunicationSenderStatus(TeacherCommunicationSenderStatus communicationSenderStatus) {
        this.communicationSenderStatus = communicationSenderStatus;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
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

    private String getSubjectsString(){
        String subjects = "";
        String del = ", ";
        for (int i = 0; i < subjectNames.size(); i++){
            subjects += subjectNames.get(i);

            if (i != subjectNames.size() - 1)
                subjects += del;
        }
        return subjects;
    }

    private String getTeacherStatus(){
        return user != null ? user.getStatus().getStatusText() : "Status not available";
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfo() {
        return Arrays.asList(
                new RequestFieldInfo("Full name", name + " " + surname, null),
                new RequestFieldInfo("Subjects", getSubjectsString(), null),
                new RequestFieldInfo("Status", getTeacherStatus(), null)
        );
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfoForCreation() {
        return null;
    }

    public List<String> getSubjectNames() {
        return subjectNames;
    }

    public void setSubjectNames(List<String> subjectNames) {
        this.subjectNames = subjectNames;
    }
}
