package com.example.studentmanagementsystemandroidapp.utils;

import com.example.studentmanagementsystemandroidapp.exceptions.RequestValidationException;

public class ValidationUtils {
    public ValidationUtils() {
    }

    public static void validateNameOrSurname(String input, String fieldName) {
        // Ensure that the name only contains letters, single spaces, hyphens, or apostrophes
        if (!input.matches("^[\\p{L}\\s'-]+$")) {
            throw new RequestValidationException(fieldName + " yalnız hərflər, tək boşluqlar, tirələr və apostroflar daxil etməlidir.");
        }
    }

    public static void validateSingleSpace(String input) {
        // Pattern to allow any characters with no more than one space between them
        if (!input.matches("^(?!.* {2,}).+$")) {
            throw new RequestValidationException("Yalnız bir boşluq istifadə edilə bilər.");
        }
    }

    public static void validateSingleLetter(String input) {
        if (!input.matches("^[\\p{L}]$")) {
            throw new RequestValidationException("Sinif hərfi yalnız bir hərf ola bilər.");
        }
    }

    public static void validateMobileNumber(String input) {
        if (!input.matches("[\\d+]+")) {
            throw new RequestValidationException(
                    "Mobil nömrə yalnız rəqəmlər və '+' simvolu qəbul edir.");
        }
    }

    public static void validateStudentCode(String input) {
        if (!input.matches("\\d{7}")) {
            throw new RequestValidationException(
                    "İş nömrəsi yalnız 7 rəqəm olmalıdır.");
        }
    }
}
