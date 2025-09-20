package com.example.studentmanagementsystemandroidapp.managers.navigation;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.studentmanagementsystemandroidapp.R;
import com.example.studentmanagementsystemandroidapp.utils.ScreenUtils;

public class NavMenuItemStyler {
    private final Activity activity;

    public NavMenuItemStyler(Activity activity) {
        this.activity = activity;
    }

    public void applyStyles(MenuItem menuItem) {
        int screenWidthDp = ScreenUtils.calculateWidthInDp(activity);
        String title = menuItem.getTitle().toString();

        // Create a custom view for the menu item
        View customView = createCustomMenuItemView(title);
        menuItem.setActionView(customView);

        // Set text size dynamically based on screen width
        float textSizePercentage = screenWidthDp > 720 ? 0.035f : 0.015f;
        int textSize = ScreenUtils.calculateWidthWithPercentage(activity, textSizePercentage);
        setTextProperties(customView, R.id.menu_item_title, title, textSize);
    }

    // Apply styles to a selected (highlighted) menu item
    public void applySelectedItemStyles(MenuItem menuItem) {
        int screenWidthDp = ScreenUtils.calculateWidthInDp(activity);
        String title = menuItem.getTitle().toString();
        Log.d("Menu item TITLE BEFORE", menuItem.getTitle().toString());

        // Create a custom view for the selected menu item
        View customView = createCustomMenuItemView(title);
        setItemSelectedProperties(customView, R.color.highlighted_background_color_menu_item, R.color.sms_blue_pressed);
        menuItem.setActionView(customView);

        // Set text size dynamically based on screen width
        float textSizePercentage = screenWidthDp > 720 ? 0.035f : 0.015f;
        int textSize = ScreenUtils.calculateWidthWithPercentage(activity, textSizePercentage);
        setTextProperties(customView, R.id.menu_item_title, title, textSize);
    }

    // Create a custom view for a menu item with the given title
    private View createCustomMenuItemView(String title) {
        View customView = LayoutInflater.from(activity).inflate(R.layout.custom_menu_item, null);
        TextView menuItemTitle = customView.findViewById(R.id.menu_item_title);
        menuItemTitle.setText(title);
        return customView;
    }

    // Set properties for a selected menu item, such as background color and text color
    private void setItemSelectedProperties(View customView, int backgroundColorRes, int textColorRes) {
        int backgroundColor = ContextCompat.getColor(activity, backgroundColorRes);
        customView.findViewById(R.id.menu_item_background_view).setBackgroundColor(backgroundColor);

        TextView menuItemTitle = customView.findViewById(R.id.menu_item_title);
        int selectedTextColor = ContextCompat.getColor(activity, textColorRes);
        menuItemTitle.setTextColor(selectedTextColor);
    }

    // Set text properties for a menu item, such as text size
    private void setTextProperties(View customView, int textViewId, String text, int textSize) {
        TextView textView = customView.findViewById(textViewId);
        textView.setTextSize(textSize);
    }
}
