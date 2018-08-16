package com.justz.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhangcm
 * @since 1.0, 2018/8/16 上午10:45
 */
public class FileChannelDemo {

    public void copy(String srcPath, String destPath) throws IOException {
        File srcFile = new File(this.getClass().getResource(srcPath).getFile());
        RandomAccessFile src = new RandomAccessFile(srcFile, "rw");
        FileChannel srcChannel = src.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int readBytes = srcChannel.read(byteBuffer);

        File destFile = new File(srcFile.getParent(), destPath);
        RandomAccessFile dest = new RandomAccessFile(destFile, "rw");
        FileChannel destChannel = dest.getChannel();

        while (readBytes != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                destChannel.write(byteBuffer);
            }
            byteBuffer.clear();
            readBytes = srcChannel.read(byteBuffer);
        }
    }

    public static void main(String[] args) throws IOException {
        FileChannelDemo demo = new FileChannelDemo();
        demo.copy("/flatmap.txt", "flatmap_copy.txt");
    }
}
