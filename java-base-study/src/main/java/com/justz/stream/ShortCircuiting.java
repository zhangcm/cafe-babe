package com.justz.stream;

import java.util.stream.Stream;

/**
 * short-circuiting:
 * 1. 对于一个intermediate操作, 接受一个无限大的Stream, 返回一个有限的新Stream
 * 2. 对于一个terminal操作, 接受一个无限大的Stream, 但能在有限的时间计算出结果
 *
 * Stream的源代码中对操作类型有说明
 *
 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
 */
public class ShortCircuiting {

    /**
     * findFirst: 获取stream中的第一个元素,返回一个Optional
     * 通过findFirst可以实现break的效果
     */
    public static void findFirst() {
        System.out.println(StreamCreater.createTen().findFirst());
    }

    public static void findFirstAndGet() {
        System.out.println(StreamCreater.createTen().findFirst().get());
    }

    /**
     * 经过filter之后新的Stream里面为6,7,8,9,10
     * 所以findFirst会返回6
     */
    public static void findFirstWithFilter() {
        System.out.println(StreamCreater.createTen().filter(i -> i > 5).findFirst().get());
    }

    /**
     * findAny: 从Stream中获取任意一个满足条件的元素, 适合并行操作
     *
     * 与findFirst的区别:
     * 在串行情况下, 两个方法无区别。
     * 在并行情况下, findAny速度更快, 但多次执行的结果可能不一样
     */
    public static void findAny() {
        // 输出1,2,3,4,5,6 最终结果是6, 6以后的元素没再执行
        StreamCreater.createTen().peek(System.out::println).filter(i -> i > 5).findAny().get();

        // 从300个数字中顺序找到第一个数字
        int result = Stream.iterate(1, item -> item + 1).limit(300).findAny().get();
        System.out.println(result);   // 1,
        // 从300个数字中并行找到第一个数字。因为并行执行的时候, 会将300个数字分为N组, 然后随机找一组执行
        // 所以输出结果是在1~300之间
        result = Stream.iterate(1, item -> item + 1).limit(300).parallel().findAny().get();
        System.out.println(result);   // 1~300之间的随机数字

        // 从1~300中并行查找第一个大于60的数字, 每次执行结果都可能不一样
        int result1 = Stream.iterate(1, item -> item + 1).limit(300).parallel().filter(i -> i > 60).findAny().get();
        int result2 = Stream.iterate(1, item -> item + 1).limit(300).parallel().filter(i -> i > 60).findAny().get();
        // 从1~300中顺序查找第一个大于60的数字, 结果必然为61
        int result3 = Stream.iterate(1, item -> item + 1).limit(300).filter(i -> i > 60).findAny().get();
        System.out.println(result1 == 61); // false, maybe true
        System.out.println(result2 == 61); // false, maybe true
        System.out.println(result3 == 61); // true
        System.out.println(result1 != result2); // true, maybe false
        System.out.println(result1 == result3); // false, maybe true
        System.out.println(result2 == result3); // false, maybe true
    }

    public static void main(String[] args) {
        findFirst(); // Optional[1]
        findFirstAndGet(); // 1
        findFirstWithFilter(); // 6
        findAny();
    }
}
