package com.example.travelmantics;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Users extends AppCompatActivity {
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        RecyclerView mechanicReclerview = findViewById(R.id.travel_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mechanicReclerview.setLayoutManager(linearLayoutManager);
        final TravelAdapter travelAdapter = new TravelAdapter();
        mechanicReclerview.setAdapter(travelAdapter);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef= storage.getReference();
        mDatabase.child("Destinations").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot dataSnap:dataSnapshot.getChildren()) {
                    final Travel travel = dataSnap.getValue(Travel.class);
                    Log.e("user", "The Travel image is " + travel.getImage());
                    StorageReference ref = storageRef.child("images/"+ travel.getImage());
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.e("sam" , "The uri path is " + uri.toString());
                            travel.setImage(uri.toString());
                            travelAdapter.addTravel(travel);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("sam" ,"The donwload failed expeption is " + e.getMessage());
                        }
                    });
                }

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
