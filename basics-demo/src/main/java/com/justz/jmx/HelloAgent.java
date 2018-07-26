package com.justz.jmx;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author zhangcm
 * @since 1.0, 2018/6/9 上午10:14
 */
public class HelloAgent {

    public static void main(String[] args) throws Exception {

//        MBeanServer server = MBeanServerFactory.createMBeanServer();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        String domainName = "MyMBean";
        ObjectName helloName = new ObjectName(domainName + ":name=HelloWorld");
        mbs.registerMBean(new Hello(), helloName);

        int rmiPort = 1099;
        Registry registry = LocateRegistry.createRegistry(rmiPort);
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + domainName);
        JMXConnectorServer jmxConnector =
                JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        jmxConnector.start();



    }
}
