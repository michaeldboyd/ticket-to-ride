package com.example.sharedcode.model;

/**
 * Created by Ali on 2/24/2018.
 */

public class Route {

    private String city1;
    private String city2;
    private int numberTrains;
    private int trainType;
    private boolean duplicate = false;

    public Route(String city1, String city2, int numberTrains, int trainType) {
        this.city1 = city1;
        this.city2 = city2;
        this.numberTrains = numberTrains;
        this.trainType = trainType;
    }

    public Route(String city1, String city2, int numberTrains, int trainType, boolean duplicate) {
        this.city1 = city1;
        this.city2 = city2;
        this.numberTrains = numberTrains;
        this.trainType = trainType;
        this.duplicate = duplicate;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public int getTrainType() {
        return trainType;
    }

    public void setTrainType(int trainType) {
        this.trainType = trainType;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public int getNumberTrains() {
        return numberTrains;
    }

    public void setNumberTrains(int numberTrains) {
        this.numberTrains = numberTrains;
    }
}
