package com.justz.jmx;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/9 上午10:11
 */
public interface HelloMBean {

    String getName();

    void setName(String name);

    void printHello();

    void printHello(String whoName);
}
