package com.nisith.firebasenotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NotificationDataActivity extends AppCompatActivity {

    static final String TAG = "ABCD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_data);
        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            String age = intent.getStringExtra("age");
            Log.d(TAG,"My Bhai Name is "+name);
            Log.d(TAG,"My Bhai Age is "+age);
            Log.d(TAG,"intent= "+intent);
        }else {
            Log.d(TAG,"intent= "+intent);
        }
    }
}