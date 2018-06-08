package com.justz.refactor.reorganize.inlinemethod;

/**
 * 内部代码和函数名同样清晰可读
 */
public class InlineMethodDemo {

    int _numberOfLateDeliveries;

    int getRating() {
        return _numberOfLateDeliveries > 5 ? 2 : 1;
    }

}
