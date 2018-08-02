package com.justz.jvm.classloader.remote;

/**
 * 为了多次载入执行类而加入的加载类
 * 把defineClass方法开放出来，另外只有外部显式调用的时候才会使用loadByte方法
 * 由虚拟机调用时，仍然按照原有的双亲委派规则使用loadClass方法进行类加载
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

}
