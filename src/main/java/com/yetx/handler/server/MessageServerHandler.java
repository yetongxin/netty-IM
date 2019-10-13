package com.yetx.handler.server;

import com.yetx.packet.MessageRequestPacket;
import com.yetx.packet.MessageResponsePacket;
import com.yetx.utils.PacketParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageServerHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        MessageResponsePacket responsePacket = receiveMessage(messageRequestPacket);
        ctx.channel().writeAndFlush(responsePacket);
    }

    private MessageResponsePacket receiveMessage(MessageRequestPacket messageRequestPacket) {
        System.out.println("receive message : "+ messageRequestPacket.getMsg());
        MessageResponsePacket packet = new MessageResponsePacket();
        packet.setMsg(messageRequestPacket.getMsg());
        packet.setDate(new Date());
        return packet;
    }
}
