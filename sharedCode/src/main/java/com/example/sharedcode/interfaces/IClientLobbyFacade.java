package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

/**
 * Created by eric on 2/7/18.
 */

public interface IClientLobbyFacade {

    void createGame(String authToken, Game newGame);
    void updateGames(String authToken, Game[] games, String message);
    void joinGame(String authToken, String message, String gameID);
    void startGame(String authToken, String message, String gameID);
    void leaveGame(String authToken, String message, String gameID);
    void getPlayersForGame(String authToken, Player[] players, String message, String gameID);
}
