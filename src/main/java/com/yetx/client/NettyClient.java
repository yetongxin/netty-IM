package com.yetx.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static int MAX_RETRY = 3;

    private static void connect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host,port).addListener(future -> {
           if(future.isSuccess()) {
               System.out.println("connect to server success");
           } else if (retry == 0) {
               System.err.println("seems can not connect to server");
           } else {

               // 指数退避的方式，比如每隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接
               int order = (MAX_RETRY - retry) + 1;
               int delay = 2 << order;
               System.err.println(new Date() + "connect to server error, retrying " + order + " times ...");
               bootstrap.config().group().schedule(()->{
                   connect(bootstrap, host, port, retry - 1);
               }, delay, TimeUnit.SECONDS);

           }
        });
    }

    // 客户端相关的数据读写逻辑是通过 Bootstrap 的 handler() 方法指定
    public static void main(String[] args) {
        NioEventLoopGroup woker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(woker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("init Channel function");
                    }

                });

//        bootstrap.connect("localhost",8000).addListener(future -> {
//                   if(future.isSuccess()){
//                       System.out.println("CONNET TO SERVER SUCCESS");
//                   } else {
//                       System.err.println("CONNECT TO SERVER ERROR");
//                   }
//                });
        connect(bootstrap, "localhost", 8000, MAX_RETRY);
    }

}
