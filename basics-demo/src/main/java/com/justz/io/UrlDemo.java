package com.justz.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class UrlDemo {

    public String readContent(URL url) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return reader.lines().collect(Collectors.joining("\r\n"));
        }
    }

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.baidu.com");
        System.out.println(new UrlDemo().readContent(url));
    }
}
