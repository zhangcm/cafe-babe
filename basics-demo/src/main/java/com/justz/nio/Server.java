package com.justz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhangcm
 * @since 1.0, 2018/8/16 上午11:25
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
            int count = selector.select(3000);
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
//                            selectionKey.attach(str);
                            channel.register(selectionKey.selector(), SelectionKey.OP_WRITE, str);
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
                        channel.register(selectionKey.selector(), SelectionKey.OP_READ);
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
