package com.wgcloud.upan;

import org.springframework.context.ApplicationEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomEvent extends ApplicationEvent {
    private String time = new SimpleDateFormat("hh:mm:ss").format(new Date());
    private String msg;

    public CustomEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public CustomEvent(Object source) {
        super(source);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}