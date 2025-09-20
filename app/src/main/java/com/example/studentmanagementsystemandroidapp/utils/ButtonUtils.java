package com.example.studentmanagementsystemandroidapp.utils;

import android.widget.Button;

public class ButtonUtils {
    public static void disableButtons(Button... buttons){
        for (Button button : buttons){
            button.setEnabled(false);
        }
    }

    public static void enableButtons(Button... buttons){
        for (Button button : buttons){
            button.setEnabled(true);
        }
    }
}
