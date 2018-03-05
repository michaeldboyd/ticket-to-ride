package com.example.sharedcode.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private Map<Integer, ArrayList<TrainCard>> hand = new HashMap<>();
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

    public Map<Integer, ArrayList<TrainCard>> getHand() {
        return hand;
    }

    public void setHand(Map<Integer, ArrayList<TrainCard>> hand) {
        this.hand = hand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (START_TRAINS != player.START_TRAINS) return false;
        if (color != player.color) return false;
        if (playerID != null ? !playerID.equals(player.playerID) : player.playerID != null)
            return false;
        return name != null ? name.equals(player.name) : player.name == null;
    }

    @Override
    public int hashCode() {
        int result = START_TRAINS;
        result = 31 * result + (playerID != null ? playerID.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + color;
        return result;
    }
}