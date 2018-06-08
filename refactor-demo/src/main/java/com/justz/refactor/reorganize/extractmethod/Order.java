package com.justz.refactor.reorganize.extractmethod;

public class Order {

    private String _name;
    private double amount;

    public Order() {
    }

    public Order(String _name, double amount) {
        this._name = _name;
        this.amount = amount;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
