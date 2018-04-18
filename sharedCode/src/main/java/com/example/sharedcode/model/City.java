package com.example.sharedcode.model;

import java.io.Serializable;

/**
 * Created by Ali on 3/2/2018.
 */

public class City implements Serializable {

    private String cityName;
    private double lat;
    private double lon;

    City (String city, double lat, double lon){
        this.cityName = city;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
