package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

// simple POJO class for all Bus Stop objects

public class BusStop {

    private String BusStopName;
    private String Approach;
    private String Returning;

    public BusStop(String busStopName, String approach, String returning) {
        BusStopName = busStopName;
        Approach = approach;
        Returning = returning;
    }

    public String getBusStopName() {
        return BusStopName;
    }

    public void setBusStopName(String busStopName) {
        BusStopName = busStopName;
    }

    public String getApproach() {
        return Approach;
    }

    public void setApproach(String approach) {
        Approach = approach;
    }

    public String getReturning() {
        return Returning;
    }

    public void setReturning(String returning) {
        Returning = returning;
    }
}
