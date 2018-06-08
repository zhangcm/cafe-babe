package com.justz.refactor.reorganize.replacemethodwithmethod;

/**
 *
 */
public class Gamma {

    private final ReplaceMethodWithMethodObjDemo demo;
    private int inputVal;
    private int quantity;
    private int yearToDate;

    private int importantValue1;
    private int importantValue2;
    private int importantValue3;

    public Gamma(ReplaceMethodWithMethodObjDemo demo, int inputVal, int quantity, int yearToDate) {
        this.demo = demo;
        this.inputVal = inputVal;
        this.quantity = quantity;
        this.yearToDate = yearToDate;
    }

    int compute() {
        importantValue1 = (inputVal * quantity) + demo.delta();
        importantValue2 = (inputVal * yearToDate) + 100;
        // 可以轻松地使用Extract Method，不必担心参数传递的问题
        importantThing();
        importantValue3 = importantValue2 * 7;
        // and so on
        return importantValue3 -  2 * importantValue1;
    }

    private void importantThing() {
        if (yearToDate - importantValue1 > 100) {
            importantValue2 -= 20;
        }
    }

}
