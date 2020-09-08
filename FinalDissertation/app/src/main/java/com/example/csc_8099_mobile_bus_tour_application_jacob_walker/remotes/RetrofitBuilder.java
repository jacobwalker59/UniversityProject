package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// retrofit builder creates Strings call to be utilised for both Google API maps and
//OpenWeatherMAP API.
//https://www.youtube.com/watch?v=4JGvDUlfk7Y&list=PLrnPJCHvNZuBdsuDMl3I-EOEOnCh6JNF3&index=6
//information for retrofit can be seen above.
// information confirmed here
//https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23

public class RetrofitBuilder {
    private static Retrofit retrofit = null;
    // each api has a base string which must be utilised.
    // this base string needs to be appended with further information, namely latitude and longitude.
    // Other directions related information can be used, however that will have to form additional functionality
    //i.e. you could pass the reference of bars into here through the calls in either Map Fragment of Weather Activity
    private static final String BASE_URL_Places = "https://maps.googleapis.com/maps/";

    public static Retrofit PlacesBuilder() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_Places)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    // open weather map requires the same principles.
    // same base weather URL, needs to be created. via GSON converter factory.
    //GSON converter factory serializes the objects and changes it to JSON for the server to utilize.


    private static final String BASE_URL_Weather = "http://api.openweathermap.org/data/2.5/weather";

    public static Retrofit WeatherBuilder() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_Weather)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
