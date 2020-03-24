package com.heiku.netty.example.demo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Aio Server
 *
 * @Author: Heiku
 * @Date: 2020/3/24
 */
public class AsyncEchoServer {
    public static int DEFAULT_PORT = 8081;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        AsynchronousServerSocketChannel serverSocketChannel;
        try {
            // get AsynchronousSocketChannel instance
            serverSocketChannel = AsynchronousServerSocketChannel.open();

            InetSocketAddress address = new InetSocketAddress(port);
            serverSocketChannel.bind(address);

            // set option
            serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

            System.out.println("AsynchronousEchoServer start, port: " + port);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        while (true){
            Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
            AsynchronousSocketChannel channel = null;
            try {
                channel = future.get();
            }catch (InterruptedException | ExecutionException e){
                System.out.println("AsynchronousEchoServer exception: " + e.getMessage());
            }
            System.out.println("AsynchronousEchoServer start receive client connect: " + channel);

            // allocate buffer
            ByteBuffer buffer = ByteBuffer.allocate(100);
            try {
                while (channel.read(buffer).get() != -1){

                    // write back
                    buffer.flip();
                    channel.write(buffer).get();
                    System.out.println("AsynchronousEchoServer -> " + channel.getRemoteAddress() + " : " + buffer.toString());

                    if (buffer.hasRemaining()){
                        buffer.compact();
                    }else {
                        buffer.clear();
                    }
                }
                channel.close();
            }catch (InterruptedException | ExecutionException | IOException e){
                System.out.println("AsynchronousEchoServer exception: " + e.getMessage());
            }
        }
    }
}
