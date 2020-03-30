package com.heiku.netty.example.demo.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @Author: Heiku
 * @Date: 2020/3/25
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 100L;
        System.out.println(new Date(currentTimeMillis));

        m.clear();
        m.writeInt(1);
        ctx.writeAndFlush(m);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
