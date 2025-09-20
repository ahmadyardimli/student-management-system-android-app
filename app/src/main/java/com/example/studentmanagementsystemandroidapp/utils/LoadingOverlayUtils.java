package com.example.studentmanagementsystemandroidapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

import com.example.studentmanagementsystemandroidapp.R;

public class LoadingOverlayUtils {
    private Dialog loadingDialog;  // For full-screen overlay
    private View layoutOverlayView;  // For layout-specific overlay
    private Context context;

    public LoadingOverlayUtils(Context context) {
        this.context = context;
    }

    // Full-screen overlay for entire activity
    public void showActivityOverlay(Activity activity, boolean dimBackground) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return; // Don't show if the activity is not in a valid state
        }

        if (loadingDialog == null) {
            loadingDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
            loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // Inflate and set the custom loading overlay
            View loadingView = LayoutInflater.from(context).inflate(R.layout.loading_overlay, null);

            // Set background based on dimBackground parameter
            FrameLayout overlayLayout = loadingView.findViewById(R.id.loadingOverlay);
            if (!dimBackground) {
                overlayLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            }

            loadingDialog.setContentView(loadingView);
            loadingDialog.setCancelable(false);

            WindowManager.LayoutParams params = loadingDialog.getWindow().getAttributes();
            params.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            loadingDialog.getWindow().setAttributes(params);
            loadingDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            overlayLayout.setOnTouchListener((v, event) -> true); // Intercepts all touch events
        }

        loadingDialog.show();
    }

    // Overlay for a specific layout (e.g., dynamicContentPlaceholder)
    @SuppressLint("ClickableViewAccessibility")
    public void showLayoutOverlay(ViewGroup targetLayout) {
        if (layoutOverlayView == null) {
            layoutOverlayView = LayoutInflater.from(context).inflate(R.layout.loading_overlay, targetLayout, false);
            FrameLayout overlayLayout = layoutOverlayView.findViewById(R.id.loadingOverlay);
            overlayLayout.setOnTouchListener((v, event) -> true); // Block all touch interactions
        }

        if (layoutOverlayView.getParent() == null) {
            targetLayout.addView(layoutOverlayView);
        } else {
            targetLayout.bringChildToFront(layoutOverlayView);
        }

        layoutOverlayView.setVisibility(View.VISIBLE);
    }

    // Hide overlay for full-screen Activity overlay
    public void hideActivityOverlay() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    // Hide overlay for layout-specific overlay
    public void hideLayoutOverlay() {
        if (layoutOverlayView != null && layoutOverlayView.getVisibility() == View.VISIBLE) {
            layoutOverlayView.setVisibility(View.GONE);
            ViewGroup parent = (ViewGroup) layoutOverlayView.getParent();
            if (parent != null) {
                parent.removeView(layoutOverlayView);
            }
        }
    }
}