package com.example.sharedcode.model;

import java.util.ArrayList;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Game {

    private String gameID;

    //Do we want to have this be an array of players instead of strings?
    ArrayList<String> playerIDs = new ArrayList<String>();


    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public ArrayList<String> getPlayerIDs(){
        return playerIDs;
    }

    /*
    * Checks if value is in list.
    *
    * If it isn't, adds player to list and returns true.
    * Else does nothing and returns false.
    *
    * */
    public boolean addPlayer(String playerID){
        if(!playerIDs.contains(playerID)) {
            playerIDs.add(playerID);
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
        if(playerIDs.contains(playerID)) {
            playerIDs.remove(playerID);
            return true;
        }
        else{
            return false;
        }
    }

    public void startGame(){

    }

}
