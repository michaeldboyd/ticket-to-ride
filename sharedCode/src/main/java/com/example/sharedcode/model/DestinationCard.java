package com.example.sharedcode.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ali on 3/2/2018.
 */

public class DestinationCard implements Serializable {

    private String startCity; //these might be enums, but didn't have those yet
    private String endCity; //or know if we're using those, so I used strings for now
    private int points;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    private boolean completed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DestinationCard that = (DestinationCard) o;

        if (points != that.points) return false;
        if (completed != that.completed) return false;
        if (!startCity.equals(that.startCity)) return false;
        return endCity.equals(that.endCity);
    }

    @Override
    public int hashCode() {
        int result = startCity.hashCode();
        result = 31 * result + endCity.hashCode();
        result = 31 * result + points;
        result = 31 * result + (completed ? 1 : 0);
        return result;
    }

    public DestinationCard(String start, String end, int points){
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
