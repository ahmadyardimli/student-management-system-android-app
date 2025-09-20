package com.example.studentmanagementsystemandroidapp.utils;

import android.text.TextUtils;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Subject;

import java.text.Collator;
import java.util.*;

public final class SubjectsDisplayUtils {
    private static final Collator AZ = Collator.getInstance(new Locale("az"));
    static { AZ.setStrength(Collator.PRIMARY); }

    private SubjectsDisplayUtils() {}

    public static String joinSortedNames(List<String> names, String delimiter) {
        if (names == null || names.isEmpty()) return "";
        List<String> copy = new ArrayList<>(names);
        copy.sort(AZ);
        return TextUtils.join(delimiter, copy);
    }

    public static void sortSubjectsInPlace(List<Subject> subjects) {
        if (subjects == null) return;
        subjects.sort((a, b) -> AZ.compare(a.getSubject(), b.getSubject()));
    }
}