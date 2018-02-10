package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLobbyFacade;

/**
 * Created by eric on 2/7/18.
 */

public class ServerProxyLobbyFacade implements IServerLobbyFacade {

    @Override
    public void createGame() {
        Command getGamesCommand = CommandFactory.createCommand("ServerLobbyFacade", "_createGame", null, null);
    }

    @Override
    public void getGames() {
        Command getGamesCommand = CommandFactory.createCommand("ServerLobbyFacade", "_getGames", null, null);

        // TODO - Send getGamesCommand to Server via socket
    }

    @Override
    public void joinGame(String gameID, String userID) {
        String[] paramTypes = {gameID.getClass().toString(), userID.getClass().toString()};
        String[] paramValues = {gameID, userID};

        Command joinGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_joinGame", paramTypes, paramValues);

        // TODO - Send joinGameCommand to Server via socket
    }

    @Override
    public void leaveGame(String gameID, String userID) {
        String[] paramTypes = {gameID.getClass().toString(), userID.getClass().toString()};
        String[] paramValues = {gameID, userID};

        Command leaveGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_leaveGame", paramTypes, paramValues);

        // TODO - Send leaveGameCommand to Server via socket
    }

    @Override
    public void startGame(String gameID) {
        String[] paramTypes = {gameID.getClass().toString()};
        String[] paramValues = {gameID};

        Command startGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_startGame", paramTypes, paramValues);

        // TODO - Send startGameCommand to Server via socket
    }

    @Override
    public void getPlayersForGame(String gameID) {
        String[] paramTypes = {gameID.getClass().toString()};
        String[] paramValues = {gameID};

        Command getPlayersForGameCommand = CommandFactory.createCommand("ServerLobbyFacade", "_getPlayersForGame", paramTypes, paramValues);

        // TODO - Send getPlayersForGameCommand to Server via socket
    }
}
