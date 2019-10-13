package com.yetx.utils;

import io.netty.bootstrap.Bootstrap;

import java.util.Date;
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
}
