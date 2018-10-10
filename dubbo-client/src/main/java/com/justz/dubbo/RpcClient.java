package com.justz.dubbo;

import com.justz.dubbo.api.HelloService;
import com.justz.dubbo.rpc.RpcFramework;

/**
 * 客户端测试类
 */
public class RpcClient {

    public static void main(String[] args) {
        HelloService helloService = RpcFramework.refer(HelloService.class);
        System.out.println(helloService.sayHello("John"));
    }
}
