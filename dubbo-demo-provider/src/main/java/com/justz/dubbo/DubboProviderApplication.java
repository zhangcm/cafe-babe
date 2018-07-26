package com.justz.dubbo;

import com.justz.dubbo.api.demo.DemoProviderImpl;
import com.justz.dubbo.exporter.DubboExporter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/8 上午10:17
 */
public class DubboProviderApplication {

    public static void main(String[] args) throws Exception {
        DemoProviderImpl demoProvider = new DemoProviderImpl();
        DubboExporter.export(demoProvider);
    }
}
