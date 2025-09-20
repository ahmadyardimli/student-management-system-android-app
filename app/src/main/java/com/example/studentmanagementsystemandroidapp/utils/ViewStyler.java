package com.example.studentmanagementsystemandroidapp.utils;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.studentmanagementsystemandroidapp.R;

public class ViewStyler {
    public static void setErrorMessageStyle(View errorMessageView, Context context, double errorMessageWidthPercentage) {
        TextView errorMessageTextView = errorMessageView.findViewById(R.id.errorMessageTextView);
        int errorMessageWidth = ScreenUtils.calculateWidthWithPercentage(context, errorMessageWidthPercentage);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) errorMessageTextView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = errorMessageWidth;
            errorMessageTextView.setLayoutParams(layoutParams);
        }
        errorMessageView.setVisibility(View.VISIBLE);
    }

    public static void setButtonSize(
            Button button,
            Context context,
            double widthPercentage,
            double heightPercentage,
            double textSizePercentage
    ) {
        // If the button text is very long, do some adjustments
        int textLength = button.getText().toString().length();
        if (textLength > 40) {
            heightPercentage = 0.1;
            textSizePercentage = 0.15;
        }

        // Calculate the target width/height in pixels
        int buttonWidth = ScreenUtils.calculateWidthWithPercentage(context, widthPercentage);
        int buttonHeight = ScreenUtils.calculateHeightWithPercentage(context, heightPercentage);

        // Get the current LayoutParams for the button
        ViewGroup.LayoutParams params = button.getLayoutParams();
        if (params == null) {
            // If button has no LayoutParams yet, create some default
            params = new ViewGroup.LayoutParams(buttonWidth, buttonHeight);
        }

        // Because margin/tensions may vary, let's check the type carefully
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
            marginParams.width = buttonWidth;
            marginParams.height = buttonHeight;
            // If you want to manipulate margins, you can do so here
            button.setLayoutParams(marginParams);
        } else {
            // fallback: just set width/height on the base LayoutParams
            params.width = buttonWidth;
            params.height = buttonHeight;
            button.setLayoutParams(params);
        }

        // Now apply text size.
        // We can’t rely on “layoutParams.height” if it’s not a ConstraintLayout
        // because the final height may differ after layout.
        // Instead, we can do a direct proportion of the *calculated* buttonHeight:
        int textSizePx = (int) (textSizePercentage * buttonHeight);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx);
    }

    public static void setButtonSize(
            Button button,
            Context context,
            double widthPercentage,
            double heightPercentage,
            double textSizePercentage,
            double topMarginPercentage,
            double bottomMarginPercentage
    ) {
        // if the text is very long, override the percentages (like your old logic)
        int textLength = button.getText().toString().length();
        if (textLength > 40) {
            heightPercentage = 0.1;
            textSizePercentage = 0.15;
        }

        // calculate width/height in px from screen percentage
        int buttonWidth  = ScreenUtils.calculateWidthWithPercentage(context, widthPercentage);
        int buttonHeight = ScreenUtils.calculateHeightWithPercentage(context, heightPercentage);

        // calculate margins in px from screen percentage
        int topMargin    = ScreenUtils.calculateHeightWithPercentage(context, topMarginPercentage);
        int bottomMargin = ScreenUtils.calculateHeightWithPercentage(context, bottomMarginPercentage);

        // get the current LayoutParams
        ViewGroup.LayoutParams rawParams = button.getLayoutParams();
        if (rawParams == null) return;  // nothing to do if the button has no layout params

        // set the width and height
        rawParams.width  = buttonWidth;
        rawParams.height = buttonHeight;

        // if it’s a margin-based LayoutParams, set the margins
        if (rawParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) rawParams;
            marginParams.topMargin    = topMargin;
            marginParams.bottomMargin = bottomMargin;
        }

        // 7) Update the text size
        int textSizePx = (int) (textSizePercentage * buttonHeight);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx);

        // 8) Re-apply the updated LayoutParams
        button.setLayoutParams(rawParams);
    }

    public static void setTextViewStyle(TextView textView, Context context, double marginTopPercentage, double textSizePercentage, double widthPercentage, String text, View parent) {
        int marginTop = ScreenUtils.calculateHeightWithPercentage(context, marginTopPercentage);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.topMargin = marginTop;
            int textViewWidth = ScreenUtils.calculateWidthWithPercentage(context, widthPercentage);
            layoutParams.width = textViewWidth;
            textView.setLayoutParams(layoutParams);
            int parentHeight = parent.getHeight();
            int textSize = (int) (textSizePercentage * parentHeight);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textView.setText(text);
        }
    }

    public static void setEditTextStyle(EditText editText, Context context, double marginTopPercentage, double textSizePercentage, double widthPercentage, String hint, int tintColor, View parent) {
        int marginTop = ScreenUtils.calculateHeightWithPercentage(context, marginTopPercentage);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) editText.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.topMargin = marginTop;
            int editTextWidth = ScreenUtils.calculateWidthWithPercentage(context, widthPercentage);
            layoutParams.width = editTextWidth;
            editText.setLayoutParams(layoutParams);
            int parentHeight = parent.getHeight();
            int textSize = (int) (textSizePercentage * parentHeight);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            editText.setHint(hint);
            int[][] states = new int[][]{
                    new int[]{android.R.attr.state_focused},  // focused
                    new int[]{-android.R.attr.state_focused}   // unfocused
            };
            int[] colors = new int[]{
                    tintColor,
                    ContextCompat.getColor(context, R.color.sms_blue_darker)
            };
            ColorStateList colorStateList = new ColorStateList(states, colors);
            editText.setBackgroundTintList(colorStateList);
        }
    }

    public static void setTextSizeByScreenHeight(TextView textView, Context context, double fractionOfScreenHeight) {
        // Safeguard against negative or overly large fractions
        if (fractionOfScreenHeight < 0 || fractionOfScreenHeight > 1) {
            fractionOfScreenHeight = 0.03;
        }

        // Fetch the device’s screen height in pixels
        android.util.DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenHeightPx = dm.heightPixels;

        // Calculate text size in pixels based on fractionOfScreenHeight
        int textSizePx = (int) (fractionOfScreenHeight * screenHeightPx);

        // Apply the computed text size
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx);
    }

    public static void setSpinnerStyle(Spinner spinner, Context context, double marginTopPercentage, double widthPercentage, double heightPercentage) {
        int marginTop = ScreenUtils.calculateHeightWithPercentage(context, marginTopPercentage);
        int spinnerWidth = ScreenUtils.calculateWidthWithPercentage(context, widthPercentage);
        int spinnerHeight = ScreenUtils.calculateHeightWithPercentage(context, heightPercentage);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) spinner.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.topMargin = marginTop;
            layoutParams.width = spinnerWidth;
            layoutParams.height = spinnerHeight;
            spinner.setLayoutParams(layoutParams);
        }
    }

    public static void setAddItemViewStartWithPercentage(int topMargin,  View addItemView){
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) addItemView.getLayoutParams();
        layoutParams.topMargin = topMargin;
        addItemView.setLayoutParams(layoutParams);
    }
}
