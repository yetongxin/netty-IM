package com.yetx.utils;

import com.yetx.enums.Command;
import com.yetx.enums.SerializerAlgo;
import com.yetx.packet.*;
import com.yetx.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 负责将数据包编码于解码为如下形式：
 * magicNumber(4 bytes) version(1) serializerAlgo(1) command(1) dataLength(4) data(n)
 */
public class PacketParser {

    public static final int magicNumber = 0x12345678;

    /**
     * // 更改前的定义
     * public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
     *     // 1. 创建 ByteBuf 对象
     *     ByteBuf byteBuf = byteBufAllocator.ioBuffer();
     *     // 2. 序列化 java 对象
     *
     *     // 3. 实际编码过程
     *
     *     return byteBuf;
     * }
     */
    @Deprecated
    public static ByteBuf encodeOld(ByteBufAllocator allocator, AbstractPacket packet){

        ByteBuf byteBuf = allocator.ioBuffer();

        byte[] bytes = Serializer.DEFAULT.encode(packet);


        // magicNumber(4 bytes) version(1) serializerAlgo(1) command(1) dataLength(4) data(n)
        byteBuf.writeInt(magicNumber);
        byteBuf.writeByte(AbstractPacket.version);
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
    /**
     * 对Packet进行序列化，然后包装成自定义的传输协议格式
     * @param byteBuf
     * @param packet 需要序列化的数据包
     * @return netty数据传输单元ByteBuf
     */
    public static void encode(ByteBuf byteBuf, AbstractPacket packet){

//        ByteBuf byteBuf = byteBufAllocator.ioBuffer();

        byte[] bytes = Serializer.DEFAULT.encode(packet);


        // magicNumber(4 bytes) version(1) serializerAlgo(1) command(1) dataLength(4) data(n)
        byteBuf.writeInt(magicNumber);
        byteBuf.writeByte(AbstractPacket.version);
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

    }

    /**
     * 将接收到的数据包解码为Packet
     * @param byteBuf
     * @return
     */
    public static AbstractPacket decode(ByteBuf byteBuf){

        byteBuf.skipBytes(4);// magicNumber
        byteBuf.skipBytes(1);// version
        byte algo = byteBuf.readByte();

        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Serializer serializer = getSerialzer(algo);
        Class<? extends AbstractPacket> requestType = getCommand(command);

        if(serializer != null && requestType != null) {
            return serializer.decode(requestType, bytes);
        }
        return null;
    }

    /**
     * 根据数据包的algo字节获取对应的序列化算法
     * @param algo
     * @return
     */
    private static Serializer getSerialzer(byte algo) {
        if(SerializerAlgo.JSON.algo == algo){
            return Serializer.DEFAULT;
        } else {
            return Serializer.DEFAULT;
        }
    }

    /**
     * 每加一个指令，都需要在这里进行配置
     * 获取数据包类型，用于反序列化
     * @param command
     * @return
     */
    private static Class<? extends AbstractPacket> getCommand(byte command) {
        if(command == Command.LOGIN_REQUEST.command) {
            return LoginRequestPacket.class;
        } else if(command == Command.LOGIN_RESPONE.command) {
            return LoginResponsePacket.class;
        } else if(command == Command.MESSAGE_REQUEST.command) {
            return MessageRequestPacket.class;
        } else if(command == Command.MESSAGE_RESPONSE.command) {
            return MessageResponsePacket.class;
        } else {
            return null;
        }
    }
}
