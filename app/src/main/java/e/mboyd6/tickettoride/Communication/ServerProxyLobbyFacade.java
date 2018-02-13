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
    public void getGames() {
        Command getGamesCommand = CommandFactory.createCommand("ServerLobbyFacade", "_getGames", null, null);

        ClientModel.getInstance().getSocket().send(new Gson().toJson(getGamesCommand));
    }

    //TODO: Should I be passing userID to this? userID is never stored in the client. I think we should be passing the authToekn.
    @Override
    public void joinGame(String gameID, String userID) {
        String[] paramTypes = {gameID.getClass().toString(), userID.getClass().toString()};
        String[] paramValues = {gameID, userID};

        Command joinGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_joinGame", paramTypes, paramValues);

        // TODO - Send joinGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(joinGameCommand));
    }

    @Override
    public void leaveGame(String gameID, String userID) {
        String[] paramTypes = {gameID.getClass().toString(), userID.getClass().toString()};
        String[] paramValues = {gameID, userID};

        Command leaveGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_leaveGame", paramTypes, paramValues);

        // TODO - Send leaveGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(leaveGameCommand));
    }

    @Override
    public void startGame(String gameID) {
        String[] paramTypes = {gameID.getClass().toString()};
        String[] paramValues = {gameID};
        Command startGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_startGame", paramTypes, paramValues);


        // TODO - Send startGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(startGameCommand));
    }

    @Override
    public void getPlayersForGame(String gameID) {
        String[] paramTypes = {gameID.getClass().toString()};
        String[] paramValues = {gameID};

        Command getPlayersForGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_getPlayersForGame", paramTypes, paramValues);

        // TODO - Send getPlayersForGameCommand to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(getPlayersForGameCommand));
    }
}
