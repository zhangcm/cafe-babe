package com.justz.stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流的转换操作, 一个流后面可以跟零个或多个intermediate操作。
 * 目的主要是打开流, 做出某种程度的数据映射/过滤, 然后返回一个新的流, 交给下一个操作使用。
 * 这类操作都是惰性化(lazy)的。仅仅调用到这类方法, 并没有真正开始流的遍历
 *
 * Stream的源代码中对操作类型有说明
 *
 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、
 * limit、 skip、 parallel、 sequential、 unordered
 */
public class StreamIntermediate {

    /**
     * filter: 对Stream中的元素进行过滤, 只保留计算结果为true的元素
     * 如下, 只保留偶数
     */
    public static void filter() {
        StreamCreater.createTen().filter(num -> num % 2 == 0).forEach(System.out::println);
    }

    /**
     * map: 遍历Stream中的元素, 对元素进行一个转换
     */
    public static void map() {
        StreamCreater.createTen().map(num -> num * 2).forEach(System.out::println);
    }

    /**
     * mapToInt: 遍历Stream中的元素, 将元素转为int
     * mapToLong, mapToDouble与mapToInt类似
     */
    public static void mapToInt() {
        People.createTen().mapToInt(people -> people.getAge()).forEach(System.out::println);
    }

    /**
     * flatMap: 遍历Stream中的元素, 将每一个元素转成一个Stream, 然后将这些Stream中的元素合并到一个Stream中
     * flatMapToInt, flatMapToLong, flatMapToDouble与flatMap类似
     * @throws Exception
     */
    public static void flatMap() throws Exception {
        // 读取文件行
        Stream<String> lines = Files.lines(Paths.get(StreamIntermediate.class.getClassLoader().getResource("flatmap.txt").toURI()));
        // 获取每一行的字
        Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));
        words.limit(10).forEach(System.out::println);

        // 将team中的people list放在一起, 组成一个新的list
        List<Team> teams = Arrays.asList(
                new Team(Arrays.asList(new People("aaa1", 21), new People("aaa2", 22))),
                new Team(Arrays.asList(new People("bbb1", 21), new People("bbb2", 22))));
        List<People> peopleList = teams.stream().flatMap(team -> team.getPeopleList().stream()).collect(Collectors.toList());
        System.out.println(peopleList.size());
    }

    /**
     * distinct: 消除重复的元素
     */
    public static void distinct() {
        // 输出: 1, 2, 3, 4, 5
        print(Stream.of(1, 1, 2, 3, 4, 5, 5).distinct().collect(Collectors.toList()));
    }

    /**
     * sorted: 将元素排序
     */
    public static void sort() {
        // 输出: 1, 3, 2, 5, 4
        print(Stream.of(1, 3, 2, 5, 4).collect(Collectors.toList()));
        // 输出: 1, 2, 3, 4, 5
        print(Stream.of(1, 3, 2, 5, 4).sorted().collect(Collectors.toList()));
    }

    /**
     * sorted(Comparator): 自定义Comparator对元素进行排序
     */
    public static void sortBy() {
        print(Arrays.asList(new People("aaa3", 23), new People("aaa1", 21), new People("aaa2", 22)));
        print(Arrays.asList(new People("aaa3", 23), new People("aaa1", 21), new People("aaa2", 22))
                .stream()
                .sorted((People p1, People p2) -> Integer.valueOf(p1.getAge()).compareTo(Integer.valueOf(p2.getAge())))
                .collect(Collectors.toList()));

    }

    /**
     * peek: 为Stream中的元素增加一些额外的行为, 比如值的打印, 可以用于调试
     *
     * 元素被消费的时候触发
     */
    public static void peek() {
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .filter(e -> e.length() > 4)
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
        /* 输出结果:
         * Filtered value: three
         * Mapped value: THREE
         * Filtered value: four
         *
         * 代码可以改写为如下:
         * List<String> numList = Arrays.asList("one", "two", "three", "four");
         * for (String num : numList) {
         *     // filter
         *     if (!(num.length() > 3)) {
         *         continue;
         *     }
         *     // peek
         *     System.out.println("Filtered value: " + num);
         *     // filter
         *     if (!(num.length() > 4)) {
         *         continue;
         *     }
         *     // map
         *     num = num.toUpperCase();
         *     // peek
         *     System.out.println("Mapped value: " + num);
         * }
         *
         * 对四个元素进行循环
         * 首先是one执行第一个filter, 不通过, 循环下一个元素
         * 接下来是two执行第一个filter, 也不通过, 循环下一个元素
         * 接下来是three, 执行filter通过, 执行peek, 输出Filtered value: three
         *        执行第二个filter通过, 执行map, 执行peek, 输出Mapped value: THREE, 循环下一个元素
         * 接下来是four, 执行filter通过, 执行peek, 输出Filtered value: four, 执行第二个filter不通过, 循环结束
         *
         * 上面的例子中, 这么多操作, 其实只有一个循环。 把这个循环当作一条流水线, intermediate操作相当于阀门。
         * 每一个元素, 在经过阀门时, 会做一些事情, 如果不符合要求, 就会在中途被丢掉, 不会经过后面的阀门。
         * 比如one, two在经过第一个filter阀门的时候就被丢掉了。
         * 而three则通过了所有阀门, four在经过peek之后在下一个filter被丢弃, 所以就产生了上面的输出结果
         */

    }

    /**
     * 返回一个Stream指定的前n个元素
     *
     * 操作类型: short-circuiting stateful intermediate
     *
     * 注意: 在一个并行有序的多元素Stream中执行这个操作会比较耗时
     */
    public static void limit() {
        print(StreamCreater.createTen().limit(5).collect(Collectors.toList()));
    }

    /**
     * 跳过指定个数的元素, 返回剩下的元素
     *
     * 操作类型: stateful intermediate
     *
     * 注意: 在一个并行有序的多元素Stream中执行这个操作会比较耗时
     */
    public static void skip() {
        print(StreamCreater.createTen().skip(5).collect(Collectors.toList()));
    }

    private static void print(List<Object> list) {
        System.out.println(String.join(" ", list.stream().map(obj -> obj.toString()).collect(Collectors.toList())));
    }

    public static void main(String[] args) throws Exception {
//        filter();
//        map();
//        mapToInt();
//        flatMap();
//        distinct();
//        sort();
//        sortBy();
//        peek();
//        limit();
        skip();
    }

}
