package com.yetx.handler.client;

import com.yetx.packet.AbstractPacket;
import com.yetx.packet.LoginRequestPacket;
import com.yetx.packet.LoginResponsePacket;
import com.yetx.utils.PacketParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;


public class LoginClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client start login...");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setPassword("123456");
        packet.setUserName("test_user1");

        ByteBuf byteBuf = PacketParser.encode(packet);
        //写数据的时候，我们通过 ctx.channel() 获取到当前连接（Netty对连接的抽象为 Channel），
        // 然后调用 writeAndFlush() 就能把二进制数据写到服务端
        ctx.channel().writeAndFlush(byteBuf);//
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;

        AbstractPacket packet = PacketParser.decode(byteBuf);
        if(packet instanceof LoginResponsePacket) {
            if(((LoginResponsePacket) packet).isSuccess()){
                System.out.println(new Date() + "登录成功" );
            } else {
                System.out.println(new Date() + "登录失败: " + ((LoginResponsePacket) packet).getMsg());
            }
        }
    }



}
