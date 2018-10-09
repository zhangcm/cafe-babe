package com.justz.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferDemo {

    public static void readAndWrite() throws IOException {
        String filePath = FileCopy.class.getClassLoader().getResource("demo.txt").getFile();
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        FileChannel fc = file.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 30, fc.size() - 30);
        byte[] ds = new byte[50];
        // 索引会移动
        mbb.get(ds, 0, 50);
        System.out.println(new String(ds));
        // 原位置替换，会直接修改文件，索引会后移
        mbb.put("tings ".getBytes());
        mbb.get(ds, 0, 50);
        System.out.println(new String(ds));
    }

    public static void readFile() throws IOException {
        String filePath = FileCopy.class.getClassLoader().getResource("flatmap.txt").getFile();
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel fc = fis.getChannel();
        // FileInputStream只能读不能写, size不能超过剩余长度
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 1950, fc.size() - 1950);
        byte[] ds = new byte[mbb.capacity()];
        mbb.get(ds);
        String content = new String(ds);
        System.out.println(content);
    }

    public static void main(String[] args) throws IOException {
//        readFile();
        readAndWrite();
    }
}
