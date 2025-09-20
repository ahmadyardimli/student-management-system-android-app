package com.example.studentmanagementsystemandroidapp.custom.spinners;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListPopupWindow;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CustomSpinner extends AppCompatSpinner {
    public interface OnSpinnerEventsListener {
        void onPopupWindowOpened(Spinner spinner);
        void onPopupWindowClosed(Spinner spinner);
    }

    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(@NonNull Context context, int mode) {
        super(context, mode);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    @Override
    public boolean performClick() {
        mOpenInitiated = true;
        if (mListener != null) {
            mListener.onPopupWindowOpened(this);
        }

        // Handle the click event for "Add Item"
        return super.performClick();
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        if (hasBeenOpened() && hasFocus) {
            performClosedEvent();
        }
    }

    public void setSpinnerEventsListener(
            OnSpinnerEventsListener onSpinnerEventsListener) {
        mListener = onSpinnerEventsListener;
    }

    public void performClosedEvent() {
        mOpenInitiated = false;
        if (mListener != null) {
            mListener.onPopupWindowClosed(this);
        }
    }

    public boolean hasBeenOpened() {
        return mOpenInitiated;
    }

    public void dismissDropdown() {
        try {
            Field popupField = AppCompatSpinner.class.getDeclaredField("mPopup");
            popupField.setAccessible(true);
            Object popup = popupField.get(this);
            if (popup instanceof ListPopupWindow) {
                ((ListPopupWindow) popup).dismiss();
            } else {
                Method dismissMethod = popup.getClass().getMethod("dismiss");
                dismissMethod.invoke(popup);
            }
        } catch (Exception e) {
            Log.e("CustomSpinner", "Error dismissing dropdown", e);
        }
    }
}
