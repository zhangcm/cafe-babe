package com.justz.dubbo.api.impl;

import com.justz.dubbo.api.HelloService;

/**
 * 服务实现
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
