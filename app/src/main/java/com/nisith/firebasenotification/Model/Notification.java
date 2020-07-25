package com.nisith.firebasenotification.Model;

public class Notification {
    public String title;
    public String body;
    public String click_action;

    public Notification(){

    }
    public Notification(String title, String body, String click_action) {
        this.title = title;
        this.body = body;
        this.click_action = click_action;

    }

    public String getClick_action() {
        return click_action;
    }

    public void setClick_action(String click_action) {
        this.click_action = click_action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
