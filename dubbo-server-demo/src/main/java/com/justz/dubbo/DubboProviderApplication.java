package com.justz.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/8 上午10:17
 */
public class DubboProviderApplication {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {"META-INF/spring/dubbo-demo-provider.xml"});
        ctx.start();
        System.in.read();
    }
}
