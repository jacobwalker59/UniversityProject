package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models;

import java.io.Serializable;

//See North East for further detail.

public class Southwest implements Serializable {

    private String lng;

    private String lat;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "ClassPojo [lng = " + lng + ", lat = " + lat + "]";
    }
}
