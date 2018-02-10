package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLobbyFacade;
import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/7/18.
 */

public class ServerProxyLobbyFacade implements IServerLobbyFacade {

    @Override
    public void createGame() {
        Command getGamesCommand = CommandFactory.createCommand("ServerLobbyFacade", "createGame", null, null);
        Sender.sendCommand(getGamesCommand, ClientModel.getInstance().getSession());
    }

    @Override
    public void getGames() {
        Command getGamesCommand = CommandFactory.createCommand("ServerLobbyFacade", "getGames", null, null);
        Sender.sendCommand(getGamesCommand, ClientModel.getInstance().getSession());

    }

    @Override
    public void joinGame(String gameID, String userID) {
        String[] paramTypes = {gameID.getClass().toString(), userID.getClass().toString()};
        String[] paramValues = {gameID, userID};

        Command joinGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "joinGame", paramTypes, paramValues);
        Sender.sendCommand(joinGameCommand, ClientModel.getInstance().getSession());
        // TODO - Send joinGameCommand to Server via socket
    }

    @Override
    public void leaveGame(String gameID, String userID) {
        String[] paramTypes = {gameID.getClass().toString(), userID.getClass().toString()};
        String[] paramValues = {gameID, userID};

        Command leaveGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "leaveGame", paramTypes, paramValues);
        Sender.sendCommand(leaveGameCommand, ClientModel.getInstance().getSession());
        // TODO - Send leaveGameCommand to Server via socket
    }

    @Override
    public void startGame(String gameID) {
        String[] paramTypes = {gameID.getClass().toString()};
        String[] paramValues = {gameID};
        Command startGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "startGame", paramTypes, paramValues);
        Sender.sendCommand(startGameCommand, ClientModel.getInstance().getSession());

        // TODO - Send startGameCommand to Server via socket
    }

    @Override
    public void getPlayersForGame(String gameID) {
        String[] paramTypes = {gameID.getClass().toString()};
        String[] paramValues = {gameID};

        Command getPlayersForGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "getPlayersForGame", paramTypes, paramValues);
        Sender.sendCommand(getPlayersForGameCommand, ClientModel.getInstance().getSession());
        // TODO - Send getPlayersForGameCommand to Server via socket
    }
}
