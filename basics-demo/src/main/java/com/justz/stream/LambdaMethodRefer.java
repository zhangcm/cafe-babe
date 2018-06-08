package com.justz.stream;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 在stream后面的流式处理中, 如果要使用 类名::方法名 的方式, 需要满足一下条件其中之一
 * 1. 类名与stream的泛型类相同, 方法不做限制
 * 2. 类名与stream的泛型类不同, 方法必须是该类的static方法
 * 3. 类名与stream的泛型类不同且方法不是static的, 需要使用 实例::方法名 的方式
 */
public class LambdaMethodRefer {

    private String expression;

    public LambdaMethodRefer() {}

    public LambdaMethodRefer(String expression) {
        this.expression = expression;
    }

    public void printExpression() {
        System.out.println(expression);
    }

    private static void print(String s) {
        System.out.println(s);
    }

    public String hello(String s) {
        System.out.println(s);
        return s;
    }

    /**
     * 使用 类名::new 方式
     * @return
     */
    public Stream<LambdaMethodRefer> createByNew() {
        return Stream.generate(LambdaMethodRefer::new).limit(10);
    }

    public static void main(String[] args) {
        // Arrays.asList("aaa", "bbb").stream()创建了一个String的stream
        // 在流的处理中, 如果需要使用String的方法可以直接用String::方法名
        Arrays.asList("aaa", "bbb").stream().peek(String::toUpperCase).findFirst();
        // 如果要使用其他类处理, 使用类名::方法名时, 需要保证方法是static类型的, 如下
        Arrays.asList("aaa", "bbb").stream().peek(LambdaMethodRefer::print).findFirst();
        // 下面这种方式编译时不通过的
//        Arrays.asList("aaa", "bbb").stream().peek(LambdaMethodRefer::hello).findFirst();
        // 使用其他类处理时, 如果要使用非static方法, 可以传该类的实例, 如下
        Arrays.asList("aaa", "bbb").stream().peek(new LambdaMethodRefer()::hello).findFirst();

        // 下面的例子创建了一个LambdaMethodRefer的stream, 虽然printExpression不是static方法, 但是可以用类名直接引用
        Arrays.asList(new LambdaMethodRefer("aaa"), new LambdaMethodRefer("bbb"))
                .stream().peek(LambdaMethodRefer::printExpression).findFirst();

        System.out.println(new LambdaMethodRefer().createByNew().findFirst());
    }

}
