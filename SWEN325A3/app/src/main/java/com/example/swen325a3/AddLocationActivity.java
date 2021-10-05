package com.example.swen325a3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        getSupportActionBar().hide();

    }
}