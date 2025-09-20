package com.example.studentmanagementsystemandroidapp.utils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SortUtils {
    public static <T> List<T> sortNumbersInAscOrder(List<T> list, Function<T, Integer> keyExtractor) {
        list.sort(Comparator.comparingInt(keyExtractor::apply));
        return list;
    }

    public static <T> List<T> sortInAlphabeticalOrder(List<T> list, Function<T, String> keyExtractor) {
        list.sort(Comparator.comparing(keyExtractor, String.CASE_INSENSITIVE_ORDER));
        return list;
    }
}
