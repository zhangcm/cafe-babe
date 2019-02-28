package com.justz.algorithm;

import java.util.HashSet;
import java.util.Set;

public class LengthOfLongestSubstring {

    private static int solution(String str) {
        if (str == null) {
            return 0;
        }
        if (str.length() <= 1) {
            return str.length();
        }
        Set<Character> set = new HashSet<>();
        char[] charArr = str.toCharArray();
        int maxLength = 0;
        int curLength = 0;
        for (int i = 0; i < charArr.length; i++) {
            char ch = charArr[i];
            if (set.add(ch)) {
                curLength++;
            } else {
                if (maxLength < curLength) {
                    maxLength = curLength;
                }
                set.clear();
                set.add(ch);
                for (int j = i - 1; j >= 0; j--) {
                    if (charArr[j] == ch) {
                        curLength = i - j;
                        break;
                    }
                    set.add(charArr[j]);
                }
            }
        }
        // 无重复元素
        if (maxLength < curLength) {
            maxLength = curLength;
        }
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(solution("abcabcbb"));
        System.out.println(solution("bbbbb"));
        System.out.println(solution("au"));
        System.out.println(solution("dvdf"));
    }
}
