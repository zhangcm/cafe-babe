package com.justz.refactor.reorganize.introexplainvar;

/**
 * Introduce Explaining Variable
 * 表达式有可能非常复杂而难以阅读，临时变量可以将表达式分解成较为容易管理的形式
 */
public class IntroduceExplainingVarDemo {

    private String platform = "Mac OS";
    private String browser = "safari";
    private boolean wasInitialized = false;
    private int resize = 10;

    void doSomething() {
        if (isMac() && isIE() && wasInitialized && wasResized()) {
            // do something
        }
    }

    private boolean wasResized() {
        return resize > 0;
    }

    private boolean isIE() {
        return browser.toLowerCase().contains("ie");
    }

    private boolean isMac() {
        return platform.toLowerCase().contains("mac");
    }
}
