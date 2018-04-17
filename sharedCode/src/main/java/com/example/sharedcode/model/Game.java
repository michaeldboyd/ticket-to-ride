package com.example.sharedcode.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Game implements Serializable{

    private String gameID;
    private ArrayList<Player> players = new ArrayList<>();
    private DestinationDeck destinationDeck;
    private TrainCardDeck trainCardDeck;
    private TrainCardDeck trainDiscardDeck;
    private FaceUpDeck faceUpDeck;
    private String currentTurnPlayerName;
    // No reason to ever let this be null
    private ArrayList<String> history = new ArrayList<>();

    //The plan for this is that if Player is null, the route is not claimed.
    //If we need to change this we totally can.
    private Map<Route, Player> routesClaimed;
    private Map<String, City> cities;

    //MemVariables pertaining to chat
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private boolean isTyping;
    private String personTyping;
    private int unreadMessages = 0;

    private boolean isLastRound = false;
    private int turnsLeft = -1;
    private boolean isStarted = false;
    private boolean isDone = false;


    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    // Getters / Setters
    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public String getCurrentTurnPlayerName() {
        return currentTurnPlayerName;
    }

    public void setCurrentTurnPlayerName(String currentTurnPlayerName) {
        this.currentTurnPlayerName = currentTurnPlayerName;
    }

    public boolean isLastRound() {
        return isLastRound;
    }

    public void setLastRound(boolean lastRound) {
        isLastRound = lastRound;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public void setTurnsLeft(int turnsLeft) {
        this.turnsLeft = turnsLeft;
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


    public Player getPlayer(String playerName) {
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                return p;
            }
        }

        return null;
    }

    public void updatePlayer(Player player) {
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getName().equals(player.getName())) {
                players.set(i, player);
                break;
            }
        }
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

                Integer cardType = 0;
                try {
                    if (trainCardDeck.size() != 0)
                        cardType = trainCardDeck.drawCard();
                    else {
                        setTrainCardDeck(getTrainDiscardDeck());
                        TrainCardDeck deck = new TrainCardDeck();
                        setTrainDiscardDeck(deck);
                        cardType = trainCardDeck.drawCard();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

                Integer count = player.getHand().get(cardType);

                player.getHand().put(cardType, count + 1);


                break;
            }
        }
    }


    public void addToHistory(String historyItem) {
        history.add(historyItem);
    }



    /**
     * This will either:
     * 1) Change the player
     * 2) End the game if no turns are left
     */
    public void changeTurnForGame() {
        int newPlayerIndex = 0;

        checkFinalRound();

        if(turnsLeft == 0) {
            isDone = true;
        } else {

            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);

                if (p.getName().equals(currentTurnPlayerName)) {
                    newPlayerIndex = i + 1;
                    break;
                }
            }

            if (newPlayerIndex >= players.size()) {
                newPlayerIndex = 0;
            }

            setCurrentTurnPlayerName(players.get(newPlayerIndex).getName());
        }
    }

    /**
     * Will check if the game should be done. If it is, it will decrement the turns left
     */
    public void checkFinalRound(){
        Player currentPlayer = getPlayer(currentTurnPlayerName);

        if(currentPlayer.getTrains() <= 2 || isLastRound) {
            isLastRound = true;

            //Set the turns left to the amount of players [Michael says..] plus one because first player must go again.
            if(turnsLeft < 0) {
                turnsLeft = players.size() + 1;
            } else { //decrement down
                --turnsLeft;
            }
        }
    }

    /**
     * This method looks at all the players and organizes them based on on score; greatest to least
     * @return
     */
    public List<Player> getPlayerListByScore(){
        List<Player> sortedPlayers = new ArrayList<>();

        //Go through players in list
        for(Player p : players){

            //Add them to list in correct position
            if(sortedPlayers.isEmpty()){
                sortedPlayers.add(p);
            } else{
                boolean playerAdded = false;

                for(int i = 0; i < sortedPlayers.size(); i++){
                    int pscore = p.getScore().getTotalPoints();
                    Score other_score = sortedPlayers.get(i).getScore();
                    int other = other_score.getTotalPoints();
                    if(pscore > other)  {
                        sortedPlayers.add(i, p);
                        playerAdded = true;
                        break;
                    }
                }
                if(!playerAdded){
                    sortedPlayers.add(p);
                }
            }
        }
        return sortedPlayers;
    }

    public boolean checkAndResolveTooManyWildcardsInFaceUp() {
        int wildcardCount = 0;

        for(int i : faceUpDeck){
            if(i == TrainType.LOCOMOTIVE){
                //If there are more than 3 locomotives and increment
                wildcardCount++;
                if (wildcardCount >= 3) {
                    break;
                }
            }

            if(wildcardCount >= 3) {
                // Discard train cards
                // Go backwards so that we avoid problems mutating while iterating
                for(int j = faceUpDeck.size() - 1; j >= 0; j--) {
                    trainDiscardDeck.returnDiscarded(faceUpDeck.get(j));
                    faceUpDeck.remove(j);
                }

                //Recreated face up deck
                for(int j = 0; j < 5; j++){
                    try {
                        faceUpDeck.add(trainCardDeck.drawCard());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return true;
            }
        }

        return false;
    }

    public int getColorNotChosen() {
        for(int i = 1; i <= PlayerColors.NUMBER_OF_COLORS; i++){
            boolean colorAlreadChosen = false;
            for(Player p: players){
                if(p.getColor() == i){
                    colorAlreadChosen = true;
                    break;
                }
            }

            if(!colorAlreadChosen){
                return i;
            }
        }

        return 0;
    }


}
