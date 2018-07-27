package com.justz.jvm.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 委派父类加载的自定义classloader
 *   1. 继承ClassLoader
 *   2. 复写findClass方法（先委托父类加载，父类未加载，自己再加载）
 *      2.1 获取class文件的InputStream
 *      2.2 调用父类的defineClass方法
 * @author zhangcm
 * @since 1.0, 2018/7/26 上午8:53
 */
public class MyDelegateClassLoader extends ClassLoader {

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
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
        ClassLoader classLoader = new MyDelegateClassLoader();

        Object obj = classLoader.loadClass("com.justz.jvm.classloader.MyDelegateClassLoader").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof MyDelegateClassLoader);
    }
}
