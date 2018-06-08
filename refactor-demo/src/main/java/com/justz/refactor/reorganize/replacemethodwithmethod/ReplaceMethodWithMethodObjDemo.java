package com.justz.refactor.reorganize.replacemethodwithmethod;

/**
 * Replace Method With Method Object
 */
public class ReplaceMethodWithMethodObjDemo {

    int gamma(int inputVal, int quantity, int yearToDate) {
        return new Gamma(this, inputVal, quantity, yearToDate).compute();
    }

    int delta() {
        // do something
        return 0;
    }
}
