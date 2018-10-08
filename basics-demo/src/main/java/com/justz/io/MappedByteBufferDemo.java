package com.justz.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferDemo {

    public static void readFile() throws IOException {
        String filePath = FileCopy.class.getClassLoader().getResource("flatmap.txt").getFile();
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel fc = fis.getChannel();
        byte[] ds = new byte[50];
        // FileInputStream只能读不能写
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 30, 50).load();
        mbb.get(ds);
        String content = new String(ds);
        System.out.println(content);
    }

    public static void main(String[] args) throws IOException {
        readFile();
    }
}
