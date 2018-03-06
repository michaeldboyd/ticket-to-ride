package com.example.sharedcode.model;

/**
 * Created by Ali on 3/2/2018.
 */

public class City {

    private String cityName;
    private double lat;
    private double lng;

    City (String city, double lat, double lon){
        this.cityName = city;
        this.lat = lat;
        this.lng = lon;
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
        return lng;
    }

    public void setLon(double lon) {
        this.lng = lon;
    }
}
