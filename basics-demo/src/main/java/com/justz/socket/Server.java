package com.justz.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void listen(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("启动服务器，监听端口: " + port + " ...");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("连接到来");
            executorService.submit(new Handler(socket));
        }
    }

    class Handler implements Runnable {

        private Socket socket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            // BufferedReader如果读不到换行符，会一直阻塞，此处使用DataInputStream
            try (DataInputStream ois = new DataInputStream(socket.getInputStream());
                 DataOutputStream oos = new DataOutputStream(socket.getOutputStream())) {
                String request = ois.readUTF();
                System.out.println("request: " + request);
                oos.writeUTF("response: " + request);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Server().listen(8080);
    }
}
