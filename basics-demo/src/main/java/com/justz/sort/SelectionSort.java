package com.justz.sort;

import java.util.Arrays;

import static com.justz.sort.SortHelper.less;
import static com.justz.sort.SortHelper.randomArr;
import static com.justz.sort.SortHelper.swap;

/**
 * 选择排序
 * 思路：
 * 级别：基础排序
 * 时间复杂度：
 * 空间复杂度：
 */
public class SelectionSort<T extends Comparable<T>> {

    public void sort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            T min = arr[i];
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (less(arr[j], min)) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    public static void main(String[] args) {
        Integer[] arr = randomArr(20);
        System.out.println(Arrays.toString(arr));
        new SelectionSort<Integer>().sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
