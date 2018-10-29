package com.justz.thread;

import java.util.concurrent.*;

/**
 * Fork/Join框架是为了充分利用多核CPU的并行能力。
 *
 * 将一个大任务拆分成多个可以并行的小任务，分别执行，最后将结果汇总起来。
 * 例如，要对10000个数求和，可以拆成10个小任务，分别对1000个数求和，最后把结果汇总起来。
 *
 * Fork/Join由两部分组成: ForkJoinTask和ForkJoinPool。
 * ForkJoinTask决定如何拆分任务，ForkJoinPool用来执行任务。
 * ForkJoinTask提供两个子类：RecursiveTask（有返回值）和RecursiveAction(无返回值)
 *
 * 工作窃取(work-stealing)算法：
 * 使用双向队列(deque)保存任务，每个工作线程都有自己的队列，每次从队列的头部获取要执行的任务。
 * 如果一个线程的所有任务先执行完，则它从其他线程的队列的尾部窃取任务执行。
 *
 * 优点：
 * 1. 充分利用了线程进行并行计算
 * 2. 减少了线程间的竞争（当队列里只有一个任务时还会有竞争）
 * 缺点：
 * 消耗了更多的系统资源，比如创建多个双端队列
 */
public class SumForkJoinTask extends RecursiveTask<Integer> {

    // 控制任务的粒度。大于这个值时，任务继续拆分。小于这个值时，可以直接计算。
    private final int threshold = 5;

    private int first;

    private int last;

    public SumForkJoinTask(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (last - first <= threshold) {
            // 任务足够小则直接计算
            for (int i = first; i <= last; i++) {
                result += i;
            }
        } else {
            // 拆分小任务
            int middle = first + (last - first) / 2;
            SumForkJoinTask leftTask = new SumForkJoinTask(first, middle);
            SumForkJoinTask rightTask = new SumForkJoinTask(middle + 1, last);
            leftTask.fork();
            rightTask.fork();
            result = leftTask.join() + rightTask.join();
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumForkJoinTask task = new SumForkJoinTask(1, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future result = forkJoinPool.submit(task);
        System.out.println(result.get());
    }
}
