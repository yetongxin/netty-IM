package com.yetx.packet;

public abstract class AbstractPacket {

    public static Byte version = 1;

    public abstract Byte getCommand();
}
