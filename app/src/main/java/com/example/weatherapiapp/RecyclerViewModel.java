package com.example.weatherapiapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecyclerViewModel extends AppCompatActivity {
    private TextView statename, date, min_temp, max_temp, the_temp, wind_speed, humidity, visibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_model);

        statename = findViewById(R.id.statenamevalue);
        date = findViewById(R.id.datevalue);
        min_temp = findViewById(R.id.mintempvalue);
        max_temp = findViewById(R.id.maxtempvalue);
        the_temp = findViewById(R.id.thetempvalue);
        wind_speed = findViewById(R.id.windspeedvalue);
        humidity = findViewById(R.id.humidityvalue);
        visibility = findViewById(R.id.visibilityvalue);


    }
}