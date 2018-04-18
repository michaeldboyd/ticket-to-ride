package com.example.sharedcode.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ali on 2/24/2018.
 */

public class TrainCard  implements Serializable {

    private int trainCarType;

    public TrainCard(int trainCarType) {
        this.trainCarType = trainCarType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainCard trainCard = (TrainCard) o;

        if (trainCarType != trainCard.trainCarType) return false;
        return color != null ? color.equals(trainCard.color) : trainCard.color == null;
    }

    @Override
    public int hashCode() {
        int result = trainCarType;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    private String color;

    public int getTrainCarType() {
        return trainCarType;
    }

    public void setTrainCarType(int trainCarType) {
        this.trainCarType = trainCarType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
