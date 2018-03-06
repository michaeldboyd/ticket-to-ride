package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public interface IServerGamplayFacade {
    void startGame(String authToken, String gameID);
    void claimRoute(String authToken, String gameID, String playerID, String city1, String city2);
    void drawTrainCard(String authToken, String gameID, String playerID);
    void playTrainCard(String authToken, String gameID, String playerID, TrainCard card);
    void drawDestinationCard(String authToken, String gameID, String playerID);
    void discardDestinationCard(String authToken, String gameID, String playerID, DestinationCard card);
    void placeTrainCars(String authToken, String gameID, String playerID, int numCars, Route route);
    void getGameHistory(String authToken, String gameID);
}
