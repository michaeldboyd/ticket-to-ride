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
    void joinGame(String message, Player player, Game game);
    void startGame(String message, String gameID);
    void leaveGame(String gameID, String message);
}
