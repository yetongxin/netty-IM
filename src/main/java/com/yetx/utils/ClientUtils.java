package com.yetx.utils;

import com.yetx.enums.Attributes;
import com.yetx.packet.MessageRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.Attribute;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientUtils {


    private static int MAX_RETRY = 3;


    public static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("connect to server success");
            } else if (retry == 0) {
                System.err.println("seems can not connect to server");
            } else {
                // 指数退避的方式，比如每隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接
                int order = (MAX_RETRY - retry) + 1;
                int delay = 2 << order;
                System.err.println(new Date() + "connect to server error, retrying " + order + " times ...");
                bootstrap.config().group().schedule(() -> {
                    connect(bootstrap, host, port, retry - 1);
                }, delay, TimeUnit.SECONDS);
            }
        });
    }

    public static void IMconnect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host, port).addListener(future -> {
            // 获取channel
            Channel channel = ((ChannelFuture) future).channel();
            if (future.isSuccess()) {
                System.out.println(new Date() + "　connect to server success");
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("seems can not connect to server");
            } else {
                // 指数退避的方式，比如每隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接
                int order = (MAX_RETRY - retry) + 1;
                int delay = 2 << order;
                System.err.println(new Date() + "connect to server error, retrying " + order + " times ...");
                bootstrap.config().group().schedule(() -> {
                    connect(bootstrap, host, port, retry - 1);
                }, delay, TimeUnit.SECONDS);
            }
        });

    }
    // 发送消息
    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            System.out.println("start console thread");
            while (!Thread.interrupted()) {
//                if(LoginUtil.isLogin(channel)) {
                if(true) {
                    System.out.println("输入消息：");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMsg(line);
                    packet.setDate(new Date());


//                    ByteBuf byteBuf = PacketParser.encode();
                    channel.writeAndFlush(packet);
                }
            }
        }).start();
    }

}
