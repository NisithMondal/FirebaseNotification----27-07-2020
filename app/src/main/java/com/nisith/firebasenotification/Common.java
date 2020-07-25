package com.nisith.firebasenotification;

import com.nisith.firebasenotification.Remote.APIService;
import com.nisith.firebasenotification.Remote.RetrofitClient;

public class Common {
    public static String currentToken = "";
    public static String baseUrl = "https://fcm.googleapis.com/";
    public static APIService getFCMClient(){
        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }
}
