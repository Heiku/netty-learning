package com.heiku.netty.bio;

import java.io.InputStream;
import java.net.Socket;

/**
 * @Author: Heiku
 * @Date: 2019/12/22
 */
public class ClientHandler {
    public static final int MAX_DATA_LEN = 1024;

    private final Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void start(){
        System.out.println("new client connect");

        new Thread(() -> {
            doStart();
        }).start();
    }

    private void doStart(){
        try {
            InputStream is = socket.getInputStream();
            while (true){
                byte[] data = new byte[MAX_DATA_LEN];
                int len;

                while ((len = is.read(data)) != -1){
                    String message = new String(data, 0, len);
                    System.out.println("client send message: " + message);

                    socket.getOutputStream().write(data);
                }
            }
        }catch (Exception e){
            System.out.println("server exception");
        }
    }
}
