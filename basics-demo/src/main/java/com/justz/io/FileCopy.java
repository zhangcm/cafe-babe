package com.justz.io;

import java.io.*;

public class FileCopy {

    public void copyWithInputStream(String src, String dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] arr = new byte[1024];
            int count;
            while ((count = fis.read(arr)) != -1) {
                // 由于复用的arr，如果直接调用fos.write(arr)有可能导致部分内容重复
                fos.write(arr, 0, count);
            }
        }
    }

    public void copyWithReader(String src, String dest) throws IOException {
        FileReader reader = new FileReader(src);
        FileWriter writer = new FileWriter(dest);
        char[] arr = new char[1024];
        int count;
        while ((count = reader.read(arr)) != -1) {
            // 由于复用的arr，如果直接调用fos.write(arr)有可能导致部分内容重复
            writer.write(arr, 0, count);
        }
        reader.close();
        writer.close();
    }

    public void copyWithBufferedReader(String src, String dest) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(src));
        BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }
        reader.close();
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        String src = FileCopy.class.getClassLoader().getResource("flatmap.txt").getFile();
        String dest = src.substring(0, src.lastIndexOf("/")) + "/flatmap_copy.txt";
        new FileCopy().copyWithBufferedReader(src, dest);
    }
}
