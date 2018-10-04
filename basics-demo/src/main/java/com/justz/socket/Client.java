package com.justz.socket;

import java.io.*;
import java.net.Socket;

public class Client {

    public void send(String message) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8080);

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeUTF((message));
            os.flush();

            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            String response = is.readUTF();
            System.out.println("response: " + response);
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
