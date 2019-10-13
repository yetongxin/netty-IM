package com.yetx.packet;

import com.yetx.enums.Command;

import java.util.Date;

public class MessageResponsePacket extends AbstractPacket {

    private String msg;

    private Date date;

    public String getMsg() {
        return msg;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE.command;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
