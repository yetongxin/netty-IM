package com.yetx.handler.client;

import com.yetx.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageClientHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        receiveResponse(messageResponsePacket);
    }

    private void receiveResponse(MessageResponsePacket responsePacket) {
        System.out.println("从服务端返回的消息: ");
        System.out.println(responsePacket.getDate() +": "+responsePacket.getMsg());
    }
}
