package com.justz.sort;

import java.util.Arrays;

import static com.justz.sort.SortHelper.less;
import static com.justz.sort.SortHelper.randomArr;
import static com.justz.sort.SortHelper.swap;

/**
 * 冒泡排序
 * 思路：遍历数组，跟其右侧的一个一个比，遇到小的则交换
 * 级别：基础排序
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 */
public class BubbleSort<T extends Comparable<T>> {

    public void sort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (less(arr[j], arr[i])) {
                    swap(arr, i, j);
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
