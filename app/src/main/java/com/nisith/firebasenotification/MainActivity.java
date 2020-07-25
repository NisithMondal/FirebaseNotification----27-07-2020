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
import com.nisith.firebasenotification.Model.Data;
import com.nisith.firebasenotification.Model.MyResponse;
import com.nisith.firebasenotification.Model.Notification;
import com.nisith.firebasenotification.Model.Sender;
import com.nisith.firebasenotification.Remote.APIService;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private EditText titleET, bodyET;
    private Button sendButton;

    private APIService mService;
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
        mService = Common.getFCMClient();

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
                Data data = new Data("Asish","22");
                Sender sender = new Sender(otherDeviceToken,notification,data);
                mService.sendNotification(sender)
                        .enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                if (response.body().success == 1){
                                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }



    private void fun(){

    }


    private void generateFCMToken(){
      FirebaseInstanceId.getInstance().getInstanceId()
              .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                  @Override
                  public void onComplete(@NonNull Task<InstanceIdResult> task) {
                      if (task.isSuccessful()){
                          String token = task.getResult().getToken();
                          Common.currentToken = token;
                          saveFCMToken(token);
                      }
                  }
              });

    }



    private void saveFCMToken(String token){
        Map<String,Object> map = new HashMap<>();
        map.put("device_token", token);
        databaseReference.updateChildren(map);
    }
}