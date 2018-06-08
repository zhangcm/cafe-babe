package com.justz.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 一个流只能有一个terminal操作, 当这个操作执行后, 流就被使用"光"了, 无法再被操作。
 * 所以这必定是流的最后一个操作。 terminal操作的执行, 才会真正开始流的遍历, 并且会生成一个结果
 *
 * 在对一个Stream进行多次转换操作(intermediate)时, 虽然每次都对Stream的每个元素进行转换, 而且是执行多次,
 * 但是时间复杂度并不是N（转换次数)个for循环里的操作次数总和。
 * 转换操作都是lazy的,多个转换操作只会在terminal操作的时候融合起来, 一次循环完成。
 *
 * Stream的源代码中对操作类型有说明
 *
 * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、
 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
 */
public class StreamTerminal {

    /**
     * foreach: 遍历所有元素并执行指定操作
     *
     * 与peek不同的是, 这是一个terminal操作。
     */
    public static void foreach() {
        StreamCreater.createTen().forEach(e -> System.out.print(e + " "));
        System.out.println();
    }

    /**
     * forEachOrdered: 保证并行执行时的顺序
     */
    public static void forEachOrdered() {
        // 输出不是: 1 3 5 4 2
        Stream.of(1, 3, 5, 4, 2).parallel().forEach(e -> System.out.print(e + " "));
        System.out.println();
        // 输出: 1 3 5 4 2
        Stream.of(1, 3, 5, 4, 2).parallel().forEachOrdered(e -> System.out.print(e + " "));
        System.out.println();
    }

    /**
     * toArray: 将Stream中的元素转为一个数组, 只能转为Object数组
     */
    public static void toArray() {
        Object[] people = People.createTen().skip(5).toArray();
        System.out.println(people[0]);
    }

    /**
     * toArray(IntFunction): 将Stream中的元素转为一个指定类型的数组
     */
    public static void toArrayBy() {
        People[] people = People.createTen().skip(6).toArray(People[]::new);
        System.out.println(people[0]);
    }

    /**
     * 根据给定的Comparator查找Stream中最小的元素
     */
    public static void min() {
        People minAgePeople = People.createTen().peek(e -> System.out.print(e.getAge() + " "))
                .min((People p1, People p2) -> Integer.valueOf(p1.getAge()).compareTo(Integer.valueOf(p2.getAge()))).get();
        System.out.println();
        System.out.println(minAgePeople.getAge());
    }

    /**
     * 根据给定的Comparator查找Stream中最大的元素
     */
    public static void max() {
        People minAgePeople = People.createTen().peek(e -> System.out.print(e.getAge() + " "))
                .max((People p1, People p2) -> Integer.valueOf(p1.getAge()).compareTo(Integer.valueOf(p2.getAge()))).get();
        System.out.println();
        System.out.println(minAgePeople.getAge());
    }

    /**
     * 返回Stream中的元素个数
     */
    public static void count() {
        System.out.println(People.createTen().limit(4).skip(2).count());
    }

    /**
     * 对元素进行计算, 只要有一个为true, 就返回true
     */
    public static void anyMatch() {
        boolean result = People.createTen().peek(e -> System.out.print(e.getAge() + " "))
                .anyMatch(people -> people.getAge() < 20);
        System.out.println();
        System.out.println(result);
    }

    /**
     * 对元素进行计算, 除非所有的都为true, 才会返回true。 如果有一个为false, 则立即返回false
     */
    public static void allMatch() {
        boolean result = People.createTen().peek(e -> System.out.print(e.getAge() + " "))
                .allMatch(people -> people.getAge() < 20);
        System.out.println();
        System.out.println(result);
    }

    /**
     * 对元素进行计算, 看是否都不满足, 除非所有都为false, 才会返回true。 只要有一个为true, 就返回false
     */
    public static void noneMatch() {
        boolean result = People.createTen().peek(e -> System.out.print(e.getAge() + " "))
                .noneMatch(people -> people.getAge() < 20);
        System.out.println();
        System.out.println(result);
    }

    /**
     * reduce(BinaryOperator)
     * 将Stream的第一个元素作为result
     * 将result与第二个元素传给BinaryOperator, 计算出来的结果作为新的result, 重复这个步骤直到遍历完Stream中的元素
     * 返回result
     */
    public static void reduce() {
        System.out.println(StreamCreater.createTen().reduce(Integer::sum).get());
    }

    /**
     * reduce(BinaryOperator)
     * 将第一个参数作为result
     * 将result与第一个元素传给BinaryOperator, 计算出来的结果作为新的result, 重复这个步骤直到遍历完Stream中的元素
     * 返回result
     *
     * 与上面方法不同的地方在于初值的设置
     */
    public static void reduceBy() {
        System.out.println(StreamCreater.createTen().reduce(10, Integer::sum));
    }

    public static void reduceWith() {
//        StreamCreater.createTen().reduce();
    }

    /**
     * collect(): 将元素按指定规则收集起来
     * 1. new, 通过参数Supplier 创建一个新的对象
     * 2. reduce, 将步骤1中创建的对象和Stream中的元素传给BiConsumer, 将返回值作为BiConsumer的第一个参数, 遍历Stream中的元素
     * 3. merge, 将结果进行合并
     */
    public static void collect() {
        List<People> peopleList = People.createTen().skip(5).collect((Supplier<List<People>>) ArrayList::new,
                (people, p) -> people.add(p), (people, people2) -> people.addAll(people2));
        System.out.println(peopleList.get(0).getName());
    }

    /**
     * collect(Collector): 使用Collector收集元素
     * Collectors是一个工具类, 里面有现成的工具方法创建Collector, 可以直接使用
     *
     * toList方法实现如下:
     *
     * Collector<T, ?, List<T>> toList() {
     *     return new CollectorImpl<>((Supplier<List<T>>) ArrayList::new, List::add,
     *             (left, right) -> { left.addAll(right); return left; },
     *             CH_ID);
     * }
     */
    public static void collectBy() {
        People.createTen().skip(5).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        foreach();
        forEachOrdered();
        toArray();
        toArrayBy();
        min();
        max();
        count();
        anyMatch();
        allMatch();
        noneMatch();
        reduce();
        reduceBy();
        collect();
    }
}
