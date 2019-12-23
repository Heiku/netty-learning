package com.heiku.netty.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * @Author: Heiku
 * @Date: 2019/12/22
 */
public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(HOST, PORT);

        new Thread(() -> {
            System.out.println("client start successfully!");
            while (true){
                try {
                    String message = "hello world";
                    System.out.println("client send data: " + message);
                    socket.getOutputStream().write(message.getBytes());
                }catch (Exception e){
                    System.out.println("write data wrong");
                }
                sleep();
            }
        }).start();
    }

    private static void sleep(){
        try {
            Thread.sleep(SLEEP_TIME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
