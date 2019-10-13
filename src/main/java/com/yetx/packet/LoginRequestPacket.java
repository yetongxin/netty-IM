package com.yetx.packet;

import com.yetx.enums.Command;


public class LoginRequestPacket extends AbstractPacket {

    private String userId;

    private String password;

    private String  userName;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST.getCommand();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "LoginRequestPacket{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
