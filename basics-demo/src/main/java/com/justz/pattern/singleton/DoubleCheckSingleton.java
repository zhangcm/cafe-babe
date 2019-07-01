package com.justz.pattern.singleton;

public class DoubleCheckSingleton {

    // 加上volatile关键字
    private static volatile DoubleCheckSingleton INSTANCE;

    private DoubleCheckSingleton() {}

    public static DoubleCheckSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoubleCheckSingleton();
                    // 实际上是3步
                    // inst = allocate()    // 分配内存
                    // constructor(inst)    // 初始化
                    // INSTANCE = inst      // 赋值

                    // 如果不加volatile，则有可能因为指令重拍变成如下顺序：
                    // inst = allocate()
                    // INSTANCE = inst
                    // constructor(inst)
                    // 如果构造方法耗时很长，其他线程就可能拿到未初始化完成的INSTANCE
                }
            }
        }
        return INSTANCE;
    }
}
