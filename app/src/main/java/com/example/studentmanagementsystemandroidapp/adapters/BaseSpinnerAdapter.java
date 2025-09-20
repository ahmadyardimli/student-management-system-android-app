package com.example.studentmanagementsystemandroidapp.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.custom.spinners.CustomSpinner;
import com.example.studentmanagementsystemandroidapp.interfaces.SpecialSpinnerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSpinnerAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> items;
    protected double parentHeight;
    protected double spinnerItemTextPercentage;
    private SpecialSpinnerItemClickListener specialItemClickListener;
    private CustomSpinner currentSpinner;
    private final boolean allowEmptyOption;
    private final boolean showSpecialItem;
    // Field to store selected item ID
    protected Integer selectedItemId;

    public BaseSpinnerAdapter(Context context, List<T> items, double parentHeight, double spinnerItemTextPercentage, SpecialSpinnerItemClickListener specialItemClickListener, CustomSpinner currentSpinner, boolean allowEmptyOption, boolean showSpecialItem, Integer selectedItemId) {
        this.context = context;
        this.parentHeight = parentHeight;
        this.spinnerItemTextPercentage = spinnerItemTextPercentage;
        this.specialItemClickListener = specialItemClickListener;
        this.currentSpinner = currentSpinner;
        this.allowEmptyOption = allowEmptyOption;
        this.showSpecialItem = showSpecialItem;
        this.selectedItemId = selectedItemId;
        this.items = new ArrayList<>(items);

        if (this.showSpecialItem && (this.items.isEmpty() || !containsSpecialItem())) {
            this.items.add(null); // Add null as placeholder for the special item
        }

        if (this.allowEmptyOption) {
            this.items.add(0, null); // Add null at the start for empty option
        }

        if (this.showSpecialItem && this.items.size() == 1 && this.items.get(0) == null) {
            currentSpinner.post(() -> {
                notifyDataSetChanged();
                currentSpinner.setSelection(0);
            });
        }
    }

    // Method to find the position of the item with selectedItemId
    public int getPositionOfSelectedItem() {
        if (selectedItemId == null) {
            return -1;
        }
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (item != null && getItemUniqueId(item) == selectedItemId) {
                return i;
            }
        }
        return -1;
    }

    protected boolean shouldAddSpecialItem() {
        return showSpecialItem;
    }

    protected abstract int getItemUniqueId(T item);


    private boolean containsSpecialItem() {
        return items.contains(null);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }


    @Override
    public T getItem(int position) {
        if (items == null || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (items.size() == 1 && items.get(0) == null) {
            // Only special item is present, show it as the main view
            convertView = LayoutInflater.from(context).inflate(R.layout.item_special_spinner, parent, false);
            SpecialViewHolder specialViewHolder = new SpecialViewHolder(convertView);
            specialViewHolder.specialItemText.setText("Manage items");
            setDynamicHeightAndTextSize(convertView, specialViewHolder.specialItemText);
            return convertView;
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView itemText = convertView.findViewById(android.R.id.text1);
            T item = getItem(position);
            itemText.setText(item != null ? getItemText(item) : "");

            // Set dynamic text size for spinner view
            float dynamicTextSize = (float) (parentHeight * spinnerItemTextPercentage * 0.9);  // Scale down by 0.9 to fit
            itemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, dynamicTextSize);

            return convertView;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (position == getCount() - 1 && items.get(position) == null && shouldAddSpecialItem()) {
            // Special item in dropdown  ("Add")
            SpecialViewHolder specialViewHolder;
            if (convertView == null || !(convertView.getTag() instanceof SpecialViewHolder)) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_special_spinner, parent, false);
                specialViewHolder = new SpecialViewHolder(convertView);
                convertView.setTag(specialViewHolder);
            } else {
                specialViewHolder = (SpecialViewHolder) convertView.getTag();
            }

            // Set text for special item and dynamic text size
            specialViewHolder.specialItemText.setText("Manage items");
            setDynamicHeightAndTextSize(convertView, specialViewHolder.specialItemText);

            // Set click listener for special item
            convertView.setOnClickListener(v -> {
                if (specialItemClickListener != null) {
                    specialItemClickListener.onSpecialItemClick(currentSpinner);
                }
            });

            return convertView;
        } else {
            // Regular dropdown items
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            TextView itemText = convertView.findViewById(android.R.id.text1);
            T item = getItem(position);
            itemText.setText(getItemText(item));

            // Set dynamic height and text size for regular items
            setDynamicHeightAndTextSize(convertView, itemText);
            return convertView;
        }
    }

    void setDynamicHeightAndTextSize(View itemView, TextView textView) {
        // Set item height based on spinner's height
        int spinnerHeight = currentSpinner.getHeight();
        if (spinnerHeight > 0) {
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            if (params != null) {
                params.height = spinnerHeight;
                itemView.setLayoutParams(params);
            }
        }

        // Dynamically set text size as a percentage of the spinner height
        float dynamicTextSize = (float) (parentHeight * spinnerItemTextPercentage * 0.9);  // Scale down by 0.9 to fit
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dynamicTextSize);
        // Vertically center, left-align horizontally
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
    }

    protected abstract String getItemText(T item);

    private static class SpecialViewHolder {
        TextView specialItemText;

        SpecialViewHolder(View view) {
            specialItemText = view.findViewById(R.id.specialItemText);
        }
    }
}
