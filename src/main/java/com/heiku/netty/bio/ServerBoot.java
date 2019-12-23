package com.heiku.netty.bio;

/**
 * @Author: Heiku
 * @Date: 2019/12/22
 */
public class ServerBoot {
    private static final int PORT = 8000;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }
}
