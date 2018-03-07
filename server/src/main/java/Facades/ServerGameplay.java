package Facades;

import com.example.sharedcode.interfaces.IServerGameplayFacade;
import com.example.sharedcode.model.*;

import java.util.Map;

public class ServerGameplay implements IServerGameplayFacade {
    private static ServerGameplay ourInstance = new ServerGameplay();

    public static ServerGameplay getInstance() {
        return ourInstance;
    }

    private ServerGameplay() {
    }

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


    @Override
    public void startGame(String authToken, String gameID) {

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

