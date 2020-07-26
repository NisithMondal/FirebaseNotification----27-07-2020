package com.nisith.firebasenotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationDataActivity extends AppCompatActivity {

    static final String TAG = "ABCD";
    private DatabaseReference databaseReference;
    private TextView resultTextView;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_data);
        resultTextView = findViewById(R.id.result_text_view);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            String age = intent.getStringExtra("age");
            uid = intent.getStringExtra("user_id");
//            Log.d("ABCD","uid== "+uid);
            retreaiveDataFromFirebase();
        }
    }

    private void retreaiveDataFromFirebase(){
        databaseReference.child(uid).child("name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String email = snapshot.getValue().toString();
//                        Log.d("ABCD","value== "+email);
                        String result = "";
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            result = result + dataSnapshot.getValue().toString() + "\n";
//                        }
                        resultTextView.setText(email);
//                        Log.d("ABCD","value==== "+result);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}