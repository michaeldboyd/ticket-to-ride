package com.example.sharedcode.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Game {

    private String gameID;
    private ArrayList<Player> players = new ArrayList<>();
    private DestinationDeck destinationDeck;
    private TrainCardDeck trainCardDeck;
    private TrainCardDeck trainDiscardDeck;
    private FaceUpDeck faceUpDeck;
    private String currentTurnPlayerID;
    private ArrayList<String> history;
    //The plan for this is that if Player is null, the route is not claimed.
    //If we need to change this we totally can.
    private Map<Route, Player> routesClaimed;
    private Map<String, City> cities;

    //MemVariables pertaining to chat
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private boolean isTyping;
    private String personTyping;
    private int unreadMessages = 0;

    private boolean isStarted = false;



    // Getters / Setters
    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public String getCurrentTurnPlayerID() {
        return currentTurnPlayerID;
    }

    public void setCurrentTurnPlayerID(String currentTurnPlayerID) {
        this.currentTurnPlayerID = currentTurnPlayerID;
    }

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

    public TrainCardDeck getTrainDiscardDeck() {
        return trainDiscardDeck;
    }

    public void setTrainDiscardDeck(TrainCardDeck trainDiscardDeck) {
        this.trainDiscardDeck = trainDiscardDeck;
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
    public void setStarted(boolean started) {
        isStarted = started;
    }
    public boolean isStarted() {return isStarted; }

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
    public boolean removePlayer(String playerName){
        boolean success = false;

        for(int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getName().equals(playerName)) {
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

    public Map<String, City> getCities() {
        return cities;
    }

    public void setCities(Map<String, City> cities) {
        this.cities = cities;
    }

    public void addDestCardToPlayer(String playerName) {
        for (Player player :
                players) {
            if (player.getName().equals(playerName)) {
                player.getDestinationCards().add(destinationDeck.drawCard());
                break;
            }
        }
    }

    public void addTrainCardToPlayerHand(String playerName) {
            for (Player player : players) {
                if (player.getName().equals(playerName)) {

                    int cardType = 0;
                    try{
                        cardType = trainCardDeck.drawCard();

                    }catch(Exception e){
                        try {

                            setTrainCardDeck(getTrainDiscardDeck());
                            TrainCardDeck deck = new TrainCardDeck();
                            setTrainDiscardDeck(deck);
                            cardType = trainCardDeck.drawCard();

                        }catch(Exception ex){

                            ex.printStackTrace();
                        }
                    }

                    int count = player.getHand().get(cardType);

                    player.getHand().put(cardType, count + 1);

                    break;
                }
            }
    }
}
