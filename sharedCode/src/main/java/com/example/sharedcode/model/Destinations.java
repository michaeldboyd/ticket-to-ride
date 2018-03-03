package com.example.sharedcode.model;

import java.util.ArrayList;

/**
 * Created by Ali on 3/2/2018.
 */

public class Destinations {

    private String startCity; //these might be enums, but didn't have those yet
    private String endCity; //or know if we're using those, so I used strings for now
    private int points;
    private ArrayList<Route> routesClaimed; 

    Destinations(String start, String end, int points){
        this.startCity = start;
        this.endCity = end;
        this.points = points;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
