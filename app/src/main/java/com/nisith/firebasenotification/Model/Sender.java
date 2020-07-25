package com.nisith.firebasenotification.Model;

public class Sender {
    public String to;
    public Data data;
    public Notification notification;


    public Sender(){

    }
    public Sender( String to, Notification notification, Data data) {
        this.notification = notification;
        this.data = data;
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
