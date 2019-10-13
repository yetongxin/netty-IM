package com.yetx.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;


// Test class Unused
public class FirstServerReadHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("server receive msg: " + byteBuf.toString(Charset.forName("utf-8")));

        // 返回响应数据
        ByteBuf sendBuf = getByteBuf(ctx,"server have received msg, nice to meet you");
        ctx.channel().writeAndFlush(sendBuf);
    }

    private static ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg){
        byte[] bytes = msg.getBytes();
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return  byteBuf;
    }
}
