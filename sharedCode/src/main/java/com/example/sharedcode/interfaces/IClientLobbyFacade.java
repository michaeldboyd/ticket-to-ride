package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

/**
 * Created by eric on 2/7/18.
 */

public interface IClientLobbyFacade {

    void createGame(Game newGame);
    void updateGames(Game[] games, String message);
    void joinGame(String gameID, String message);
    void startGame(String gameID, String message);
    void leaveGame(String gameID, String message);
    void getPlayersForGame(String gameID, Player[] players, String message);
    void playerColorChanged(String playerID, String gameID, PlayerColors color);
}
