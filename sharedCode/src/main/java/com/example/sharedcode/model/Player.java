package com.example.sharedcode.model;

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
        Map <Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < 9; i++){
            map.put((Integer)i, 0);
        }
        hand = map;
    }

    private int longestPath = 0;
    private String playerID;
    private String name;
    private int color;
    private Map<Integer, Integer> hand = new HashMap<>(); // <TrainType, numCards>
    private ArrayList<DestinationCard> destinationCards = new ArrayList<>();
    private int trains = START_TRAINS;
    private Score score = new Score();

    public boolean hasLongestPath() {
        return score.isLongestRoute();
    }

    public void setLongestPath(boolean hasLongestPath) { this.getScore().setLongestRoute(hasLongestPath); }

    public int getLongestPath() {
        return longestPath;
    }

    public void setLongestPath(int longestPath) {
        this.longestPath = longestPath;
    }

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

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
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

    public Map<Integer, Integer> getHand() {
        return hand;
    }

    public void setHand(Map<Integer, Integer> hand) {
        this.hand = hand;
    }

    public int removeFromHand(Route route, int wildcardsUsed) {

        for(int i = 0; i < route.getNumberTrains(); i++){
            if(wildcardsUsed > 0){
                hand.put(TrainType.LOCOMOTIVE, hand.get(TrainType.LOCOMOTIVE) - 1);
                wildcardsUsed--;
            } else {
                hand.put(route.getTrainType(), hand.get(route.getTrainType()) - 1);
            }
        }

        return cardsInHand();
    }

    public int cardsInHand() {
        int cardsLeft = 0;
        for(int i = 0; i < hand.size(); i++){
            cardsLeft += hand.get(i);
        }
        return cardsLeft;
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