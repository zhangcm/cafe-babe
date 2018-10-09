package com.justz.sort;

import java.util.Arrays;

import static com.justz.sort.SortHelper.less;
import static com.justz.sort.SortHelper.randomArr;
import static com.justz.sort.SortHelper.swap;

/**
 * 冒泡排序
 * 思路：从左到右不断交换相邻逆序的元素，在一轮的循环之后，可以让最大的元素上浮到右侧
 * 级别：基础排序
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 */
public class BubbleSort<T extends Comparable<T>> {

    public void sort(T[] arr) {
        for (int len = arr.length, i = len - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (less(arr[j + 1], arr[j])) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = randomArr(20);
        System.out.println(Arrays.toString(arr));
        new BubbleSort<Integer>().sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
