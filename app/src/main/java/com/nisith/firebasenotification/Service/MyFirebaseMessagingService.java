package com.nisith.firebasenotification.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nisith.firebasenotification.NotificationDataActivity;
import com.nisith.firebasenotification.R;
import com.nisith.firebasenotification.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("ABCD","Service= "+token);
        super.onNewToken(token);
        updateFCMToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage);
        }

    }

    private void showNotification(RemoteMessage remoteMessage){
       String title = remoteMessage.getNotification().getTitle();
       String body = remoteMessage.getNotification().getBody();
       String name = remoteMessage.getData().get("name");
       String age = remoteMessage.getData().get("age");
       String user_id = remoteMessage.getData().get("user_id");

       String channelId = "MyNotificationChannel";
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           NotificationChannel notificationChannel = new NotificationChannel(channelId,"MyNotify",
                   NotificationManager.IMPORTANCE_HIGH);
           NotificationManager notificationManager = getSystemService(NotificationManager.class);
           notificationManager.createNotificationChannel(notificationChannel);
       }

        Intent intent = new Intent(getApplicationContext(), NotificationDataActivity.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent.putExtra("name",name);
       intent.putExtra("age",age);
        intent.putExtra("user_id",user_id);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),100,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelId)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notification)
                .setDefaults(Notification.DEFAULT_ALL)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify((int) SystemClock.currentThreadTimeMillis(),builder.build());

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
