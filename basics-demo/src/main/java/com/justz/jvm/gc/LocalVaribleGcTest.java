package com.justz.jvm.gc;

/**
 * 局部变量表 内存回收测试
 * VM Option: -verbose:gc
 * @author zhangcm
 * @since 1.0, 2018/7/27 上午9:29
 */
public class LocalVaribleGcTest {

    /**
     * 执行结果：
     * [GC (System.gc())  69468K->66040K(251392K), 0.0010195 secs]
     * [Full GC (System.gc())  66040K->65934K(251392K), 0.0061744 secs]
     *
     * GC的时候，placeholder还在作用域内
     */
//    public static void main(String[] args) {
//        byte[] placeholder = new byte[64 * 1024 * 1024];
//        System.gc();
//    }

    /**
     * 执行结果：
     * [GC (System.gc())  69468K->66040K(251392K), 0.0010232 secs]
     * [Full GC (System.gc())  66040K->65934K(251392K), 0.0064072 secs]
     *
     * placeholder还在局部变量表内，GC Roots能找到
     */
//    public static void main(String[] args) {
//        {
//            byte[] placeholder = new byte[64 * 1024 * 1024];
//        }
//        System.gc();
//    }

    /**
     * 执行结果：
     * [GC (System.gc())  69468K->66040K(251392K), 0.0012896 secs]
     * [Full GC (System.gc())  66040K->398K(251392K), 0.0061898 secs]
     *
     * 局部变量表的slot是会复用的，int a = 0 把placeholder原本占用的slot复用了，所以placeholder可以被回收
     */
    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }
}
