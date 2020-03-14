package com.heiku.netty.example.component.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: Heiku
 * @Date: 2020/3/14
 */
public class ByteBufHeapBufferDemo {
    public static void main(String[] args) {

        // default allocate from heap
        ByteBuf buf = Unpooled.buffer();
        String s = "Netty";
        buf.writeBytes(s.getBytes());

        if(buf.hasArray()){
            byte[] array = buf.array();

            int offset = buf.readerIndex() + buf.arrayOffset();
            System.out.println("readIndex: " + buf.readerIndex());      // 0
            System.out.println("arrayOffset: " + buf.arrayOffset());    // 0

            int len = buf.readableBytes();          // 5

            ByteBufDemo.printBuf(array, offset, len);
        }
    }
}
