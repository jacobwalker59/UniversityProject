package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

//irrelevant to this code at the moment
// the developer began adding extra functionality so developer the project after the deadline
// code will take images from a private JSON file
//rather than use an API
//mix of methods to improve coding ability

public class JsonParsedObject {

    private String name;
    private String FirstPicture;
    private String SecondPicture;

    public JsonParsedObject(String name, String firstPicture, String secondPicture) {
        this.name = name;
        FirstPicture = firstPicture;
        SecondPicture = secondPicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstPicture() {
        return FirstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        FirstPicture = firstPicture;
    }

    public String getSecondPicture() {
        return SecondPicture;
    }

    public void setSecondPicture(String secondPicture) {
        SecondPicture = secondPicture;
    }
}
