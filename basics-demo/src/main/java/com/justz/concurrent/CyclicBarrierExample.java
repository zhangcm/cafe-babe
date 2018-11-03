package com.justz.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 等大家都到达某个点之后，再一起执行后面的。
 *
 * 比如某个大型计算分为两步，第二步依赖第一步的结果。
 * 所有线程先执行第一步，执行完第一步之后，使用CyclicBarrier等待其他线程执行完毕，之后再进行第二步。
 */
public class CyclicBarrierExample {

    public static void main(String[] args) {
        int totalThread = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(totalThread);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < totalThread; i++) {
            executorService.execute(() -> {
                System.out.print("before..");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.print("after..");
            });
        }
    }

}
