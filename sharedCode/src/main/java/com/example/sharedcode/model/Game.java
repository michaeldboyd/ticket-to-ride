package com.example.sharedcode.model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Game {

    private String gameID;
    private ArrayList<Player> players = new ArrayList<Player>();
    private DestinationDeck destinationDeck;
    private TrainCardDeck trainCardDeck;
    private FaceUpDeck faceUpDeck;
    //The plan for this is that if Player is null, the route is not claimed.
    //If we need to change this we totally can.
    private Map<Route, Player> routesClaimed;

    //MemVariables pertaining to chat
    private ArrayList<ChatMessage> chatMessages;
    private boolean isTyping;
    private String personTyping;
    private int unreadMessages = 0;

    public String getPersonTyping() {
        return personTyping;
    }

    public void setPersonTyping(String personTyping) {
        this.personTyping = personTyping;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean typing) {
        isTyping = typing;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public DestinationDeck getDestinationDeck() {
        return destinationDeck;
    }

    public void setDestinationDeck(DestinationDeck destinationDeck) {
        this.destinationDeck = destinationDeck;
    }

    public TrainCardDeck getTrainCardDeck() {
        return trainCardDeck;
    }

    public void setTrainCardDeck(TrainCardDeck trainCardDeck) {
        this.trainCardDeck = trainCardDeck;
    }

    public FaceUpDeck getFaceUpDeck() {
        return faceUpDeck;
    }

    public void setFaceUpDeck(FaceUpDeck faceUpDeck) {
        this.faceUpDeck = faceUpDeck;
    }

    public Map<Route, Player> getRoutesClaimed() {
        return routesClaimed;
    }

    public void setRoutesClaimed(Map<Route, Player> routesClaimed) {
        this.routesClaimed = routesClaimed;
    }


    /*
    * Checks if value is in list.
    *
    * If it isn't, adds player to list and returns true.
    * Else does nothing and returns false.
    *
    * */
    public boolean addPlayer(Player player){
        if(players.size() >= 5){
            return false;
        }
        if(!players.contains(player)) {

            players.add(player);
            return true;
        }
        else {
            return false;
        }
    }


    /*
   * Checks if value is in list.
   *
   * If it is, removes player from list and returns true.
   * Else does nothing and returns false.
   *
   * */
    public boolean removePlayer(String playerID){
        boolean success = false;

        for(int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getPlayerID().equals(playerID)) {
                players.remove(i);
                success = true;
                break;
            }
        }

        return success;
    }

    public void startGame() {
        GameInitializer gameInitializer = new GameInitializer();
        gameInitializer.initializeGame(this);
    }

}
