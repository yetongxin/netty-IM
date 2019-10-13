package com.yetx.app;

import com.yetx.handler.PacketDecoderHandler;
import com.yetx.handler.PacketEncoderHandler;
import com.yetx.handler.client.LoginClientHandler;
import com.yetx.handler.client.MessageClientHandler;
import com.yetx.utils.ClientUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class IMClient {
    private static int MAX_RETRY = 3;


    // 客户端相关的数据读写逻辑是通过 Bootstrap 的 handler() 方法指定
    public static void main(String[] args) {
        NioEventLoopGroup woker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(woker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("init client Channel ");
                        socketChannel.pipeline().addLast(new PacketDecoderHandler());
                        socketChannel.pipeline().addLast(new LoginClientHandler());
                        socketChannel.pipeline().addLast(new MessageClientHandler());
                        socketChannel.pipeline().addLast(new PacketEncoderHandler());
                    }

                });
        ClientUtils.IMconnect(bootstrap, "localhost", 8000, MAX_RETRY);

    }

}
