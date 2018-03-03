package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public interface IServerGamplayFacade {
    void startGame(String gameID);
    void claimRoute(String gameID, String playerID);
    void drawTrainCard(String gameID, String playerID);
    void playTrainCard(String gameID, String playerID, TrainCard card);
    void drawDestinationCard(String gameID, String playerID);
    void discardDestinationCard(String gameID, String playerID, DestinationCard card);
    void placeTrainCars(String gameID, String playerID, int numCars, Route route) ;
    void getGameHistory(String gameID);
}
