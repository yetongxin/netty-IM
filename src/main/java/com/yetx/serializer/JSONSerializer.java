package com.yetx.serializer;

import com.alibaba.fastjson.JSON;
import com.yetx.enums.SerializerAlgo;

public class JSONSerializer implements Serializer {


    @Override
    public byte getSerializerAlgrithm() {
        return SerializerAlgo.JSON.getAlgo();
    }

    @Override
    public byte[] encode(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T decode(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
