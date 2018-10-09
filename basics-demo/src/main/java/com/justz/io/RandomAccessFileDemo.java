package com.justz.io;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileDemo {

    public static void readFile() throws IOException {
        String filePath = FileCopy.class.getClassLoader().getResource("flatmap.txt").getFile();
        RandomAccessFile rcf = new RandomAccessFile(filePath, "rw");
        rcf.seek(4);

        System.out.println(rcf.readChar());
    }

    public static void main(String[] args) throws IOException {
        readFile();
    }

}
