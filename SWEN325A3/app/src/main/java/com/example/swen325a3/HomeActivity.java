package com.example.swen325a3;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {

    //fields
    private SensorManager sensorManager; //object to keep track of the sensors
    private ImageView compass; //compass image, the one we rotate
    private float DegreeStart = 0f;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    //buttons
    Button addLocationButton;
    Button manageLocationsButton;
    Button tutorialButton;
    Button aboutButton;
    Button compassButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.compass = (ImageView) findViewById(R.id.main_compass);

        setButtonListeners(this.addLocationButton, this.manageLocationsButton, this.compassButton, this.tutorialButton, this.aboutButton);



    }

    /**
     * Function to set the listeners for all of the buttons on this screen
     * @param addLocation button to go to the add location screen
     * @param manageLocations button to go to the manage locations screen
     * @param tutorial button to go to the tutorial screen
     * @param about button to go to the about screen
     */
    private void setButtonListeners(Button addLocation, Button manageLocations, Button compass, Button tutorial, Button about){
        addLocation = (Button) findViewById(R.id.b_add_location_from_home);
        addLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddLocationActivity.class);
                startActivity(intent);
            }
        });

        manageLocations = (Button) findViewById(R.id.b_manage_locations_from_home);
        manageLocations.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ManageLocationsActivity.class);
                startActivity(intent);
            }
        });

        compass = (Button) findViewById(R.id.b_plain_compass_from_home);
        compass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PlainCompassActivity.class);
                startActivity(intent);
            }
        });

        tutorial = (Button) findViewById(R.id.b_tutorial_from_home);
        tutorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        about = (Button) findViewById(R.id.b_about_from_home);
        about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Don't receive any more updates from the sensors
        this.sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get updates from the accelerometer and magnetometer at a constant rate.
        // To make batch operations more efficient and reduce power consumption,
        // provide support for delaying updates to the application.
        //
        // In this example, the sensor reporting delay is small enough such that
        // the application receives an update before the system checks the sensor
        // readings again.
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }

        updateOrientationAngles();


        // get angle around the z-axis rotated
        float degree = Math.round(this.orientationAngles[0] * (180/Math.PI));

        Log.i("azimuth", "inOSC: " + degree + ", ie, " + orientationAngles[0]);
        // rotation animation - reverse turn degree degrees
        RotateAnimation ra = new RotateAnimation(
                DegreeStart,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        // set the compass animation after the end of the reservation status
        ra.setFillAfter(true);

        // set how long the animation for the compass image will take place
        ra.setDuration(0);

        // Start animation of compass image
        this.compass.startAnimation(ra);
        DegreeStart = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.
    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "rotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        Log.i("azimuth", "inUOA: " + orientationAngles[0]);
        // "orientationAngles" now has up-to-date information.
    }


}

//Note that in constructing the basic compass and getting it to spin I have drawn inspiration from both
// https://www.codespeedy.com/simple-compass-code-with-android-studio/ and
// https://developer.android.com/guide/topics/sensors/sensors_position#sensors-pos-orient