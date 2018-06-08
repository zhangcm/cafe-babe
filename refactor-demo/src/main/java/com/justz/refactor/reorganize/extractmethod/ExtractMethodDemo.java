package com.justz.refactor.reorganize.extractmethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Extract Method Demo
 */
public class ExtractMethodDemo {

    String _name;

    private List<Order> _orders = new ArrayList<Order>(Arrays.asList(new Order("aaa", 12.2), new Order("bbb", 5.5)));

    void printOwing() {
        printBanner();

        double outstanding = getOutstanding();

        printDetail(outstanding);
    }

    private double getOutstanding() {
        double result = 0.0;

        for (Order order : _orders) {
            result  += order.getAmount();
        }
        return result;
    }

    private void printDetail(double amount) {
        // print details
        System.out.println("name: " + _name);
        System.out.println("amount: " + amount);
    }

    void printBanner() {
        // print banner
        System.out.println("*******************************");
        System.out.println("******** Customer Owes ********");
        System.out.println("*******************************");
    }

}
