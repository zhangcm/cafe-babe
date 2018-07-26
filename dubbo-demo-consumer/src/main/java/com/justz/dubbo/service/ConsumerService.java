package com.justz.dubbo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.Person;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Reference(version = "1.0.0")
    private DemoService demoService;

    public Person sayHello(String name) {
        return demoService.sayHello(name);
    }

}
