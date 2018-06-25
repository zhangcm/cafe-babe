package com.justz.dubbo;

import com.justz.dubbo.api.demo.DemoProvider;
import com.justz.dubbo.refer.DubboRefer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/8 上午10:20
 */
public class DubboConsumerApplication {

    public static void main(String[] args) {
        DemoProvider demoProvider = DubboRefer.refer(DemoProvider.class);
        String result = demoProvider.sayHello();
        System.out.println(result);
        result = demoProvider.sayHi();
        System.out.println(result);
    }
}
