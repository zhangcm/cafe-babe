package com.justz.jmx;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/9 上午10:12
 */
public class Hello implements HelloMBean {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printHello() {
        System.out.println("Hello world, " + name);
    }

    @Override
    public void printHello(String whoName) {
        System.out.println("Hello, " + whoName);
    }

}
