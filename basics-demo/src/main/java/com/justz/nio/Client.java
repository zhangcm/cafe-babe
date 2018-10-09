package com.justz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author zhangcm
 */
public class Client {

    public void connect(int port) throws IOException {
        Selector selector = Selector.open();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(port));

        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            int selected = selector.select();
            if (selected == -1) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.finishConnect();
                    channel.register(selector, SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    System.out.print("请输入请求: ");
                    String request = scanner.next();

                    // 通过wrap直接构造的ByteBuffer可以直接写
                    ByteBuffer byteBuffer = ByteBuffer.wrap(request.getBytes());
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.write(byteBuffer);
                    System.out.println("发送请求: " + request);
                    key.interestOps(SelectionKey.OP_READ);
//                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int count = channel.read(byteBuffer);
                    while (count > 0) {
                        System.out.print("收到响应: ");
                        System.out.println(new String(byteBuffer.array(), 0, count));
                        byteBuffer.clear();
                        count = channel.read(byteBuffer);
                    }
                    key.interestOps(SelectionKey.OP_WRITE);
//                    channel.register(selector, SelectionKey.OP_WRITE);
                }
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect(8080);
    }
}
