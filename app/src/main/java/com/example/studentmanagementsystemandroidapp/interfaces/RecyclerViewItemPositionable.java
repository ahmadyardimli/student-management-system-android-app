package com.example.studentmanagementsystemandroidapp.interfaces;

import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;

import java.util.List;

public interface RecyclerViewItemPositionable {
    int getPosition();
    void setPosition(int position);
    int getItemId();
    String getItemName();
    List<RequestFieldInfo> getRequestFieldInfo();
    List<RequestFieldInfo> getRequestFieldInfoForCreation();
}
