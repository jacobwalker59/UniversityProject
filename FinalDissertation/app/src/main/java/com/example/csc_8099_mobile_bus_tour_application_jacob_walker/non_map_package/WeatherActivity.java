package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models_weather.CurrentWeather;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route.UserLocation;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.util.Common;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes.GoogleApiService;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes.RetrofitBuilder;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//weather parsing information was taken from the following

//https://www.youtube.com/watch?v=8-7Ip6xum6E&t=624s
//https://www.youtube.com/watch?v=awYSrhUZQL0&t=2009s
//https://www.youtube.com/watch?v=awYSrhUZQL0&t=2009s

//generalised Retrofit builder information taken from below

//https://medium.com/@shrestharohit/retrofit-openweather-api-98da13f4e16f

public class WeatherActivity extends AppCompatActivity {

    UserLocation location;
    Context mContext;
    private GoogleApiService googleApiService;

    CurrentWeather mWeather;

    TextView name, temp, humidity, wind;
    Button directions;
    ImageView image;

    public String weatherType;
    public String weatherTemp;

    RelativeLayout warning;
    TextView msg;

    // gets information by using retrofit builder and then combining in with the adapter in order to set the information
    // initialised the XML activities in order to display weather information accordingly.
    // Taken from the following
    //https://www.youtube.com/watch?v=oSzMK0e_i18
    //https://www.youtube.com/watch?v=8-7Ip6xum6E&t=624s
    //https://www.youtube.com/watch?v=awYSrhUZQL0&t=872s

    //*******below is the only modifications to the code, it is there to make sure that
    // essentially user location is getting set to something in here
    // since passing the intent as per is too much


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        warning = findViewById(R.id.warning);
        msg = findViewById(R.id.msg);

        mContext = this;
        init();
        location = (UserLocation) getIntent().getSerializableExtra("location");
        if (location != null) {
            name.setText(location.getName());
            getCurrentWeather();
        }
    }

   public void init() {
        name = findViewById(R.id.name);
        temp = findViewById(R.id.temp);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind);
        directions = findViewById(R.id.directions);
        image = findViewById(R.id.image);

        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, MapsDirectionActivity.class);
                intent.putExtra("location", location);
                //intent.putExtra("name", location);
                startActivity(intent);

            }
        });
    }

    public void setLocation(UserLocation newLocation) {
        location = newLocation;
    }

    // putting a location variable in at the bottom
    public void getCurrentWeather() {

        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);
        dialog.show();

        // below functionality taken straight from https://www.youtube.com/watch?v=4JGvDUlfk7Y&list=PLrnPJCHvNZuBdsuDMl3I-EOEOnCh6JNF3&index=6
        // retrofit builder uses GSON converter, and a call is made to the server


        String url = Common.apiRequest("" + location.getLat(), "" + location.getLng());
        Log.d("finalUrl", url);

        googleApiService = RetrofitBuilder.PlacesBuilder().create(GoogleApiService.class);

        Call<CurrentWeather> call = googleApiService.getLocationWeather(url);

        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {

                mWeather = response.body();

                temp.setText(mWeather.getMain().getTemp().toString() + "C°");
                humidity.setText(mWeather.getMain().getHumidity().toString() + "%");
                wind.setText(mWeather.getWind().getSpeed().toString() + "%");
                // the two below should be taken out if they somehow crash the system
                weatherType = mWeather.getWeather().toString();
                weatherTemp = mWeather.getMain().getTemp().toString();

                Picasso.get().load(Common.getImage(mWeather.getWeather().get(0).getIcon()))
                        .into(image);
                warningMethod();

                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //used to specify path constraints for each path.
    //developer can change the path constraints to suit the responses taken from the JSON response.
    //done below using simple if statements but in the future can be taken from the database
   public void warningMethod() {

        boolean tempWarning = false;
        boolean weatherWarning = false;

        if(location.getName().equals("Barn-Side Lake Stroll")) {

            if (mWeather.getMain().getTemp() < 17) {
                tempWarning = true;
                warning.setVisibility(View.VISIBLE);
                msg.setText("Weather is freezing down there!" + " " +
                       location.getName() + " !");
            }

            if((mWeather.getMain().getTemp() > 17)){
                msg.setText("Have a nice trip!");
            }

        }
       if(location.getName().equals("Hill Side Avenue")) {


           if (!(mWeather.getWeather().get(0).getMain().equals("Clear"))) {
               warning.setVisibility(View.VISIBLE);

               msg.setText("Vision may be impaired Tread Cautiously." +
                       location.getName() + " is hazardous under such conditions.");
           }
               else
                   msg.setText("Weather is Cloudy");
           }

       }
    }