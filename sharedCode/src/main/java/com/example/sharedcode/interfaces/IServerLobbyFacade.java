package com.example.sharedcode.interfaces;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public interface IServerLobbyFacade {

    void createGame(String authtoken); //join game for creator and update list
    void getGames();
    void joinGame(String gameID, String playerID);
    void leaveGame(String gameID, String playerID);
    void startGame(String gameID);
    void getPlayersForGame(String gameID);


}
