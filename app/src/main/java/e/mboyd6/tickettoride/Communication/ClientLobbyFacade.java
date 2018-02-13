package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

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


    public static void _createGameReceived(Game newGame) {
        instance().createGame(newGame);
    }

    public static void _updateGamesReceived(Game[] games, String message) {
        instance().updateGames(games, message);
    }

    public static void _joinGameReceived(String gameID, String message) {
        instance().joinGame(gameID, message);
    }

    public static void _startGameReceived(String gameID, String message) {
        instance().startGame(gameID, message);
    }

    public static void _leaveGameReceived(String gameID, String message) {
        instance().leaveGame(gameID, message);
    }
    static void _getPlayersForGameReceived(String gameID, Player[] players, String message) {
        instance().getPlayersForGame(gameID, players, message);
    }




    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    //Must be called after a user creates a new game. this won't be called now
    @Override
    public void createGame(Game newGame) {
        System.out.println("createGame called on CLF");
        ClientModel.getInstance().setCreateGameResponse(newGame);

    }

    @Override
    public void updateGames(Game[] games, String message) {
        System.out.println("updateGames called on CLF");
        ClientModel.getInstance().setUpdateGamesResponse(games, message);
    }

    @Override
    public void joinGame(String gameID, String message) {
        System.out.println("joinGame called on CLF");

        ClientModel.getInstance().setJoinGameResponse(gameID, message);
    }

    //start the game
    @Override
    public void startGame(String gameID, String message) {
        System.out.println("startGame called on CLF");

        ClientModel.getInstance().setStartGameResponse(gameID, message);
    }

    //leave the game
    @Override
    public void leaveGame(String gameID, String message) {
        System.out.println("leaveGame called on CLF");

        ClientModel.getInstance().setLeaveGameResponse(gameID, message)
    }

    @Override
    public void getPlayersForGame(String gameID, Player[] players, String message) {
        System.out.println("getPlayersForGame called on CLF");
    }
}
