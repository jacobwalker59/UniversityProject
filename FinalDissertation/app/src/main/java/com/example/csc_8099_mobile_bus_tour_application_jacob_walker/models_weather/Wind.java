
package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


// POJO (Plain Old Java Class) classes extracted from JSON string and correspond to the information for each JSON object and its corresponding keys.
// the following are POJO classes have simple getter and setter methods for each class.
// serialized override is simply used in the event if JSON key and variable name differ, in which case
//key name is passed as the parameter inside of it.
public class Wind {

    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

}
