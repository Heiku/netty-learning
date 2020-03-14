package com.heiku.netty.example.component.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: Heiku
 * @Date: 2020/3/13
 */
public class ByteBufDemo {
    public static void main(String[] args) {
        // create a buffer
        ByteBuf buf = Unpooled.buffer(10);

        System.out.println("---------- init ----------");
        printBuffer(buf);

        System.out.println("---------- write data ----------");
        String str = "ByteBuffer";
        buf.writeBytes(str.getBytes());
        printBuffer(buf);

        System.out.println("---------- read data ----------");
        while (buf.isReadable()){
            System.out.println(buf.readByte());
        }
        printBuffer(buf);

        System.out.println("---------- discard ReadBytes ---------");
        buf.readerIndex(8);
        buf.discardReadBytes();
        while (buf.isReadable()){
            System.out.println(buf.readByte());
        }
        printBuffer(buf);

        System.out.println("---------- clear ---------");
        buf.clear();
        printBuffer(buf);
    }

    private static void printBuffer(ByteBuf buf){
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());
        System.out.println("capacity: " + buf.capacity() + "\n");
    }

    public static void printBuf(byte[] arr, int offset, int len){
        System.out.println("array: " + arr);
        System.out.println("array -> string: " + new String(arr));
        System.out.println("offset: " + offset);
        System.out.println("len: " + len);
    }
}
