package com.justz.sort;

import java.util.Arrays;

import static com.justz.sort.SortHelper.less;
import static com.justz.sort.SortHelper.randomArr;
import static com.justz.sort.SortHelper.swap;

/**
 * 快速排序
 * 思路：
 * 级别：基础排序
 * 时间复杂度：
 * 空间复杂度：
 */
public class QuickSort<T extends Comparable<T>> {

    public void sort(T[] arr) {
        this.sort(arr, 0, arr.length - 1);
    }

    private void sort(T[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        T base = arr[low];
        int i = low;
        for (int j = high; j > i; j--) {
            if (less(arr[j], base)) {
                for (i = i + 1; i < j; i++) {
                    if (less(base, arr[i])) {
                        swap(arr, i, j);
                        break;
                    }
                }
            }
        }
        swap(arr, low, i);
        sort(arr, low, i - 1);
        sort(arr, i + 1, high);
    }

    public static void main(String[] args) {
        Integer[] arr = randomArr(20);
        System.out.println(Arrays.toString(arr));
        new QuickSort<Integer>().sort(arr);
        System.out.println(Arrays.toString(arr));
    }


}
