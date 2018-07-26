package com.justz.dubbo;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.justz.dubbo.api.demo.DemoProvider;
import com.justz.dubbo.service.ConsumerService;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.ListResponse;
import org.apache.dubbo.demo.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/8 上午10:20
 */
public class DubboConsumerApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[] {"META-INF/spring/dubbo-demo-consumer.xml"});
        ctx.start();

        String name = "hello";
        DemoService demoService = (DemoService) ctx.getBean("demoService");

//        Person obj = demoService.sayHello(name);
//        System.out.println(obj.getChild().getFather());

//        List<Person> list = demoService.queryList(name);
//        System.out.println(list.get(0).getChild().getAge());

        ListResponse<Person> response = demoService.queryResponse(name);
        System.out.println(response.getData().get(0).getChild().getAge());

//        int intVal = demoService.getInt(name);
//        System.out.println(intVal);

//        BigDecimal bdValue = demoService.getBigDecimal(name);
//        System.out.println(bdValue);

//        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
//        reference.setInterface("com.foo.BarService");
//        reference.setVersion("1.0.0");
//        reference.setGeneric(true);
//        GenericService genericService = reference.get();
//        Object result = genericService.$invoke("test", new String[] {"java.lang.String"}, new Object[] {"test"});
//        System.out.println(result);


//        DemoService demoService = (DemoService) ctx.getBean("demoService");
//        String hello = demoService.sayHello("world");
//        System.out.println(hello);

//        while (true) {
//            String hello = demoService.sayHello("world");
//            System.out.println(hello);
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }
}
