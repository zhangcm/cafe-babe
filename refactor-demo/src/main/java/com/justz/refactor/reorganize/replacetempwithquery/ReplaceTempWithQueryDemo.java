package com.justz.refactor.reorganize.replacetempwithquery;

/**
 * Replace Temp With Query
 *
 * 注：如果把临时变量替换为一个查询，那么同一个类中所有方法都可以获得这份信息。
 * 在上述情况下，替换相对来说有意义一些
 */
public class ReplaceTempWithQueryDemo {

    private int _quantity = 3;
    private double _itemPrice = 3.25;

    double calcBasePrice() {
        if (basePrice() > 1000) {
            return basePrice() * 0.95;
        }
        return basePrice() * 0.98;
    }

    private double basePrice() {
        return _quantity * _itemPrice;
    }

    /**
     * 两个临时变量
     */
    double getPrice() {
        return basePrice() * discountFactor();
    }

    private double discountFactor() {
        if (basePrice() > 1000) {
            return 0.95;
        } else {
            return 0.98;
        }
    }

}
