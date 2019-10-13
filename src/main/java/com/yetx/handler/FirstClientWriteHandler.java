package com.yetx.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInboundInvoker;

import java.nio.charset.Charset;

// Test class
public class FirstClientWriteHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client write out data");
        ByteBuf byteBuf = getByteBuf(ctx,"Hello world!!!");
        ctx.channel().writeAndFlush(byteBuf);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("clident read data: " + ((ByteBuf)msg).toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg){
        byte[] bytes = msg.getBytes();

        // ctx.alloc() 获取到一个 ByteBuf 的内存管理器，作用就是分配一个ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();

        byteBuf.writeBytes(bytes);
        // 返回netty所需要的数据格式
        return byteBuf;
    }
}
