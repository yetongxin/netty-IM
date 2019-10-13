package com.yetx.utils;

import com.yetx.enums.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {

    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean isLogin(Channel channel) {
        Attribute<Boolean> attribute = channel.attr(Attributes.LOGIN);
        return attribute.get()!=null;
    }
}
