package e.mboyd6.tickettoride.Communication;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.ClientModel;


public class ServerProxyLobbyFacade implements IServerLobbyFacade {
    private Map args = new HashMap();
    private static ServerProxyLobbyFacade _instance = new ServerProxyLobbyFacade();

    public static ServerProxyLobbyFacade instance() {

        if (_instance == null){
            _instance = new ServerProxyLobbyFacade();
        }

        return _instance;
    }

    private ServerProxyLobbyFacade() { args.put(JsonWriter.TYPE, true);}





    @Override
    public void createGame(String authToken) {
        String[] paramTypes = {authToken.getClass().toString()};
        String[] paramValues = {authToken};
        Command getGamesCommand = CommandFactory.createCommand(null, "ServerLobbyFacade", "_createGame", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(getGamesCommand, args));
    }

    @Override
    public void getGames(String authToken) {
        String[] paramTypes = {authToken.getClass().toString()};
        String[] paramValues = {authToken};
        Command getGamesCommand = CommandFactory.createCommand(null, "ServerLobbyFacade", "_getGames", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(getGamesCommand, args));
    }

    @Override
    public void joinGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, gameID};

        Command joinGameCommand = CommandFactory.createCommand(null, "ServerLobbyFacade", "_joinGame", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(joinGameCommand, args));
    }

    @Override
    public void leaveGame(String authToken, String gameID, String playerID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString()};
        String[] paramValues = {authToken, gameID, playerID};

        Command leaveGameCommand = CommandFactory.createCommand(null, "ServerLobbyFacade", "_leaveGame", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(leaveGameCommand, args));
    }

    @Override
    public void startGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, gameID};
        Command startGameCommand = CommandFactory.createCommand(null, "ServerLobbyFacade", "_startGame", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(startGameCommand, args));
    }

    @Override
    public void getPlayersForGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, gameID};

        Command getPlayersForGameCommand = CommandFactory.createCommand(null, "ServerLobbyFacade", "_getPlayersForGame", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(getPlayersForGameCommand, args));
    }

    @Override
    public void playerColorChanged(String authToken, String gameID, String playerID, int color) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString(), "int"};
        Object[] paramValues = {authToken, gameID, playerID, Integer.valueOf(color)};

        Command playerColorChangedCommand = CommandFactory.createCommand(null,"ServerLobbyFacade", "_playerColorChanged", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(playerColorChangedCommand, args));
    }
}
