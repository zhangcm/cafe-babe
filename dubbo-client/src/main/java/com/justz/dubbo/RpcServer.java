package com.justz.dubbo;

import com.justz.dubbo.api.impl.HelloServiceImpl;
import com.justz.dubbo.rpc.RpcFramework;

/**
 * 服务端测试类
 */
public class RpcServer {

    public static void main(String[] args) {
        RpcFramework.export(new HelloServiceImpl());
    }
}
