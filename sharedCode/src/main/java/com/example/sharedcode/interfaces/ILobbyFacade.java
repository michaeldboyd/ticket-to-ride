package com.example.sharedcode.interfaces;

import com.example.sharedcode.communication.CommandResult;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public interface ILobbyFacade {

    public CommandResult createGame(String gameName);

    public CommandResult getGames();

    public CommandResult joinGame(String gameID, String username);

    public CommandResult leaveGame(String gameID, String username);

    public void startGame(String gameID);

    public CommandResult getPlayersForGame(String gameID);


}
