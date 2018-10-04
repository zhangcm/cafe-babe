package com.justz.sort;

import java.util.Arrays;

import static com.justz.sort.SortHelper.*;

/**
 * 插入排序
 * 思路：
 * 通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入
 *
 * 算法：
 * 1. 从第一个元素开始，该元素可以被认为已经排序
 * 2. 取出下一个元素，从后往前遍历其之前的元素
 * 3. 如果新元素小于该元素，则将该元素右移一位
 * 4. 重复步骤3，直到遇到新元素大于该元素或遍历结束
 * 5. 将新元素插到该元素后
 * 6. 重复步骤2~5
 * 级别：基础排序
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 */
public class InsertionSort<T extends Comparable<T>> {

    public void sort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;
            // 从右往左依次比，遇到大的就右移
            while (j >= 0 && less(temp, arr[j])) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;

            // 另一种思路：从右往左，遇到大的则交换
//            for (int j = i; j > 0 && less(arr[j], arr[j - 1]); j--) {
//                swap(arr, j, j - 1);
//            }

            // 一开始的思路：
            // 1. 在之前的元素里，从头开始，找到第一个大于arr[i]的元素的位置
            // 2. 将该位置及其之后的元素依次右移一位
            // 3. 将arr[i]插入该位置
            // 缺点：循环次数多
//            for (int j = 0; j < i; j++) {
//                if (less(arr[i], arr[j])) {
//                    T temp = arr[i];
//                    for (int k = i; k > j + 1; k--) {
//                        arr[k] = arr[k - 1];
//                    }
//                    arr[j] = temp;
//                    break;
//                }
//            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = randomArr(20);
        System.out.println(Arrays.toString(arr));
        new InsertionSort<Integer>().sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
