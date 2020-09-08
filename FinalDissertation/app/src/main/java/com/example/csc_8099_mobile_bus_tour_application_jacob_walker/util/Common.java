package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.util;


import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

    //code was adopted from the following sources

    //https://github.com/TomaszBialek/Java-Android/blob/master/Android/java/com/example/tomek/javaproject/Common/Common.java
    //https://github.com/eddydn/WeatherApp/blob/master/app/src/main/java/dev/edmt/weatherapp/Common/Common.java

    // whilst the API request below were used primarily as different means of testing.
    // did allow for the use of Picasso library to gain images and place them in the list fragment
    // the Common and apiRequest methodologies act in exactly the same manner as the Retrobuilder class.
    // whilst they would normally be removed from use, they will be incorporated into the development dicussion
    // in the dissertation about introduction of new libraries
    //API keys specified for both API's here

public class Common {
    public static String API_KEY = "cd03ed72fb7929925483ae4053973a13";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";

    @NonNull
    public static String apiRequest(String lat, String lng){
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("?lat=%s&lon=%s&appid=%s&units=metric",lat,lng,API_KEY));
        return sb.toString();
    }

    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return  dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png",icon);
    }

    public static String getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
