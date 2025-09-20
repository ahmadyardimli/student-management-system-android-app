package com.example.studentmanagementsystemandroidapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtils {
    public static int calculateWidthWithPercentage(Context context, double percentage) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        int screenWidth = displayMetrics.widthPixels;
        return (int) (percentage * screenWidth);
    }

    public static int calculateHeightWithPercentage(Context context, double percentage) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        int screenHeight = displayMetrics.heightPixels;
        return (int) (percentage * screenHeight);
    }

    public static int calculateWidthInDp(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        double screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) screenWidth;
    }

    public static int calculateHeightInDp(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        double density = context.getResources().getDisplayMetrics().density;
        return (int) (displayMetrics.heightPixels / density);
    }


    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    public static float getDeviceScreenHeight(Context context){
        // Get screen height
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        return screenHeight;
    }

    public static int calculatePaddingWithPercentage(Context context, double percentage) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        int screenHeight = displayMetrics.heightPixels;
        return (int) (screenHeight * percentage);
    }
}

