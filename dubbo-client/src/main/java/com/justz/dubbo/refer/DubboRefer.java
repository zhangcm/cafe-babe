package com.justz.dubbo.refer;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class DubboRefer {

    public static <T> T refer(Class<T> clazz) {

        Object obj = Proxy.newProxyInstance(DubboRefer.class.getClassLoader(), new Class[] {clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 8080);
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                os.write((clazz.getName() + "#" + method.getName() + "\n").getBytes());
                os.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }
        });
        return (T) obj;
    }
}
