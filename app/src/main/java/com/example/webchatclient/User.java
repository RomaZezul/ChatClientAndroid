package com.example.webchatclient;

public class User {
    private String nick;
    private Boolean online;

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getNick() {
        return nick;
    }

    public Boolean getOnline() {
        return online;
    }

    @Override
    public String toString() {
        return boolToStr(online) + nick ;
    }
    private String boolToStr(Boolean b){
        return b ? "      CONNECT  ->  " : "DISCONNECT  ->  ";
    }
}
