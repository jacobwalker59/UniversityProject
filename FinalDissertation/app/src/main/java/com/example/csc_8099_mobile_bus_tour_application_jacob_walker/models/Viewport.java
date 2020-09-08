package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models;

import java.io.Serializable;

//Part of the classes passed in from JSON converter.
//Used for NorthEast and SouthWest classes

public class Viewport implements Serializable {

    private Southwest southwest;

    private Northeast northeast;

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    @Override
    public String toString() {
        return "ClassPojo [southwest = " + southwest + ", northeast = " + northeast + "]";
    }
}
