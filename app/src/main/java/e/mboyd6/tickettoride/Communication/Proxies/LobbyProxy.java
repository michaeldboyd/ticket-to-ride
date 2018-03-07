package e.mboyd6.tickettoride.Communication.Proxies;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLobbyFacade;

import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.ClientModel;


public class LobbyProxy implements IServerLobbyFacade {
    private Map args = new HashMap();
    private static LobbyProxy _instance = new LobbyProxy();

    public static LobbyProxy instance() {

        if (_instance == null){
            _instance = new LobbyProxy();
        }

        return _instance;
    }

    private LobbyProxy() { args.put(JsonWriter.TYPE, true);}


    private final String CLASS_NAME = "Facades.ServerLobby";



    @Override
    public void createGame(String authToken) {
        String[] paramTypes = {authToken.getClass().toString()};
        String[] paramValues = {authToken};
        Command getGamesCommand = CommandFactory.createCommand(null, CLASS_NAME, "_createGame", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(getGamesCommand, args));
    }

    @Override
    public void getGames(String authToken) {
        String[] paramTypes = {authToken.getClass().toString()};
        String[] paramValues = {authToken};
        Command getGamesCommand = CommandFactory.createCommand(null, CLASS_NAME, "_getGames", paramTypes, paramValues);

        Sender.sendToServer(getGamesCommand);
    }

    @Override
    public void joinGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, gameID};

        Command joinGameCommand = CommandFactory.createCommand(null, CLASS_NAME, "_joinGame", paramTypes, paramValues);

        Sender.sendToServer(joinGameCommand);
    }

    @Override
    public void leaveGame(String authToken, String gameID, String playerID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString()};
        String[] paramValues = {authToken, gameID, playerID};

        Command leaveGameCommand = CommandFactory.createCommand(null, CLASS_NAME, "_leaveGame", paramTypes, paramValues);

        Sender.sendToServer(leaveGameCommand);
    }

    @Override
    public void startGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, gameID};
        Command startGameCommand = CommandFactory.createCommand(null, CLASS_NAME, "_startGame", paramTypes, paramValues);

        Sender.sendToServer(startGameCommand);
    }

    @Override
    public void playerColorChanged(String authToken, String gameID, String playerID, int color) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString(), "int"};
        Object[] paramValues = {authToken, gameID, playerID, color};

        Command playerColorChangedCommand = CommandFactory.createCommand(null,CLASS_NAME, "_playerColorChanged", paramTypes, paramValues);

        Sender.sendToServer(playerColorChangedCommand);
    }
}
