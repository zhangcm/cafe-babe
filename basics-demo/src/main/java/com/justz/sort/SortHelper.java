package com.justz.sort;

import java.util.Random;

/**
 * 排序工具类
 */
public final class SortHelper {

    private static final Random random = new Random();

    private SortHelper() {}

    public static <T extends Comparable<T>> boolean less(T val1, T val2) {
        return val1.compareTo(val2) < 0;
    }

    public static <T extends Comparable<T>> boolean great(T val1, T val2) {
        return val1.compareTo(val2) > 0;
    }

    public static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static Integer[] randomArr(int length) {
        Integer[] arr = new Integer[length];
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

}
