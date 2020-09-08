
package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// POJO (Plain Old Java Class) classes extracted from JSON string and correspond to the information for each JSON object and its corresponding keys.
// the following are POJO classes have simple getter and setter methods for each class.
// serialized override is simply used in the event if JSON key and variable name differ, in which case
//key name is passed as the parameter inside of it.
public class Main {

    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

}
