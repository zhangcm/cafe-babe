package com.justz.algorithm;

/**
 * 两数相加
 */
public class LinkSum {

    public ListNode solution(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }

        ListNode sum = null;
        ListNode temp1 = l1;
        ListNode temp2 = l2;
        ListNode temp3 = null;

        boolean full = false;
        while (temp1 != null || temp2 != null) {
            int val1 = 0;
            int val2 = 0;
            if (temp1 != null) {
                val1 = temp1.val;
                temp1 = temp1.next;
            }
            if (temp2 != null) {
                val2 = temp2.val;
                temp2 = temp2.next;
            }
            int val3 = full ? val1 + val2 + 1 : val1 + val2;
            if (val3 >= 10) {
                full = true;
                val3 = val3 - 10;
            } else {
                full = false;
            }
            if (sum == null) {
                sum = new ListNode(val3);
                temp3 = sum;
            } else {
                temp3.next = new ListNode(val3);
                temp3 = temp3.next;
            }
        }
        if (full) {
            temp3.next = new ListNode(1);
        }
        return sum;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {

    }
}
