package com.nisith.firebasenotification.Service;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nisith.firebasenotification.Common;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("ABCD","Service= "+token);
        super.onNewToken(token);
        Common.currentToken = token;
        updateFCMToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = "";
        String body = "";
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
//            remoteMessage.getNotification().getData();

        Log.d("ABCD","Title: "+title);
        Log.d("ABCD","Body: "+body);
        Log.d("ABCD","Name: "+remoteMessage.getData().get("name"));
        Log.d("ABCD","Age: "+remoteMessage.getData().get("age"));
    }

    private void updateFCMToken(String token){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
            Map<String, Object> map = new HashMap<>();
            map.put("device_token", token);
            databaseReference.updateChildren(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MyFirebaseMessagingService.this, "FCM Token Update", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
