package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route;

//simple POJO Class for bus Stop locations
//includes Get and Set methods for each variable

public class Stop {
    String name;
    Double lat;
    Double lng;

    public Stop(String name, Double lat, Double lng) {
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
