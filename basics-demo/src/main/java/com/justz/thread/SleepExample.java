package com.justz.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * sleep()是Thread的方法，会让出cpu，但不会释放锁
 */
public class SleepExample {

    private synchronized void sayHi() {
        System.out.println("before say Hi");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hi");
    }

    private synchronized void sayHello() {
        System.out.println("Hello");
    }

    private void sayByeBye() {
        System.out.println("Bye Bye");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        SleepExample example1 = new SleepExample();
        executorService.execute(example1::sayHi);
        executorService.execute(example1::sayHello);
        executorService.execute(example1::sayByeBye);
        // 执行结果：
        // befor say Hi
        // Bye Bye
        // Hi
        // Hello
    }
}
