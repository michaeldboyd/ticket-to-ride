package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.Game;


/**
 * Created by jonathanlinford on 3/2/18.
 */

public interface IClientGamplayFacade {
    void claimedRoute(String gameID, String playerID);
    void drewTrainCard(String gameID, String playerID);
    void discardedTrainCard(String gameID, String playerID);
    void drewDestinationCard(String gameID, String playerID);
    void discardedDestinationCard(String gameID, String playerID);
    void placedTrainCars(String gameID, String playerID);
    void historyUpdated(String gameID, String historyItem);
    void newPlayerTurn(String gameID, String playerID);
    void updateGame(Game game, String message);
    void endGame(Game game, String message);
}
