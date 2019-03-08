package com.justz.algorithm;

/**
 * 最长回文子串
 */
public class LongestPalindrome {

    private static String solution(String s) {
        if (s == null || s.trim().length() <= 1) {
            return s;
        }
        char[] arr = s.trim().toCharArray();
        int length = arr.length;
        int start = 0;
        int end = 0;
        int maxLength = 0;
        int i = 0;
        // 如果剩下的元素已经小于了maxLength，就没必要继续找了
        while (i < length - maxLength) {
            int j = length - 1;
            // 如果i与j的差距已经小于了maxLength，就没必要继续找了
            while (j > (i + maxLength)) {
                if (arr[j] == arr[i] && isPalindrome(arr, i, j)) {
                    if (maxLength < j - i) {
                        maxLength = j - i;
                        start = i;
                        end = j;
                        break;
                    }
                }
                j--;
            }
            i++;
        }
        return s.substring(start, end + 1);
    }

    private static boolean isPalindrome(char[] arr, int start, int end) {
        int i = start, j = end;
        while (i < j) {
            if (arr[i++] != arr[j--]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(solution("babad"));
        System.out.println(solution("cbbd"));
    }
}
