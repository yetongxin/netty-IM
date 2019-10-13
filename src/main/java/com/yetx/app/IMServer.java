package com.yetx.app;

import com.yetx.handler.FirstServerReadHandler;
import com.yetx.handler.PacketDecoderHandler;
import com.yetx.handler.PacketEncoderHandler;
import com.yetx.handler.Spliter;
import com.yetx.handler.server.AuthServerHandler;
import com.yetx.handler.server.LoginServerHandler;
import com.yetx.handler.server.MessageServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class IMServer {

    public static void main(String[] args) {
        // boss表示监听端口，accept 新连接的线程组，worker表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup woker = new NioEventLoopGroup();

        // 引导类 ServerBootstrap，这个类将引导我们进行服务端的启动工作。
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 1.线程模型 2.IO模型 3.连接读写处理逻辑
        serverBootstrap
                .group(boss,woker)
                .channel(NioServerSocketChannel.class) // 这里是一个NioServerSocketChannel, 对应BIO的ServerSocket
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 这里是一个NioSocketChannel,对应BIO的Socket
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        System.out.println("server initChannel...");
                        nioSocketChannel.pipeline().addLast(new Spliter());
                        nioSocketChannel.pipeline().addLast(new PacketDecoderHandler());
                        nioSocketChannel.pipeline().addLast(new LoginServerHandler());
                        nioSocketChannel.pipeline().addLast(new AuthServerHandler());
                        nioSocketChannel.pipeline().addLast(new MessageServerHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoderHandler());

                    }
                })
                .bind(8000);

        // childHandler()用于指定处理新连接数据的读写处理逻辑，handler()用于指定在服务端启动过程中的一些逻辑
        // attr()方法可以给服务端的 channel，也就是NioServerSocketChannel指定一些自定义属性，然后我们可以通过channel.attr()取出这个属性
        // childAttr()方法可以给每一条连接指定自定义属性，然后后续我们可以通过channel.attr()取出该属性。
    }
}
