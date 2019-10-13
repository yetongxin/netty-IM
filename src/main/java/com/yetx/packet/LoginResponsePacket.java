package com.yetx.packet;

import com.yetx.enums.Command;

public class LoginResponsePacket extends AbstractPacket {

    private boolean success;

    private String msg;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONE.command;
    }

    public LoginResponsePacket() {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginResponsePacket{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
