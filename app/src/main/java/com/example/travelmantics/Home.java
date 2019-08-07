package com.example.travelmantics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void admin(View view) {
        startActivity(new Intent(Home.this , Admin.class));
    }

    public void User(View view) {
        startActivity(new Intent(Home.this , Users.class));
    }
}
