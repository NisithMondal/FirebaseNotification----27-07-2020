package com.nisith.firebasenotification.Retrofit.Model;

public class Notification {
    private String title;
    private String body;
    public String click_action;
    public Notification(){

    }

    public Notification(String title, String body,String click_action) {
        this.title = title;
        this.body = body;
        this.click_action = click_action;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getClick_action() {
        return click_action;
    }
}
