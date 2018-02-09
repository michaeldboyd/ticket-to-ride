package com.example.server;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import Model.serverModel;

/**
 * Created by eric on 2/7/18.
 */

public class ClientProxyLobbyFacade implements IClientLobbyFacade {

    private static ClientProxyLobbyFacade lobbyFacade;

    public static ClientProxyLobbyFacade instance() {
        if (lobbyFacade == null) {
            lobbyFacade = new ClientProxyLobbyFacade();
        }

        return lobbyFacade;
    }

    private ClientProxyLobbyFacade() {}


    @Override
    public void createGame(String gameID, String message) {
        Game newGame = new Game();
        newGame.setGameID(gameID);
        serverModel.instance().games.put(gameID, newGame);

        // This is called after the Server has attempted to get all games
        // If successful, message == null

        String[] paramTypes = {newGame.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {newGame, message};
        Command createGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "createGame", paramTypes, paramValues);

        // TODO - Send createGameClientCommand to Client via socket
    }

    @Override
    public void updateGames(Game[] games, String message) {
        // This is called after the Server has attempted to get all games
        // If successful, message == null

        String[] paramTypes = {games.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {games, message};
        Command updateGamesClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "updateGames", paramTypes, paramValues);

        // TODO - Send updateGamesClientCommand to Client via socket
    }

    @Override
    public void joinGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == null

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};
        Command joinGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "joinGame", paramTypes, paramValues);

        // TODO - Send joinGameClientCommand to Client via socket
    }

    @Override
    public void startGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == null

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};
        Command startGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "startGame", paramTypes, paramValues);

        // TODO - Send startGameClientCommand to Client via socket
    }

    @Override
    public void leaveGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == null

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};
        Command leaveGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "leaveGame", paramTypes, paramValues);

        // TODO - Send leaveGameClientCommand to Client via socket
    }

    @Override
    public void getPlayersForGame(String gameID, Player[] players, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == null

        String[] paramTypes = {gameID.getClass().toString(), players.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, players, message};
        Command getPlayersForGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "getPlayersForGame", paramTypes, paramValues);

        // TODO - Send getPlayersForGameClientCommand to Client via socket
    }
}
