package com.justz.pattern.singleton;

public class SynchronizeSingleton {

    private static SynchronizeSingleton INSTANCE;

    private SynchronizeSingleton() {}

    public static synchronized SynchronizeSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SynchronizeSingleton();
        }
        return INSTANCE;
    }
}
