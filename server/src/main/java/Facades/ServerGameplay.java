package Facades;

import Communication.SocketManager;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerGameplayFacade;
import com.example.sharedcode.model.*;
import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;
import java.util.Map;

public class ServerGameplay implements IServerGameplayFacade {
    private static ServerGameplay ourInstance = new ServerGameplay();

    public static ServerGameplay getInstance() {
        return ourInstance;
    }

    private ServerGameplay() {
    }

    private final String CLASS_NAME = "e.mboyd6.tickettoride.Facades.ClientGameplay";
    public static void _startGame(String authToken, String gameID) {
        ourInstance.startGame(authToken, gameID);
    }

    public static void _claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed) {
        ourInstance.claimRoute(authToken, gameID, player, routesClaimed);
    }

    public static void _drawTrainCards(String authToken, String gameID, String playerID) {
        //ourInstance.drawTrainCards(authToken, gameID, playerID);
    }

    public static void _playTrainCard(String authToken, String gameID, String playerID, TrainCard card) {
        //ourInstance.playTrainCard(authToken, gameID, playerID, card);
    }

    public static void _drawDestinationCard(String authToken, String gameID, String playerID) {
        //ourInstance.drawDestinationCard(authToken, gameID, playerID);
    }

    public static void _discardDestinationCard(String authToken, String gameID, String playerID, DestinationCard card) {
        //ourInstance.discardDestinationCard(authToken, gameID, playerID, card);
    }

    public static void _placeTrainCars(String authToken, String gameID, String playerID, int numCars, Route route) {
        //ourInstance.placeTrainCars(authToken, gameID, playerID, numCars, route);
    }

    public static void _getGameHistory(String authToken, String gameID) {
        ourInstance.getGameHistory(authToken, gameID);
    }


    // this hsould be called by one person, and should send out an update to everyone
    @Override
    public void startGame(String authToken, String gameID) {
        //initialize game and send cards to each player
        String message = "";
        Game game = new Game(); // send back a new game if there's an error, just so we don't worry
        // about error handling
        if(ServerModel.instance().getGames().containsKey(gameID)){
            game = ServerModel.instance().getGames().get(gameID);

            // initilize game.



            ServerModel.instance().getGames().put(gameID, game);

        } else message = "Game not found on server";

        String[] paramTypes = {game.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};
        Command command = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_updateGame", paramTypes, paramValues);

        SocketManager.instance().notifyPlayersInGame(gameID, command);
    }


    @Override
    public void claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed) {

    }

    @Override
    public void drawTrainCards(String authToken, String gameID, Player player, FaceUpDeck faceUpDeck, TrainCardDeck trainCardDeck, TrainCardDeck trainDiscardDeck) {

    }

    @Override
    public void drawDestinationCard(String authToken, String gameID, Player player, DestinationDeck destinationDeck) {

    }

    @Override
    public void getGameHistory(String authToken, String gameID) {

    }
}

