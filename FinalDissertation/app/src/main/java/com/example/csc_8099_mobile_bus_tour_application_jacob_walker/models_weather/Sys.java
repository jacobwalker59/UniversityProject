
package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// POJO (Plain Old Java Class) classes extracted from JSON string and correspond to the information for each JSON object and its corresponding keys.
// the following are POJO classes have simple getter and setter methods for each class.
// serialized override is simply used in the event if JSON key and variable name differ, in which case
//key name is passed as the parameter inside of it.
// Sunset and SunRise

public class Sys {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

}
