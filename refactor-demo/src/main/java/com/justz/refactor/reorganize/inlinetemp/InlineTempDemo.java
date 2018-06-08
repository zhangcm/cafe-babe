package com.justz.refactor.reorganize.inlinetemp;

/**
 * @author OF1264
 * @since 1.0, 16/1/6 下午4:05
 */
public class InlineTempDemo {

    Order _order = new Order(12);

    boolean canGetGift() {
        return _order.basePrice() > 1000;
    }

}

class Order {
    double _basePrice;

    public Order(double basePrice) {
        this._basePrice = basePrice;
    }

    double basePrice() {
        return _basePrice;
    }
}
