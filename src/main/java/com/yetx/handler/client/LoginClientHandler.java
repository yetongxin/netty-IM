package com.yetx.handler.client;

import com.yetx.packet.AbstractPacket;
import com.yetx.packet.LoginRequestPacket;
import com.yetx.packet.LoginResponsePacket;
import com.yetx.utils.LoginUtil;
import com.yetx.utils.PacketParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;


public class LoginClientHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    /**
     * 启动就开始login
     * @param ctx
     * @throws Exception
     */

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client start login...");

        LoginRequestPacket packet = new LoginRequestPacket();
//        packet.setUserId(UUID.randomUUID().toString());
        packet.setPassword("123456");
        packet.setUserName("test_user1");

//        ctx.writeAndFlush(packet);
//        ByteBuf byteBuf = PacketParser.encode(packet);
//        写数据的时候，我们通过 ctx.channel() 获取到当前连接（Netty对连接的抽象为 Channel），
//        然后调用 writeAndFlush() 就能把二进制数据写到服务端
//        另外可以先write(),再一次性flush()
        ctx.channel().writeAndFlush(packet);
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf)msg;
//
//        AbstractPacket packet = PacketParser.decode(byteBuf);
//        if(packet instanceof LoginResponsePacket) {
//            if(((LoginResponsePacket) packet).isSuccess()){
//                System.out.println(new Date() + "登录成功" );
//            } else {
//                System.out.println(new Date() + "登录失败: " + ((LoginResponsePacket) packet).getMsg());
//            }
//        }
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        handleLoginResponse(channelHandlerContext, loginResponsePacket);
    }

    private void handleLoginResponse(ChannelHandlerContext channelHandlerContext, LoginResponsePacket responsePacket) {
        if(responsePacket.isSuccess()){
            System.out.println(new Date() + "登录成功" );
            LoginUtil.markAsLogin(channelHandlerContext.channel());
        } else {
            System.out.println(new Date() + "登录失败: " + responsePacket.getMsg());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接已经断开...");
    }
}
