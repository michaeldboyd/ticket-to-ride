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
    private String name;

    public Route(String city1, String city2, int numberTrains, int trainType) {
        this.city1 = city1;
        this.city2 = city2;
        this.numberTrains = numberTrains;
        this.trainType = trainType;
        this.name = city1 + city2;
    }

    public Route(String city1, String city2, int numberTrains, int trainType, boolean duplicate) {
        this.city1 = city1;
        this.city2 = city2;
        this.numberTrains = numberTrains;
        this.trainType = trainType;
        this.duplicate = duplicate;
        this.name = city1 + city2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (numberTrains != route.numberTrains) return false;
        if (trainType != route.trainType) return false;
        if (duplicate != route.duplicate) return false;
        if (city1 != null ? !city1.equals(route.city1) : route.city1 != null) return false;
        if (city2 != null ? !city2.equals(route.city2) : route.city2 != null) return false;
        return name != null ? name.equals(route.name) : route.name == null;
    }

    @Override
    public int hashCode() {
        int result = city1 != null ? city1.hashCode() : 0;
        result = 31 * result + (city2 != null ? city2.hashCode() : 0);
        result = 31 * result + numberTrains;
        result = 31 * result + trainType;
        result = 31 * result + (duplicate ? 1 : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
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

    public String getName() {
        return name;
    }
}
