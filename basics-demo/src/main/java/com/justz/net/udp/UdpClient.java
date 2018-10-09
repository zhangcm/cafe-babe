package com.justz.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UdpClient {

    public void request(String request) throws IOException {
        DatagramSocket socket = new DatagramSocket(8082);

        byte[] arr = request.getBytes();
        // 需指定地址
        DatagramPacket requestPacket = new DatagramPacket(request.getBytes(), arr.length,
                new InetSocketAddress("127.0.0.1", 8081));
        socket.send(requestPacket);
        System.out.println("发送请求：" + request);

        DatagramPacket responsePacket = new DatagramPacket(new byte[1024], 1024);
        socket.receive(responsePacket);
        System.out.println("收到响应：" + new String(responsePacket.getData()));
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new UdpClient().request("Hello");
    }
}
