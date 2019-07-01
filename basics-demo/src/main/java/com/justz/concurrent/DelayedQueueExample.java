package com.justz.concurrent;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列demo
 * 注：对于某些会过期的数据，可以使用这种方式获取。比全部扫描一遍性能要高
 */
public class DelayedQueueExample {

    private static DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();


    static class Producer {

        public void produce(DelayedTask delayedTask) {
            delayQueue.put(delayedTask);
        }

    }

    static class Consumer implements Runnable {

        public void run() {
            while (!delayQueue.isEmpty()) {
                // 从队列中获取延时任务
                DelayedTask task = delayQueue.poll();
                if (task == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("休眠1秒");
                } else {
                    System.out.println(task.getName());
                }
            }
        }
    }

    // 延迟队列的元素必须实现了Delayed接口
    static class DelayedTask implements Delayed {

        private String name;
        private long executeTime;

        DelayedTask(long delayed) {
            this.name = "延迟" + delayed;
            this.executeTime = System.currentTimeMillis() + delayed;
        }

        // 此方法返回小于0的数时，元素可以被take或者poll到
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        // PriorityQueue，决定了元素的顺序
        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.executeTime, ((DelayedTask) o).getExecuteTime());
        }

        public long getExecuteTime() {
            return executeTime;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.produce(new DelayedTask(5000));
        producer.produce(new DelayedTask(3000));
        // 虽然第一个任务先加进去的，但由于延迟时间较长，并不是第一个被取出来的
        new Thread(new Consumer()).start();
        // 执行结果：
        // 休眠1秒
        // 休眠1秒
        // 休眠1秒
        // 延迟3000
        // 休眠1秒
        // 休眠1秒
        // 延迟5000
    }
}
