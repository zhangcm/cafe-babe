package com.justz.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class RandomAccessFileDemo {

    public static void operate() throws IOException {
        String filePath = FileCopy.class.getClassLoader().getResource("demo.txt").getFile();
        RandomAccessFile rcf = new RandomAccessFile(filePath, "rw");
        // 调用writeChar()、writeInt()等方法会往文件里写入字节信息，直接打开文件会显示乱码
        rcf.writeChar('a');
        rcf.writeInt(32);
        rcf.seek(0);
        System.out.println(rcf.readChar());
        System.out.println(rcf.readInt());
        rcf.close();
    }

    public static void operateString() throws IOException {
        String filePath = FileCopy.class.getClassLoader().getResource("demo.txt").getFile();
        RandomAccessFile rcf = new RandomAccessFile(filePath, "rw");
        rcf.write("123".getBytes());
        rcf.write("你好".getBytes());
        rcf.seek(0);
        // readLine的返回值默认ISO-8859-1编码，需转换
        System.out.println(new String(rcf.readLine().getBytes(Charset.forName("ISO-8859-1"))));
        rcf.close();
    }

    public static void main(String[] args) throws IOException {
//        operate();
        operateString();
    }

}
