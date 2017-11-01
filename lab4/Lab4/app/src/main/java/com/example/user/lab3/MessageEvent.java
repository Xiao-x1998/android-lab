package com.example.user.lab3;


public class MessageEvent {
    private int id;

    private Boolean flag;

    private int times;

    public MessageEvent(int id,int times,Boolean flag) {
        this.id = id;
        this.times = times;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public int getTimes() {
        return times;
    }

    public Boolean getFlag() {
        return flag;
    }

}
