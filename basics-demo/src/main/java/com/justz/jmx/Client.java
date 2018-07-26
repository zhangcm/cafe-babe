package com.justz.jmx;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/9 下午3:54
 */
public class Client {

    public static void main(String[] args) throws Exception {
        String domainName = "MyMBean";
        int rmiPort = 1099;

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:"
                + rmiPort + "/" + domainName);
        JMXConnector jmxc = JMXConnectorFactory.connect(url);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        System.out.println("Domains:---------------------");
        String[] domains = mbsc.getDomains();
        for (int i = 0; i < domains.length; i++) {
            System.out.println("\tDomain[" + i + "] = " + domains[i]);
        }
        // MBean count
        System.out.println(String.format("MBean count = %s", mbsc.getMBeanCount()));

        // process attribute
        ObjectName mBeanName = new ObjectName(domainName + ":name=HelloWorld");
        mbsc.setAttribute(mBeanName, new Attribute("Name", "John"));
        System.out.println("Name = " + mbsc.getAttribute(mBeanName, "Name"));

        // 执行Hello中的printHello方法，分别通过代理和rmi的方式执行

        // 通过rmi
        mbsc.invoke(mBeanName, "printHello", null, null);
        mbsc.invoke(mBeanName, "printHello", new String[] {"Tom"}, new String[] {String.class.getName()});

        // 获取mbean的信息
        MBeanInfo info = mbsc.getMBeanInfo(mBeanName);
        System.out.println("Hello Class: " + info.getClassName());
        for (int i = 0; i < info.getAttributes().length; i++) {
            System.out.println("Hello Attribute: " + info.getAttributes()[i].getName());
        }
        for (int i = 0; i < info.getOperations().length; i++) {
            System.out.println("Hello Operation: " + info.getOperations()[i].getName());
        }

        // mbean的对象名
        System.out.println("All Object Name:-----------------");
        Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
        for (Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
            ObjectInstance oi = it.next();
            System.out.println("\t" + oi.getObjectName());
        }
        jmxc.close();

    }

}
