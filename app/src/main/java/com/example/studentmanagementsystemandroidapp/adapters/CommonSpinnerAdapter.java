package com.example.studentmanagementsystemandroidapp.adapters;

import android.content.Context;
import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.interfaces.RecyclerViewItemPositionable;
import com.example.studentmanagementsystemandroidapp.interfaces.SpecialSpinnerItemClickListener;
import java.util.List;

public class CommonSpinnerAdapter<T extends RecyclerViewItemPositionable> extends BaseSpinnerAdapter<T> {

    public CommonSpinnerAdapter(Context context, List<T> items, double parentHeight, double spinnerItemTextPercentage,
                                SpecialSpinnerItemClickListener specialItemClickListener, CustomSpinner currentSpinner,
                                boolean allowEmptyOption, boolean showSpecialItem, Integer selectedItemId) {
        super(context, items, parentHeight, spinnerItemTextPercentage, specialItemClickListener, currentSpinner,
                allowEmptyOption, showSpecialItem, selectedItemId);
    }

    // Implement the method to get the display text for an item
    @Override
    protected String getItemText(T item) {
        return item != null ? item.getItemName() : "Select empty";
    }

    // Implement the method to get the unique ID of an item
    @Override
    protected int getItemUniqueId(T item) {
        return item.getItemId();
    }
}



