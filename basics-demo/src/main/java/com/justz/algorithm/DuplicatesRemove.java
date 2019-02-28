package com.justz.algorithm;

/**
 * 去除数组重复元素
 */
public class DuplicatesRemove {

    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int index = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[index++] = nums[i];
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1, 1, 2};
        int length = new DuplicatesRemove().removeDuplicates(arr);
        for (int i = 0; i < length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
