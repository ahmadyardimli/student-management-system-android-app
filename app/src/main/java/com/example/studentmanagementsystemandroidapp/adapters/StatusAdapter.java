package com.example.studentmanagementsystemandroidapp.adapters;

import android.content.Context;

import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.interfaces.SpecialSpinnerItemClickListener;
import com.example.studentmanagementsystemandroidapp.interfaces.users.Status;

import java.util.List;

public class StatusAdapter<T extends Status> extends BaseSpinnerAdapter<T> {

    public StatusAdapter(Context context, List<T> items, double parentHeight, double spinnerItemTextPercentage, SpecialSpinnerItemClickListener specialItemClickListener, CustomSpinner currentSpinner) {
        super(context, items, parentHeight, spinnerItemTextPercentage, specialItemClickListener, currentSpinner, false, false, 0);
    }

    @Override
    protected int getItemUniqueId(T item) {
        return 0;
    }

    @Override
    protected String getItemText(T status) {
        return status.getStatusText();
    }
}

