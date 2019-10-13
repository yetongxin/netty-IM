package com.yetx.enums;

public enum SerializerAlgo {
    JSON((byte)1);

    SerializerAlgo(byte algo) {
        this.algo = algo;
    }

    public final byte algo;

    public byte getAlgo() {
        return algo;
    }

}
