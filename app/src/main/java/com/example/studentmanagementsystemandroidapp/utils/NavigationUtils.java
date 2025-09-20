package com.example.studentmanagementsystemandroidapp.utils;

import android.content.Context;
import android.content.Intent;

public class NavigationUtils {
    public static void navigateToActivity(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}
