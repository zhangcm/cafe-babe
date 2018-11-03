package com.justz.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * wait()/notifyAll()必须在同步方法或同步块中
 */
public class WaitNotifyExample {

    private synchronized void before() {
        System.out.println("before");
        notifyAll();
    }

    private synchronized void after() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotifyExample example = new WaitNotifyExample();
        executorService.execute(example::after);
        executorService.execute(example::before);
    }
}
