package com.justz.dubbo.api.demo;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/8 上午10:07
 */
public class DemoProviderImpl implements DemoProvider {

    public String sayHello(String name) {
        return "Hello " + name;
    }

}
