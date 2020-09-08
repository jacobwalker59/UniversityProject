package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models;

import java.io.Serializable;

//https://codebeautify.org/jsonviewer
//website above used to convert JSON to POJO classes
// each POJO class presents key/value information corresponding to each object within the JSON
//only get and set methods are typical for all POJO classes.

public class Geometry implements Serializable {

    private Viewport viewport;

    private Location location;

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ClassPojo [viewport = " + viewport + ", location = " + location + "]";
    }
}
