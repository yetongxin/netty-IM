package com.yetx.handler.server;

import com.yetx.packet.AbstractPacket;
import com.yetx.packet.LoginRequestPacket;
import com.yetx.packet.LoginResponsePacket;
import com.yetx.utils.PacketParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read Data");
        // 解码
        ByteBuf requestByteBuf = (ByteBuf) msg;
        AbstractPacket packet = PacketParser.decode(requestByteBuf);

        // 校验
        if(packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            System.out.println("receive packet:"+loginRequestPacket.toString());

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if(valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                loginResponsePacket.setMsg("登录成功");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setMsg("账号密码错误");
            }

            // 发送数据
            ByteBuf responseByteBuf = PacketParser.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }


    }

    private boolean valid(LoginRequestPacket packet) {
        if(packet.getUserName()!=null && packet.getUserName().length()!=0 && packet.getPassword().equals("123456")) {
            return true;
        }
        return false;
    }
}
