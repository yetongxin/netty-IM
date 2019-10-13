package com.yetx.enums;

public enum Command {
    LOGIN_REQUEST(1),

    LOGIN_RESPONE(101),

    MESSAGE_REQUEST(2),
    MESSAGE_RESPONSE(102),

    ;



    Command(Byte command) {
        this.command = command;
    }

    public Byte command;

    Command(int i) {
        this.command = (byte)i;
    }

    public Byte getCommand() {
        return command;
    }
}
