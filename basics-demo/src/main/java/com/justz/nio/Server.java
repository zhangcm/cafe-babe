package com.justz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO服务端
 * 1. 一个SocketChannel对应一个SelectionKey。同一个SocketChannel触发了不同的事件所返回的SelectionKey是相同的
 * 2. 使用SocketChannel.register方法，如果未指定attachment，会导致之前的被清空。如果只是监听不同的事件，可以使用interestOps方法
 * 3. SelectionKey的interestOps()和attach()方法可以替代SocketChannel.register
 */
public class Server {

    public void bind(int port) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("listening " + port + "...");

        while (true) {
            int count = selector.select();
            if (count != -1) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        System.out.println("收到新连接: " + socketChannel);
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int readCounts = channel.read(byteBuffer);
                        if (readCounts > 0) {
                            String str = new String(byteBuffer.array(), 0, readCounts);
                            System.out.println("收到请求: " + str);
                            selectionKey.interestOps(SelectionKey.OP_WRITE);
                            selectionKey.attach(str);
//                            channel.register(selectionKey.selector(), SelectionKey.OP_WRITE, str);
                        }
                    } else if (selectionKey.isWritable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        String response = (String) selectionKey.attachment();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        byteBuffer.put(response.getBytes());
                        byteBuffer.flip();
                        channel.write(byteBuffer);
                        byteBuffer.clear();
                        System.out.println("发送响应: " + response);
                        selectionKey.interestOps(SelectionKey.OP_READ);
//                        channel.register(selectionKey.selector(), SelectionKey.OP_READ);
                    }
                    iterator.remove();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.bind(8080);

    }
}
