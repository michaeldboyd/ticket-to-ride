package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.PlayerColors;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public interface IServerLobbyFacade {

    void createGame(String authToken); //join game for creator and update list
    void getGames(String authToken);
    void joinGame(String authToken, String gameID);
    void leaveGame(String authToken, String gameID, String playerID);
    void startGame(String authToken, String gameID);
    void getPlayersForGame(String authToken, String gameID);
    void playerColorChanged(String authToken, String playerID, String gameID, int color);
}
