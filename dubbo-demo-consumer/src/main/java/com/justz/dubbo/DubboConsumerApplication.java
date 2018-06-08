package com.justz.dubbo;

import com.justz.dubbo.api.demo.DemoProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/8 上午10:20
 */
public class DubboConsumerApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[] {"META-INF/spring/dubbo-demo-consumer.xml"});
        ctx.start();
        DemoProvider demoProvider = (DemoProvider) ctx.getBean("demoProvider");
        String hello = demoProvider.sayHello("world");
        System.out.println(hello);
    }
}
