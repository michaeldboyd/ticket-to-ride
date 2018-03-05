package com.example.sharedcode.model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Player {

    final public int START_TRAINS = 45;

    public Player(String playerID, String name, int color) {
        this.playerID = playerID;
        this.name = name;
        this.color = color;
    }

    private String playerID;
    private String name;
    private int color;
    private ArrayList<TrainCard> trainCards = new ArrayList<>();
    private ArrayList<DestinationCard> destinationCards = new ArrayList<>();
    private int trains = START_TRAINS;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(ArrayList<TrainCard> trainCards) {
        this.trainCards = trainCards;
    }

    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    public int getTrains() {
        return trains;
    }

    public void setTrains(int trains) {
        this.trains = trains;
    }
}