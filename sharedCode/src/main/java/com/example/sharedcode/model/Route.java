package com.example.sharedcode.model;

/**
 * Created by Ali on 2/24/2018.
 */

public class Route {

    private String startCity; //these might be enums, but didn't have those yet
    private String endCity; //or know if we're using those, so I used strings for now
    private int points;
    private String color; //also not sure if we're using enums still for this
    private int length;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
