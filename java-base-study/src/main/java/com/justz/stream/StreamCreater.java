package com.justz.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream的创建
 */
public class StreamCreater {

    /**
     * 通过Stream的of方法创建
     * @return
     */
    public static Stream<Integer> createTen() {
        return Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    /**
     * 通过集合对象的stream方法创建
     * @return
     */
    public static Stream<Integer> createByCollection() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return list.stream();
    }

    /**
     * 通过generate方法生成一个无限元素的Stream,元素通过调用Supplier的get方法产生
     * 需配合limit方法使用
     * @return
     */
    public static Stream<Integer> generate() {
        return Stream.generate(() -> (int) (Math.random() * 100)).limit(10);
    }

    /**
     * 使用iterate方法生成一个无限元素的Stream,
     * 元素的生成是通过重复对给定的种子值(seed)调用用户指定函数
     * 包含的元素为: seed, f(seed), f(f(seed)), f(f(f(seed)))
     * 需配合limit方法使用
     * 第一个参数作为seed,并传给后面的方法,将方法的返回值作为方法下一次执行的输入值递归调用
     * public T next() {
     *     return t = (t == Streams.NONE) ? seed : f.apply(t);
     * }
     */
    public static Stream<Integer> iterate() {
        return Stream.iterate(3, item -> item + 1).limit(10);
    }

    public static void main(String[] args) {
        System.out.println(createTen().collect(Collectors.toList()));
        System.out.println(createByCollection().collect(Collectors.toList()));
        System.out.println(generate().collect(Collectors.toList()));
        System.out.println(iterate().collect(Collectors.toList()));
    }

}
