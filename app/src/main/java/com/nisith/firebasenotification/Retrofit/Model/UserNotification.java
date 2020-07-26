package com.nisith.firebasenotification.Retrofit.Model;
//UserNotification structure must bo like this
//{
//        "to" : <FCM Token>,
//        "notification" : {
//        "body" : "Message from Dave",
//        "title" : "1 New Message"
//        "image" : "YOUR IMAGE URL"
//        },
//        "data" : {      // Custom data can be sent
//        "name" : "Nisith",
//        "age" : "23"
//        }
//        }
public class UserNotification {
    private String to; //to which FCM device Token notification to be send
    private Notification notification;
    private Data data;
    public UserNotification(){

    }

    public UserNotification(String to, Notification notification, Data data) {
        this.to = to;
        this.notification = notification;
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public Notification getNotification() {
        return notification;
    }

    public Data getData() {
        return data;
    }
}
