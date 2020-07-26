package com.nisith.firebasenotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nisith.firebasenotification.Retrofit.Model.Data;
import com.nisith.firebasenotification.Retrofit.Model.Notification;
import com.nisith.firebasenotification.Retrofit.RetrofitServerRequest;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private EditText titleET, bodyET;
    private Button sendButton;
    private String deviceToken;

    private String otherDeviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleET = findViewById(R.id.title);
        bodyET = findViewById(R.id.body);
        sendButton = findViewById(R.id.send);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        generateFCMToken();

////////////////////////////////////
        FirebaseDatabase.getInstance().getReference().child("users").child("YIqOS5oTfSMiCSjuKuDd9ir2Bex2")
                .child("device_token").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue().toString();
                otherDeviceToken = value;
                Log.d("ABCD","Other Token= "+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /////////////////////////////////////////

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleET.getText().toString();
                String body = bodyET.getText().toString();
                String action = "com.nisith.firebasenotification.NISITH_MONDAL";
                Notification notification = new Notification(title,body,action);
//                Log.d("ABCDE",Common.currentToken);
                Data data = new Data("nisith","23",firebaseUser.getUid());
                Log.d("abcd","iddddd= "+firebaseUser.getUid());
                RetrofitServerRequest serverRequest = new RetrofitServerRequest(getApplicationContext());
                serverRequest.sendNotification(otherDeviceToken, notification, data);

            }
        });

    }




    private void generateFCMToken(){
      FirebaseInstanceId.getInstance().getInstanceId()
              .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                  @Override
                  public void onComplete(@NonNull Task<InstanceIdResult> task) {
                      if (task.isSuccessful()){
                          String token = task.getResult().getToken();
                          deviceToken = token;
                          saveFCMToken(token);
                      }
                  }
              });

    }



    private void saveFCMToken(String token){
        Map<String,Object> map = new HashMap<>();
        map.put("device_token", token);
        map.put("name",firebaseUser.getEmail());
        databaseReference.updateChildren(map);
    }
}