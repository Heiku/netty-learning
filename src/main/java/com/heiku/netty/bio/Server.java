package com.heiku.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Heiku
 * @Date: 2019/12/22
 */
public class Server {
    private ServerSocket serverSocket;

    public Server(int port){
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("server start  successfully, port: " + port);
        } catch (IOException e) {
            System.out.println("server start failed");
        }
    }

    public void start(){
        new Thread(() -> {
            doStart();
        }).start();
    }

    private void doStart(){
        while (true){
            try {
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
            }catch (Exception e){
                System.out.println("server exception");
            }
        }
    }
}
