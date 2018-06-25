package com.justz.socket;

import java.io.*;
import java.net.Socket;

public class Client {

    public void send(String message) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            os.write((message + "\n").getBytes());
            os.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result);
        } catch (Exception e) {

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.send("Hello");
        client.send("Hi");
    }
}
