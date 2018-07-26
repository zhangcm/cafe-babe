package com.justz.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义classloader
 *   1. 继承ClassLoader
 *   2. 复写loadClass方法
 *      2.1 获取class文件的InputStream
 *      2.2 调用父类的defineClass方法
 * @author zhangcm
 * @since 1.0, 2018/7/26 上午8:53
 */
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream is = getClass().getResourceAsStream(fileName);
            if (is == null) {
                return super.loadClass(name);
            }
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }

    public static void main(String[] args) throws Exception {
        ClassLoader myClassLoader = new MyClassLoader();
        Object obj = myClassLoader.loadClass("com.justz.classloader.MyClassLoader").newInstance();

        System.out.println(obj.getClass());
        System.out.println(obj instanceof MyClassLoader);
    }
}
