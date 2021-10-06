package com.example.swen325a3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ManageLocationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_locations);
        getSupportActionBar().hide();

        Button homeButton = (Button) findViewById(R.id.b_home_from_manage_locations);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ManageLocationsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}