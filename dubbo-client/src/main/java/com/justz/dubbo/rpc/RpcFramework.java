package com.justz.dubbo.rpc;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RPC框架
 */
public class RpcFramework {

    public static void export(Object target) {
        try {
            ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket();
            serverSocket.bind(new InetSocketAddress(20880));
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    Invocation invocation = (Invocation) in.readObject();
                    Method method = target.getClass().getMethod(invocation.getMethodName(), invocation.getParamTypes());
                    Object result = method.invoke(target, invocation.getArgs());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    try {
                        out.writeObject(result);
                    } catch (Throwable t) {
                        out.writeObject(t);
                    } finally {
                        out.flush();
                        out.close();
                        in.close();
                    }
                } finally {
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static <T> T refer(Class<T> clazz) {
        ClientInvocationHandler handler = new ClientInvocationHandler();
        return (T) Proxy.newProxyInstance(RpcFramework.class.getClassLoader(), new Class[]{clazz}, handler);
    }

    static class ClientInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Invocation invocation = new Invocation(proxy.getClass(), method.getName(), args, method.getParameterTypes());
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(20880));
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(invocation);
                out.flush();

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                return in.readObject();
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }
        }
    }
}
