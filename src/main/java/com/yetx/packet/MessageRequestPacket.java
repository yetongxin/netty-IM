package com.yetx.packet;

import com.yetx.enums.Command;

import java.util.Date;

public class MessageRequestPacket extends AbstractPacket{

    private String msg;

    private Date date;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST.command;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
