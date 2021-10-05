package com.example.swen325a3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ManageLocationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_locations);
        getSupportActionBar().hide();

    }
}