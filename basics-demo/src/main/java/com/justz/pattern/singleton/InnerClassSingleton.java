package com.justz.pattern.singleton;

public class InnerClassSingleton {

    private InnerClassSingleton() {}

    public static InnerClassSingleton getInstance() {
        return Inner.INSTANCE;
    }

    private static class Inner {

        private static InnerClassSingleton INSTANCE = new InnerClassSingleton();

    }
}
