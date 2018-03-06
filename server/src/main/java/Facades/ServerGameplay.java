package Facades;

import com.example.sharedcode.interfaces.IServerGamplayFacade;
import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;

public class ServerGameplay implements IServerGamplayFacade{
    private static ServerGameplay ourInstance = new ServerGameplay();

    public static ServerGameplay getInstance() {
        return ourInstance;
    }

    private ServerGameplay() {
    }

    public static void _startGame(String authToken, String gameID) {
        ourInstance.startGame(authToken, gameID);
    }

    public static void _claimRoute(String authToken, String gameID, String playerID, String city1, String city2) {
        ourInstance.claimRoute(authToken, gameID, playerID, city1, city2);
    }

    public static void _drawTrainCard(String authToken, String gameID, String playerID) {
        ourInstance.drawTrainCard(authToken, gameID, playerID);
    }

    public static void _playTrainCard(String authToken, String gameID, String playerID, TrainCard card) {
        ourInstance.playTrainCard(authToken, gameID, playerID, card);
    }

    public static void _drawDestinationCard(String authToken, String gameID, String playerID) {
        ourInstance.drawDestinationCard(authToken, gameID, playerID);
    }

    public static void _discardDestinationCard(String authToken, String gameID, String playerID, DestinationCard card) {
        ourInstance.discardDestinationCard(authToken, gameID, playerID, card);
    }

    public static void _placeTrainCars(String authToken, String gameID, String playerID, int numCars, Route route) {
        ourInstance.placeTrainCars(authToken, gameID, playerID, numCars, route);
    }

    public static void _getGameHistory(String authToken, String gameID) {
        ourInstance.getGameHistory(authToken, gameID);
    }

    @Override
    public void startGame(String authToken, String gameID) {

    }

    @Override
    public void claimRoute(String authToken, String gameID, String playerID, String city1, String city2) {

    }

    @Override
    public void drawTrainCard(String authToken, String gameID, String playerID) {
        // TODO: - We need to handle two situations
            // 1. Drawing a card off the top
            // 2. Draw one of the 5 cards that are visible
                // If the card taken from visible cards is a locomotive, end turn
                // The second card cannot be a locomotive
    }

    @Override
    public void playTrainCard(String authToken, String gameID, String playerID, TrainCard card) {

    }

    @Override
    public void drawDestinationCard(String authToken, String gameID, String playerID) {
        // TODO: - Randomly select 3 destination cards and send them back to the client
            // Tell the client how many cards are left? -- or do we tell it *which* cards were drawn?
    }

    @Override
    public void discardDestinationCard(String authToken, String gameID, String playerID, DestinationCard card) {
        // TODO: - Put the specified DestinationCard in the 'discard pile'
    }

    @Override
    public void placeTrainCars(String authToken, String gameID, String playerID, int numCars, Route route) {
        /* TODO: - We need to have a master copy of the map on the server
            How are we going to store this?
            Do we mark each route as taken or not?
         */
    }

    @Override
    public void getGameHistory(String authToken, String gameID) {

    }
}
