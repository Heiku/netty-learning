package com.heiku.netty.example.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * pass the data between channels
 *
 * truncate(int size):      截取 channel 中的前 size 字节，后面的长度将被删除
 * force():     将通道中里尚未写入磁盘的数据强制写道磁盘上，默认会缓存在内存中，等到操作系统的调度落盘。
 *
 * @Author: Heiku
 * @Date: 2020/3/24
 */
public class TransferFromChannel {
    public static void main(String[] args) {
        try {
            RandomAccessFile from = new RandomAccessFile("C:\\windows-version.txt", "r");
            FileChannel fromChannel = from.getChannel();

            RandomAccessFile to = new RandomAccessFile("C:\\Users\\DELL\\Desktop\\to.txt", "rw");
            FileChannel toChannel = to.getChannel();

            long position = 0;
            long count = fromChannel.size();

            toChannel.transferFrom(fromChannel, position, count);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
