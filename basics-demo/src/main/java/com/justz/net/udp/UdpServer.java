package com.justz.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UdpServer {

    public void service() throws IOException {
        DatagramSocket socket = new DatagramSocket(8081);

        byte[] arr = new byte[1024];
        DatagramPacket requestPacket = new DatagramPacket(arr, 1024);
        socket.receive(requestPacket);
        String request = new String(requestPacket.getData());
        System.out.println("收到请求：" + new String(arr) );

        String response = "resp: " + request;
        DatagramPacket responsePacket = new DatagramPacket(response.getBytes(), 1024,
                new InetSocketAddress("127.0.0.1", 8082));
        socket.send(responsePacket);
        System.out.println("发送响应：" + response);

        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new UdpServer().service();
    }
}
