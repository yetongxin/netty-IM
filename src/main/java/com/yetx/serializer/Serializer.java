package com.yetx.serializer;

/**
 * 负责序列化数据包Packet
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    byte getSerializerAlgrithm();

    byte[] encode(Object object);

    <T> T decode(Class<T> clazz, byte[] bytes);
}
