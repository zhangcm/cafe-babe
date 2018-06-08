package com.justz.refactor.reorganize.removeassigntoparam;

/**
 * Remove Assignments to Parameter
 */
public class RemoveAssignToParamDemo {

    int discount(int inputVal) {
        int result = inputVal;
        if (inputVal > 50) {
            result -= 2;
        }
        return result;
    }

}
