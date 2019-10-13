package com.yetx.enums;

public enum Command {
    LOGIN_REQUEST((byte)1),

    LOGIN_RESPONE((byte)101);


    Command(Byte command) {
        this.command = command;
    }

    public Byte command;

    public Byte getCommand() {
        return command;
    }

    public void setCommand(Byte command) {
        this.command = command;
    }
}
