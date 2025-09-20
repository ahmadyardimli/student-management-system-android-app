package com.example.studentmanagementsystemandroidapp.managers.auth;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;

import com.example.studentmanagementsystemandroidapp.activities.auth_activities.LoginActivity;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;
import com.example.studentmanagementsystemandroidapp.managers.session.SessionManager;
import com.example.studentmanagementsystemandroidapp.managers.users.students.StudentSelfManager;
import com.example.studentmanagementsystemandroidapp.managers.users.teachers.TeacherSelfManager;
import com.example.studentmanagementsystemandroidapp.network.RetrofitInstance;

public final class LogoutManager {
    private LogoutManager() {}

    public static void logout(Activity activity) {
        Context app = activity.getApplicationContext();

        // Clear local auth + any caches
        try { new TokenStore(app).clear(); } catch (Throwable ignored) {}
        try { StudentSelfManager.clearCache(); } catch (Throwable ignored) {}
        try { TeacherSelfManager.clearCache(); } catch (Throwable ignored) {}

        // Reset session debounce + Retrofit
        SessionManager.resetFlag();
        RetrofitInstance.reset(app);

        // Go to Login and show a friendly toast
        Intent i = new Intent(activity, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra(LoginActivity.EXTRA_SHOW_SIGNED_OUT_TOAST, true);
        activity.startActivity(i);
    }
}