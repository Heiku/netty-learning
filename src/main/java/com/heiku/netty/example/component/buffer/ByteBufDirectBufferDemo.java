package com.heiku.netty.example.component.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: Heiku
 * @Date: 2020/3/13
 */
public class ByteBufDirectBufferDemo {
    public static void main(String[] args) {

        // create buffer
        ByteBuf buf = Unpooled.directBuffer(20);
        String str = "ByteBuf";
        buf.writeBytes(str.getBytes());

        // backing array
        if (!buf.hasArray()){
            int offset = buf.readerIndex();     // 0
            int len = buf.readableBytes();      // 7

            byte[] arr = new byte[len];
            buf.getBytes(offset, arr);      // buf[] -> (byte)str

            ByteBufDemo.printBuf(arr, offset, len);
        }
    }


}
