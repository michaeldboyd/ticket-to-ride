package com.example.server;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandResult;
import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import Model.serverModel;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLobbyFacade implements IServerLobbyFacade {

    int playerLimit = 5;

    // Creates command to create game and send back to the client
    @Override
    public void createGame() {
        // Don't need to check for existence of a new game because this should only be called when creating a brand new game

        // TODO - message parameter is always null -- we should remove it or figure out potential errors/problems
        // Create a random UUID for gameID to pass to createGame method
        ClientProxyLobbyFacade.instance().createGame(UUID.randomUUID().toString(), null);
    }

    @Override
    public void getGames() {
        Game[] games = (Game[]) serverModel.instance().games.values().toArray();

        // TODO - message parameter is always null -- we should remove it or figure out potential errors/problems
        ClientProxyLobbyFacade.instance().updateGames(games, null);
    }

    @Override
    public void joinGame(String gameID, String playerID) {
        String message = null;

        if (serverModel.instance().games.containsKey(gameID)) {

            // Only set message if we fail to add user to the game
            if (!serverModel.instance().games.get(gameID).addPlayer(playerID)) {
                message = "Could not add player to game [id = " + gameID + "]";
            }

        }

        ClientProxyLobbyFacade.instance().joinGame(gameID, message);
    }

    @Override
    public void leaveGame(String gameID, String playerID) {
        String message = null;

        // This returns false if playerID is not part of game
        if (!serverModel.instance().games.get(gameID).removePlayer(playerID)) {
            message = "couldn't remove player because wasn't in game yet";
        }

        ClientProxyLobbyFacade.instance().leaveGame(gameID, message);
    }


    @Override
    public void startGame(String gameID) {
        String message = null;

        if (!serverModel.instance().games.containsKey(gameID)) {
            message = "Game doesn't exist.";
        }

        ClientProxyLobbyFacade.instance().startGame(gameID, message);
    }

    @Override
    public void getPlayersForGame(String gameID) {
        String message = null;
        Player[] players = null;

        //create commandresult
        if (serverModel.instance().games.containsKey(gameID)) {

            players = (Player[])serverModel.instance().games.get(gameID).getPlayerIDs().toArray();
        } else {

            message = "Game does not exist.";
        }

        ClientProxyLobbyFacade.instance().getPlayersForGame(gameID, players, message);
    }

}
