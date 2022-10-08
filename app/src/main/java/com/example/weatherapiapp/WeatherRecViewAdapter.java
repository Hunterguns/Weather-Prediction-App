package com.example.weatherapiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherRecViewAdapter extends RecyclerView.Adapter<WeatherRecViewAdapter.ViewHolder> {

    ArrayList<WeatherReportModel> weatherReport = new ArrayList<>();

    public void setWeatherReport(ArrayList<WeatherReportModel> weatherReport) {
        this.weatherReport = weatherReport;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler_view_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.vh_statename.setText(weatherReport.get(position).getWeather_state_name());
        holder.vh_date.setText(weatherReport.get(position).getApplicable_date());
        holder.vh_max_temp.setText(Float.toString(weatherReport.get(position).getMax_temp()));
        holder.vh_min_temp.setText(Float.toString(weatherReport.get(position).getMin_temp()));
        holder.vh_the_temp.setText(Float.toString(weatherReport.get(position).getThe_temp()));
        holder.vh_wind_speed.setText(Float.toString((int) weatherReport.get(position).getWind_speed()));
        holder.vh_humidity.setText(Integer.toString(weatherReport.get(position).getHumidity()));
        holder.vh_visibility.setText(Float.toString((int) weatherReport.get(position).getVisibility()));
    }

    @Override
    public int getItemCount() {
        return weatherReport.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView vh_statename;
        private final TextView vh_date;
        private final TextView vh_min_temp;
        private final TextView vh_max_temp;
        private final TextView vh_the_temp;
        private final TextView vh_wind_speed;
        private final TextView vh_humidity;
        private final TextView vh_visibility;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vh_statename = itemView.findViewById(R.id.statenamevalue);
            vh_date = itemView.findViewById(R.id.datevalue);
            vh_min_temp = itemView.findViewById(R.id.mintempvalue);
            vh_max_temp = itemView.findViewById(R.id.maxtempvalue);
            vh_the_temp = itemView.findViewById(R.id.thetempvalue);
            vh_wind_speed = itemView.findViewById(R.id.windspeedvalue);
            vh_humidity = itemView.findViewById(R.id.humidityvalue);
            vh_visibility = itemView.findViewById(R.id.visibilityvalue);


        }
    }

}
