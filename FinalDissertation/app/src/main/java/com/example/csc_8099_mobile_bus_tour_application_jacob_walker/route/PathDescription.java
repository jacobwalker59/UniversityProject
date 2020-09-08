package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route;

//POJO class for Adapter.
//Set and get methods are passed in for each individual item

public class PathDescription {

    public String desc;
    public double miles;

    public PathDescription(String desc, double miles) {
        this.desc = desc;
        this.miles = miles;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }
}
