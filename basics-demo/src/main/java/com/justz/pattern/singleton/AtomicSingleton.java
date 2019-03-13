package com.justz.pattern.singleton;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicSingleton {

    private static AtomicReference<AtomicSingleton> INSTANCE = new AtomicReference<>();

    private AtomicSingleton() {}

    public static AtomicSingleton getInstance() {
        for (;;) {
            AtomicSingleton current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new AtomicSingleton();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }
}
