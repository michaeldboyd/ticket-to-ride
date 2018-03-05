package e.mboyd6.tickettoride.Facades;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.model.ChatMessage;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.UpdateType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import e.mboyd6.tickettoride.Communication.Proxies.LobbyProxy;
import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLobby implements IClientLobbyFacade {

    private static ClientLobby lobbyFacade;

    public static ClientLobby instance() {
        if (lobbyFacade == null) {
            lobbyFacade = new ClientLobby();
        }

        return lobbyFacade;
    }


    public static void _createGameReceived(Game newGame) {
        instance().createGame(newGame);
    }

    public static void _updateGamesReceived(Game[] games, String message) {
        instance().updateGames(games, message);
    }

    public static void _joinGameReceived(String gameID, String playerID, String message) {
        instance().joinGame(gameID, playerID, message);
    }

    public static void _startGameReceived(String gameID, String message) {
        instance().startGame(gameID, message);
    }

    public static void _leaveGameReceived(String gameID, String message) {
        instance().leaveGame(gameID, message);
    }
    static void _getPlayersForGameReceived(String gameID, Player[] players, String message) {
        instance().getPlayersForGame(players, gameID, message);
    }




    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    //Must be called after a user creates a new game. this won't be called now
    @Override
    public void createGame(Game newGame) {
        UpdateType type = UpdateType.GAME_CREATED;
        boolean success = true; //no message on this command.. keeping logic just in case
        String message = "";
        //TODO all empty objects are parsed as null in json serialization. Reinstantiate.
        newGame.setChatMessages(new ArrayList<ChatMessage>());

        List<Game> games = ClientModel.getInstance().getGames();
        games.add(newGame);

        sendUpdate(type, success, message);

        String authToken = ClientModel.getInstance().getAuthToken();
        //join the game you just created
        LobbyProxy.instance()
                .joinGame(authToken, newGame.getGameID());

    }

    @Override
    public void updateGames(Game[] games, String message) {
        UpdateType type = UpdateType.GAME_LIST;
        boolean success = isSuccess(message);

        if(success)
        {
            ArrayList<Game> updatedGames = new ArrayList<>(Arrays.asList(games));
            ClientModel.getInstance().setGames(updatedGames);
        } //if it fails, we'll handle the error later on

        sendUpdate(type, success, message);
    }

    @Override
    public void joinGame(String gameID, String playerID, String message) {
        UpdateType type = UpdateType.GAME_JOINED;
        boolean success = isSuccess(message);

        if(success)
        {
            //join the game
            boolean joinedGame = joinGame(gameID);
            if(joinedGame) {
                ClientModel.getInstance().setPlayerID(playerID);

            } else message = "Game ID wasn't found correctly. Choose another game for now.";
        }

        sendUpdate(type, success, message);
    }

    //start the game
    @Override
    public void startGame(String gameID, String message) {
        UpdateType type = UpdateType.GAME_STARTED;
        boolean success = isSuccess(message);

        //just in case, we're going to double check you've joined the game (this was in the original login)
       if(success) {
           if(joinGame(gameID)) {
               message = "The game you're trying to start has the wrong ID. our deepest condolences.";
               success = false;
           }
       }

       sendUpdate(type, success, message);
    }

    //leave the game
    @Override
    public void leaveGame(String gameID, String message) {
        UpdateType type = UpdateType.GAME_LEFT;
        boolean success = isSuccess(message);
        if(success) {
            ClientModel.getInstance().setCurrentGame(null);
        }

        sendUpdate(type, success, message);
    }

    @Override
    public void getPlayersForGame(Player[] players, String gameID, String message) {
        //WHY WAS THIS HERE AGAIN?
    }

    @Override
    public void playerColorChanged(String gameID, String playerID, int color) {
        // Don't do anything
        // Game list will be updated automatically
    }

    private boolean isSuccess(String message){
        if(message == null || message.equals(""))
            return true;
        else return false;
    }

    private void sendUpdate(UpdateType type, boolean success, String error)
    {
        UpdateArgs args = new UpdateArgs(type, success, error);
        ClientModel.getInstance().sendUpdate(args);
    }


    private boolean joinGame(String gameID){
        ArrayList<Game> games = ClientModel.getInstance().getGames();
        if(games != null)
        {
            for(Game g: games){
                if(g.getGameID().equals(gameID)){
                    //GameID is set as currentGame
                   if(g.getChatMessages() == null)
                        g.setChatMessages(new ArrayList<ChatMessage>());
                    ClientModel.getInstance().setCurrentGame(g);
                    return true;
                }
            }
        }
        return false;
    }
}
