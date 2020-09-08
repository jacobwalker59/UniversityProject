package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models_weather.CurrentWeather;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route.UserLocation;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.util.Common;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes.GoogleApiService;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes.RetrofitBuilder;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.util.weatherResponceInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Code was taken from the following youtube videos.

//https://www.youtube.com/watch?v=8-7Ip6xum6E&t=624s
//https://www.youtube.com/watch?v=awYSrhUZQL0&t=2009s
//https://www.youtube.com/watch?v=awYSrhUZQL0&t=2009s


public class WeatherConfiguration extends AppCompatActivity {

    UserLocation location;
    Context mContext;
    private GoogleApiService googleApiService;

    CurrentWeather mWeather;

    public String weatherType ="";
    public String weatherTemp ="";

    weatherResponceInterface weatherInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        weatherInterface= (weatherResponceInterface) this;
        }

    public void setLocation (UserLocation newLocation)
    {
        location = newLocation;
    }

    // putting a location variable in at the bottom
    public void getCurrentWeather() {

        String url = Common.apiRequest("" + location.getLat(), "" + location.getLng());
        Log.d("finalUrl", url);

        googleApiService = RetrofitBuilder.PlacesBuilder().create(GoogleApiService.class);

        Call<CurrentWeather> call = googleApiService.getLocationWeather(url);

        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {

                mWeather = response.body();
                //here mWeather has been set to the body of the response call
                // inside the response call, all methods within are accessible, note that
                // sunrise and sunset could also be used as well from Sys class and utilised along with get paths and notifications.

                // the two below should be taken out if they somehow crash the system
                weatherType = mWeather.getWeather().toString();
                weatherTemp = mWeather.getMain().getTemp().toString();
                weatherInterface.OnWeatherResponce();
                finish();

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getWeatherType(){
        return weatherType;
    }

    public String getWeatherTemp(){
        return weatherTemp;
    }

}
