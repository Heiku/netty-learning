package com.heiku.netty.example.demo.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: Heiku
 * @Date: 2020/3/23
 */
public class NIOEchoClient {
    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("please fill the args [host] [name] in main arguments");
            System.exit(1);
        }

        String hostName = args[0];
        int port = Integer.parseInt(args[1]);

        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(hostName, port));
        }catch (IOException e){
            System.err.println("NIOEchoClient exception: " + e.getMessage());
            System.exit(1);
        }

        ByteBuffer writeBuffer = ByteBuffer.allocate(32);
        ByteBuffer readBuffer = ByteBuffer.allocate(32);
        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))){
            String userInput;
            while ((userInput = stdIn.readLine()) != null){
                writeBuffer.put(userInput.getBytes());
                writeBuffer.flip();
                // set position to buffer head:0
                writeBuffer.rewind();

                // write to channel
                socketChannel.write(writeBuffer);

                // read from channel
                socketChannel.read(readBuffer);

                // clear buffer
                writeBuffer.clear();
                readBuffer.clear();
                System.out.println("echo: " + userInput);
            }
        }catch (UnknownHostException e){
            System.err.println("unknown host: " + hostName);
            System.exit(1);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
