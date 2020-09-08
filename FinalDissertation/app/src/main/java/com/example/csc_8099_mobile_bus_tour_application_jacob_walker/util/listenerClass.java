package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.util;


//weather listener will be used to solve future mutext problem
//developer kept in to make calls to this class alongside the mutex, so that this class cannot operate
//unless JSON is called first

public class listenerClass {
    private weatherResponceInterface weatherListener;

    public void setMyCustomListener(weatherResponceInterface weatherListener){
        this.weatherListener=weatherListener;
    }
}
