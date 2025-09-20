package com.example.studentmanagementsystemandroidapp.utils;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassLetter;
import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.ClassNumber;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//  Utility class for sorting ClassNumber and ClassLetter lists.
public class ClassNumberAndLetterSorter {
    public static List<ClassNumber> sortClassNumbers(List<ClassNumber> classNumbers) {
        if (classNumbers == null || classNumbers.isEmpty()) return classNumbers;
        Collections.sort(classNumbers, Comparator.comparingInt(ClassNumber::getNumberValue));
        return classNumbers;
    }

    public static List<ClassLetter> sortClassLetters(List<ClassLetter> classLetters) {
        if (classLetters == null || classLetters.isEmpty()) return classLetters;
        Collections.sort(classLetters, (o1, o2) ->
                o1.getLetterValue().compareToIgnoreCase(o2.getLetterValue()));
        return classLetters;
    }

}
