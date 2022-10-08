package com.example.weatherapiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button getCityId, getWeatherById, getWeatherByName;
    private EditText entryField;
    private RecyclerView weatherReportsRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCityId = findViewById(R.id.btn_getCityId);
        getWeatherById = findViewById(R.id.btn_getWeatherById);
        getWeatherByName = findViewById(R.id.btn_getWeatherByName);
        entryField = findViewById(R.id.entryField);
        weatherReportsRecView = findViewById(R.id.weatherReportsRecView);
        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        getCityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherDataService.getCityId(entryField.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String cityId) {
                        Toast.makeText(MainActivity.this, "Returned Ctiy ID:" + cityId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        getWeatherById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getWeatherForecastByCityId(entryField.getText().toString(), new WeatherDataService.ForecastByIdResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayList<WeatherReportModel> weatherReports) {
                        WeatherRecViewAdapter adapter = new WeatherRecViewAdapter();
                        weatherReportsRecView.setAdapter(adapter);
                        weatherReportsRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        adapter.setWeatherReport(weatherReports);
                    }
                });
            }
        });

        getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getWeatherForecastByCityName(entryField.getText().toString(), new WeatherDataService.GetCityForecastByNameCallBack() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayList<WeatherReportModel> weatherReports) {
                        WeatherRecViewAdapter adapter = new WeatherRecViewAdapter();
                        weatherReportsRecView.setAdapter(adapter);
                        weatherReportsRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        adapter.setWeatherReport(weatherReports);
                    }
                });
            }
        });
    }
}