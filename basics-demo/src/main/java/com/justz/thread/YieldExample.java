package com.justz.thread;

/**
 * yield
 * 给调度器一个建议，当前线程可以让出处理器，让别的线程执行。
 * 只是建议，调度器可忽略
 * 如果调度器采纳了，当前线程会从运行状态变为就绪状态，等待再次被调度
 */
public class YieldExample {

    private static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println("yield before");
            Thread.yield();
            System.out.println("yield after");
        }
    }

    private static class Thread2 extends Thread {
        @Override
        public void run() {
            System.out.println("hello, thread2");
        }
    }

    private static class Thread3 extends Thread {
        @Override
        public void run() {
            System.out.println("hello, thread3");
        }
    }

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        Thread3 thread3 = new Thread3();
        thread1.start();
        thread2.start();
        thread3.start();
        // 执行结果1:
        // yield before
        // hello, thread3
        // hello, thread2
        // yield after
        // 执行结果2:
        // yield before
        // hello, thread2
        // yield after
        // hello, thread3
    }
}
