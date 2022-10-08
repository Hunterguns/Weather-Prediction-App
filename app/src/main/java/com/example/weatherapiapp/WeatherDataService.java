package com.example.weatherapiapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Handles retrieving data from the api
public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_WEATHER_BY_CITY_ID = "https://www.metaweather.com/api/location/";
    String cityId = "";
    private final Context mContext;

    public WeatherDataService(Context mContext) {
        this.mContext = mContext;
    }

    //Get the City ID using city name
    void getCityId(String cityName, VolleyResponseListener volleyResponseListener) {

        String url = QUERY_FOR_CITY_ID + cityName;      //url from where data is accessed

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {        //to get the Json Array JsonArrayRequest is made with responseListeners

            @Override
            public void onResponse(JSONArray response) {                                //What to do when we get Response from the API calling
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityId = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                volleyResponseListener.onResponse(cityId);
            }
        }, new Response.ErrorListener() {                                                           //ErrorListener
            @Override
            public void onErrorResponse(VolleyError error) {                                                                   //What to do when we get error from the API calling
                volleyResponseListener.onError("Something went wrong in implementing getCityID");
            }
        });

        MySingleton.getInstance(mContext).addToRequestQueue(arrayRequest);                                          //Initialising the MySingleton class's instance and calling addToRequestQueue to add the Request to the request queue

    }

    /**
     * Get the Forecast of City weather using City ID
     * @param cityId - ID of the city
     * @param forecastByIdResponseListener
     */
    public void getWeatherForecastByCityId(String cityId, ForecastByIdResponseListener forecastByIdResponseListener) {

        ArrayList<WeatherReportModel> weatherReports = new ArrayList<>();                                           //Arraylist to store weather reports of different days

        String url = QUERY_FOR_WEATHER_BY_CITY_ID + cityId;

        //get json object

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {          //JsonObjectRequest to retrieve JsonObject
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray consolidated_weather_array = response.getJSONArray("consolidated_weather");                                   //Retrieve array named as consolidated_weather from the JsonObject

                    for (int i = 0; i < consolidated_weather_array.length(); i++) {                                                               //Loop through the array to get all the days weather forecast available and abb to the arrayList
                        //get first item from the JSON array
                        WeatherReportModel day_weather_report = new WeatherReportModel();

                        JSONObject first_day_from_api = (JSONObject) consolidated_weather_array.get(i);

                        day_weather_report.setId(first_day_from_api.getInt("id"));                                                          //Get Different values from the API request response and assign it to values of the object
                        day_weather_report.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                        day_weather_report.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                        day_weather_report.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                        day_weather_report.setCreated(first_day_from_api.getString("created"));
                        day_weather_report.setApplicable_date(first_day_from_api.getString("applicable_date"));
                        day_weather_report.setMin_temp(first_day_from_api.getLong("min_temp"));
                        day_weather_report.setMax_temp(first_day_from_api.getLong("max_temp"));
                        day_weather_report.setThe_temp(first_day_from_api.getLong("the_temp"));
                        day_weather_report.setWind_speed(first_day_from_api.getLong("wind_speed"));
                        day_weather_report.setWind_direction(first_day_from_api.getLong("wind_direction"));
                        day_weather_report.setAir_pressure(first_day_from_api.getLong("air_pressure"));
                        day_weather_report.setHumidity(first_day_from_api.getInt("humidity"));
                        day_weather_report.setVisibility(first_day_from_api.getLong("visibility"));
                        day_weather_report.setPredictability(first_day_from_api.getInt("predictability"));

                        weatherReports.add(day_weather_report);                                                                                 //add object to arraylist

                    }

                    forecastByIdResponseListener.onResponse(weatherReports);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Something went wrong in onResponse tryCatch block", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                forecastByIdResponseListener.onError("Something went wrong in implementing getForecastByID");
            }
        });

        MySingleton.getInstance(mContext).addToRequestQueue(request);

    }

    /**
     * Get Forecast of City weather using city name
     * @param cityName name of the city
     * @param getCityForecastByNameCallBack
     */
    public void getWeatherForecastByCityName(String cityName, GetCityForecastByNameCallBack getCityForecastByNameCallBack) {
        getCityId(cityName, new VolleyResponseListener() {                                                              //call getCityId function to get the cityId using the city name
            @Override
            public void onError(String message) {
                getCityForecastByNameCallBack.onError("Something went wrong in implementing getCityID");
            }

            @Override
            public void onResponse(String cityId) {
                getWeatherForecastByCityId(cityId, new ForecastByIdResponseListener() {                                 //on getting response use the cityId to get the weather forecast using the getWeatherForecastByCityId function
                    @Override
                    public void onError(String message) {
                        getCityForecastByNameCallBack.onError("Something went wrong in implementing getForecastByID");
                    }

                    @Override
                    public void onResponse(ArrayList<WeatherReportModel> weatherReports) {
                        getCityForecastByNameCallBack.onResponse(weatherReports);
                    }
                });
            }
        });
    }

    //
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityId);
    }

    public interface ForecastByIdResponseListener {
        void onError(String message);

        void onResponse(ArrayList<WeatherReportModel> weatherReports);
    }

    public interface GetCityForecastByNameCallBack {
        void onError(String message);

        void onResponse(ArrayList<WeatherReportModel> weatherReports);
    }


}
