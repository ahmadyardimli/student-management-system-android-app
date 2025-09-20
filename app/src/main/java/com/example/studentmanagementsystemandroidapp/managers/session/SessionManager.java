package com.example.studentmanagementsystemandroidapp.managers.session;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.studentmanagementsystemandroidapp.R;

import java.util.concurrent.atomic.AtomicBoolean;

public final class SessionManager {
    public static final String ACTION_SESSION_EXPIRED = "com.example.studentmanagementsystemandroidapp.SESSION_EXPIRED";
    public static final String EXTRA_MESSAGE = "extra_message";

    // Debounce across the whole app until reset after a successful login
    private static final AtomicBoolean ALREADY_NOTIFIED = new AtomicBoolean(false);

    public static void notifySessionExpiredOnce(Context appContext, String ignored) {
        notifySessionExpiredOnce(appContext);
    }

    // Fire the broadcast only once per session-cascade
    public static void notifySessionExpiredOnce(Context appContext) {
        if (!ALREADY_NOTIFIED.compareAndSet(false, true)) return; // only first time

        Intent i = new Intent(ACTION_SESSION_EXPIRED);
        i.putExtra(EXTRA_MESSAGE, appContext.getString(R.string.session_expired_message));
        LocalBroadcastManager.getInstance(appContext.getApplicationContext()).sendBroadcast(i);
    }

    // Call this right after a successful login.
    public static void resetFlag() {
        ALREADY_NOTIFIED.set(false);
    }

    // UI can use this to suppress error toasts during the cascade.
    public static boolean isSessionEnded() {
        return ALREADY_NOTIFIED.get();
    }

    private SessionManager() {}
}
