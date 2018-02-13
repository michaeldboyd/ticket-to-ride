package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLobbyFacade implements IClientLobbyFacade {

    private static ClientLobbyFacade lobbyFacade;

    public static ClientLobbyFacade instance() {
        if (lobbyFacade == null) {
            lobbyFacade = new ClientLobbyFacade();
        }

        return lobbyFacade;
    }


    public static void _createGameReceived(String token, Game newGame) {
        instance().createGame(token, newGame);
    }

    public static void _updateGamesReceived(String token, Game[] games, String message) {
        instance().updateGames(token, games, message);
    }

    public static void _joinGameReceived(String token, String gameID, String playerID, String message) {
        instance().joinGame(token, gameID, playerID, message);
    }

    public static void _startGameReceived(String token, String gameID, String message) {
        instance().startGame(token, gameID, message);
    }

    public static void _leaveGameReceived(String token, String gameID, String message) {
        instance().leaveGame(token, gameID, message);
    }
    static void _getPlayersForGameReceived(String token, String gameID, Player[] players, String message) {
        instance().getPlayersForGame(token, players, gameID, message);
    }




    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    //Must be called after a user creates a new game. this won't be called now
    @Override
    public void createGame(String token, Game newGame) {
        System.out.println("createGame called on CLF");
        ClientModel.getInstance().setCreateGameResponse(newGame);

    }

    @Override
    public void updateGames(String token, Game[] games, String message) {
        System.out.println("updateGames called on CLF");
        ClientModel.getInstance().setUpdateGamesResponse(games, message);
    }

    @Override
    public void joinGame(String token, String gameID, String playerID, String message) {
        System.out.println("joinGame called on CLF");

        ClientModel.getInstance().setJoinGameResponse(gameID, playerID, message);
    }

    //start the game
    @Override
    public void startGame(String token, String gameID, String message) {
        System.out.println("startGameResponse called on CLF");

        ClientModel.getInstance().setStartGameResponse(gameID, message);
    }

    //leave the game
    @Override
    public void leaveGame(String token, String gameID, String message) {
        System.out.println("leaveGame called on CLF");

        ClientModel.getInstance().setLeaveGameResponse(gameID, message);
    }

    @Override
    public void getPlayersForGame(String authToken, Player[] players, String gameID, String message) {
        System.out.println("getPlayersForGame called on CLF");
    }

    @Override
    public void playerColorChanged(String gameID, String playerID, int color) {
        // Don't do anything
        // Game list will be updated automatically
    }
}
