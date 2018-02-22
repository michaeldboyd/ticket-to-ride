package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.google.gson.Gson;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/7/18.
 */

public class ServerProxyLobbyFacade implements IServerLobbyFacade {

    private static ServerProxyLobbyFacade _instance = new ServerProxyLobbyFacade();

    private ServerProxyLobbyFacade() {}

    public static ServerProxyLobbyFacade instance() {return _instance;}

    @Override
    public void createGame(String authToken) {
        String[] paramTypes = {authToken.getClass().toString()};
        String[] paramValues = {authToken};
        Command getGamesCommand = CommandFactory.createCommand("ServerLobbyFacade", "_createGame", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(new Gson().toJson(getGamesCommand));
    }

    @Override
    public void getGames(String authToken) {
        String[] paramTypes = {authToken.getClass().toString()};
        String[] paramValues = {authToken};
        Command getGamesCommand = CommandFactory.createCommand("ServerLobbyFacade", "_getGames", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(new Gson().toJson(getGamesCommand));
    }

    //TODO: Should I be passing userID to this? userID is never stored in the client. I think we should be passing the authToekn.
    @Override
    public void joinGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, gameID};

        Command joinGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_joinGame", paramTypes, paramValues);

        // TODO - Send joinGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(joinGameCommand));
    }

    @Override
    public void leaveGame(String authToken, String gameID, String playerID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString()};
        String[] paramValues = {authToken, gameID, playerID};

        Command leaveGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_leaveGame", paramTypes, paramValues);

        // TODO - Send leaveGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(leaveGameCommand));
    }

    @Override
    public void startGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, gameID};
        Command startGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_startGame", paramTypes, paramValues);


        // TODO - Send startGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(startGameCommand));
    }

    @Override
    public void getPlayersForGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, gameID};

        Command getPlayersForGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_getPlayersForGame", paramTypes, paramValues);

        // TODO - Send getPlayersForGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(getPlayersForGameCommand));
    }

    @Override
    public void playerColorChanged(String authToken, String gameID, String playerID, int color) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString(), "int"};
        Object[] paramValues = {authToken, gameID, playerID, Integer.valueOf(color)};

        Command playerColorChangedCommand = CommandFactory.createCommand("ServerLobbyFacade", "_playerColorChanged", paramTypes, paramValues);

        // TODO - Send getPlayersForGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(playerColorChangedCommand));
    }
}
