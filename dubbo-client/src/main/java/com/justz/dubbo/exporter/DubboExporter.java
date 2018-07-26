package com.justz.dubbo.exporter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DubboExporter {

    private static Map<String, Object> map = new ConcurrentHashMap<>();
    private static ServerSocket serverSocket = null;

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void export(Object obj) {
        map.put(obj.getClass().getInterfaces()[0].getName(), obj);
        if (serverSocket != null) {
            return;
        }
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new Handler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Handler implements Runnable {

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String request = reader.readLine();
                String[] arr = request.split("#");
                Object object = map.get(arr[0]);
                Method method = object.getClass().getMethod(arr[1]);
                Object response = method.invoke(object);
                OutputStream os = socket.getOutputStream();
                os.write(response.toString().getBytes());
//                os.write("\n###".getBytes());
                os.flush();
            } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
