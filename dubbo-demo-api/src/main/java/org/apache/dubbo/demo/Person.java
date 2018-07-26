package org.apache.dubbo.demo;

import java.io.Serializable;

/**
 * @author zhangcm
 * @since 1.0, 2018/7/19 上午10:17
 */
public class Person implements Serializable {

    private String name;

    private int age;

    private Child child;

    public Person() {
    }

    public Person(String name, int age, Child child) {
        this.name = name;
        this.age = age;
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
