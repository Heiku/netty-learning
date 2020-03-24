package com.heiku.netty.example.demo.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * Aio Server
 *
 * @Author: Heiku
 * @Date: 2020/3/24
 */
public class AsyncEchoClient {
    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("please fill the args [host] [name] in main arguments");
            System.exit(1);
        }
        String hostName = args[0];
        int port = Integer.parseInt(args[1]);

        AsynchronousSocketChannel socketChannel = null;
        try {
            socketChannel = AsynchronousSocketChannel.open();
            socketChannel.connect(new InetSocketAddress(hostName, port));
        }catch (IOException e){
            System.err.println("AsynchronousEchoClient exception: " + e.getMessage());
            System.exit(1);
        }

        // allocate buffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(32);
        ByteBuffer readBuffer = ByteBuffer.allocate(32);

        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))){
            String input;
            while ((input = stdIn.readLine()) != null){
                writeBuffer.put(input.getBytes());

                // reset limit
                writeBuffer.flip();
                writeBuffer.rewind();

                // write to channel
                socketChannel.write(writeBuffer);

                // read from channel
                socketChannel.read(readBuffer);
                System.out.println("readBuffer: " + readBuffer.toString());

                // clear
                writeBuffer.clear();
            }
        }catch (UnknownHostException e){
            System.err.println("unknown host: " + hostName + " ,port: " + port);
            System.exit(1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
