package com.yetx.handler;

import com.yetx.packet.AbstractPacket;
import com.yetx.utils.PacketParser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 以前是自行通过channelHandlerContext.alloc()获取一个ByteBufAllocator，
 * 随后自行创建ByteBuf（ByteBuf byteBuf = byteBufAllocator.ioBuffer();）后进行Encode，再返回该ByteBuf，
 *
 * 现在更改为使用Netty 提供的一个特殊的 channelHandler 来专门处理编码逻辑，我们不需要每一次将响应写到对端的时候调用一次编码逻辑进行编码，
 * 也不需要自行创建 ByteBuf，这个类叫做 MessageToByteEncoder，从字面意思也可以看出，它的功能就是将对象转换到二进制数据。
 */
public class PacketEncoderHandler extends MessageToByteEncoder<AbstractPacket> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacket packet, ByteBuf byteBuf) throws Exception {
        PacketParser.encode(byteBuf, packet);
    }
}
