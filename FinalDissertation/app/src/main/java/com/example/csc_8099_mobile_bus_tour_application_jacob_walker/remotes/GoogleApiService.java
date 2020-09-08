package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models_weather.CurrentWeather;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models.MyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

// returns a call object, to the current weather object.
// abstract interface class, part of retrofit builder whose call method witll be implemented.
// simple abstract function which needs to be overridden
// taken from below
//https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23

public interface GoogleApiService {

    @GET
    Call<CurrentWeather> getLocationWeather(@Url String url);

    @GET
    Call<MyPlaces> getMyNearByPlaces(@Url String url);

}



