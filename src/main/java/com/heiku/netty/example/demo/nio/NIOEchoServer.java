package com.heiku.netty.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Nio Server
 *
 * @Author: Heiku
 * @Date: 2020/3/23
 */
public class NIOEchoServer {
    public static int DEFAULT_PORT = 8081;

    public static void main(String[] args) {
        int port;

        try {
            port = Integer.parseInt(args[0]);
        }catch (RuntimeException e){
            port = DEFAULT_PORT;
        }

        ServerSocketChannel serverSocketChannel;
        Selector selector;
        try {
            // open return ServerSocketChannelImpl instance
            serverSocketChannel = ServerSocketChannel.open();

            // config, configureBlocking(false) means nio
            InetSocketAddress address = new InetSocketAddress(port);
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);

            // same with serverSocketChannel, return a SelectorProviderImpl(WindowsSelectorImpl)
            selector = Selector.open();

            // register serverSocketChannel on selector, to deal with the accept event
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("NIOEchoServer start, port: " + port);
        } catch (IOException e){
            e.printStackTrace();
            return;
        }

        // use main thread to get accept socket
        while (true){
            try {
                // select a set of channel who are ready
                selector.select();
            }catch (IOException e){
                e.printStackTrace();
            }
            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    // acceptable
                    if (key.isAcceptable()){
                        // get the registered channel on above, accept the socketChannel from client
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverChannel.accept();

                        // nio
                        socketChannel.configureBlocking(false);

                        // client register on selector, start to read/write data
                        SelectionKey clientKey = socketChannel.register(selector,
                                SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                        // allocate byteBuffer for channel buffer
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        clientKey.attach(buffer);
                    }

                    // readable
                    if (key.isReadable()){
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        client.read(output);

                        System.out.println(client.getRemoteAddress() + " -> NIOEchoServer: " + output.toString());

                        key.interestOps(SelectionKey.OP_WRITE);
                    }

                    // writable
                    if (key.isWritable()){
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        client.write(output);

                        System.out.println("NIOEchoServer -> " + client.getRemoteAddress() + " : " + output.toString());

                        output.compact();
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }catch (IOException e){
                    key.cancel();

                    try {
                        key.channel().close();
                    }catch (IOException ex){
                        System.out.println("NIOEchoServer exception!" + ex.getMessage());
                    }
                }
            }
        }
    }
}
