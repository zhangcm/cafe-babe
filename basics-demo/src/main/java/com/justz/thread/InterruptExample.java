package com.justz.thread;

/**
 * 线程中断
 */
public class InterruptExample {

    private static class MyThread extends Thread {
        @Override
        public void run() {
            // 执行thread.interrupt方法时，interrupted会变成true
            while (!interrupted()) {
                // ..
            }
            System.out.println("Thread interrupted");
        }
    }

    private static class ThreadWithSleep extends Thread {
        @Override
        public void run() {
            try {
                // 执行thread.interrupt方法时，sleep方法会抛异常
                Thread.sleep(2000);
                System.out.println("Thread run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        thread.interrupt();

        ThreadWithSleep thread1 = new ThreadWithSleep();
        thread1.start();
        thread1.interrupt();
    }

}
