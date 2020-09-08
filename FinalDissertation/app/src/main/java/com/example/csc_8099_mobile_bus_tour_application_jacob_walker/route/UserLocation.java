package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route;

import java.io.Serializable;

//Simple POJO class for user location name
//includes set and get methods only to be called for Recycler View Usage

public class UserLocation implements Serializable {
    String name;
    Double lat;
    Double lng;

    public UserLocation(String name,  Double lat, Double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
