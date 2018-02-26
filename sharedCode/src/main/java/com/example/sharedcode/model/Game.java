package com.example.sharedcode.model;

import java.util.ArrayList;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Game {

    private String gameID;

    private ArrayList<Player> players = new ArrayList<Player>();


    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public ArrayList<Player> getPlayers(){
        return players;
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

    }

}
