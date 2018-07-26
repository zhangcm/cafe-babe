package org.apache.dubbo.demo;

import java.io.Serializable;

/**
 * @author zhangcm
 * @since 1.0, 2018/7/19 上午10:25
 */
public class Child implements Serializable {

    private String name;

    private String father;

    private int age;

    public Child() {
    }

    public Child(String name, String father, int age) {
        this.name = name;
        this.father = father;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
