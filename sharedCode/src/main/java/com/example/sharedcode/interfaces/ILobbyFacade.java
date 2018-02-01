package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public interface ILobbyFacade {
    public void createGame();

    public Game[] getGames();

    public boolean joinGame(String gameID);

    public void startGame(String gameID);

    public Player[] getPlayersForGame(String gameID);


}
