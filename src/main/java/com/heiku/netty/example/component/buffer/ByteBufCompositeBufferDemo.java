package com.heiku.netty.example.component.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: Heiku
 * @Date: 2020/3/14
 */
public class ByteBufCompositeBufferDemo {
    public static void main(String[] args) {
        // heap buffer
        ByteBuf heapBuf = Unpooled.buffer(10);
        String heapStr = "heap";
        heapBuf.writeBytes(heapStr.getBytes());

        // direct buffer
        ByteBuf directBuf = Unpooled.directBuffer(10);
        String directStr = "direct";
        directBuf.writeBytes(directStr.getBytes());

        // composite buffer
        CompositeByteBuf compositeBuf = Unpooled.compositeBuffer(20);
        compositeBuf.addComponents(heapBuf, directBuf);

        if (!compositeBuf.hasArray()){
            for (ByteBuf buf : compositeBuf) {
                int offset = buf.readerIndex();
                int len = buf.readableBytes();

                byte[] arr = new byte[len];
                buf.getBytes(offset, arr);

                ByteBufDemo.printBuf(arr, offset, len);
            }
        }

    }
}
