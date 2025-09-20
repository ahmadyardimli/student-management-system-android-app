package com.example.studentmanagementsystemandroidapp.models.userandexamdetails;
import com.example.studentmanagementsystemandroidapp.helpers.userandexamdetails.RequestFieldInfo;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import java.util.Arrays;
import java.util.List;

public class SubGroup implements RecyclerViewItemPositionable {
    private int id;
    private String subGroup;
    private int position;

    public SubGroup() {
    }

    public SubGroup(int id, String subGroupName) {
        this.id = id;
        this.subGroup = subGroupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
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
        return subGroup;
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfo() {
        return Arrays.asList(
                new RequestFieldInfo("Sub Group", subGroup,  "Please enter the sub group.")
        );
    }

    @Override
    public List<RequestFieldInfo> getRequestFieldInfoForCreation() {
        return Arrays.asList(
                new RequestFieldInfo("Add Sub Group", "Sub Group", "Please enter the sub group.")
        );
    }
}
