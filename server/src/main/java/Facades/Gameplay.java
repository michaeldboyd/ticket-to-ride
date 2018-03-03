package Facades;

import com.example.sharedcode.interfaces.IServerGamplayFacade;
import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;

public class Gameplay implements IServerGamplayFacade{
    private static Gameplay ourInstance = new Gameplay();

    public static Gameplay getInstance() {
        return ourInstance;
    }

    private Gameplay() {
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

    }

    @Override
    public void playTrainCard(String authToken, String gameID, String playerID, TrainCard card) {

    }

    @Override
    public void drawDestinationCard(String authToken, String gameID, String playerID) {

    }

    @Override
    public void discardDestinationCard(String authToken, String gameID, String playerID, DestinationCard card) {

    }

    @Override
    public void placeTrainCars(String authToken, String gameID, String playerID, int numCars, Route route) {

    }

    @Override
    public void getGameHistory(String authToken, String gameID) {

    }
}
