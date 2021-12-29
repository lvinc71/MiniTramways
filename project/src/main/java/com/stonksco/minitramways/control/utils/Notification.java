package com.stonksco.minitramways.control.utils;

/**
 * Représente un message passé d'un "Listened" à un "Listener"
 */
public class Notification {

    private Object data;
    private String message;

    public Notification(String msg) {
        message = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

}
