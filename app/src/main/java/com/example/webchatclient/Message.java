package com.example.webchatclient;

import java.io.Serializable;

public class Message implements Serializable {
    String nick, message;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return nick + "\n\r\t\t" + message;
    }

}