package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models;

import java.io.Serializable;

//get and set methods for everything within the North East, is a devoid class, which is not necessary but was
// a result of the developer calling the JSON upon first use in Newcastle upon Tyne
// represents county area at point of original call

public class Northeast implements Serializable {

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
