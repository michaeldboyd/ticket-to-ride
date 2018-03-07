package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.DestinationDeck;
import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainCardDeck;

import java.util.Map;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public interface IServerGamplayFacade {
    void startGame(String authToken, String gameID);
    void claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed);
    void drawTrainCards(String authToken, String gameID, Player player, FaceUpDeck faceUpDeck, TrainCardDeck trainCardDeck, TrainCardDeck trainDiscardDeck);
    void drawDestinationCard(String authToken, String gameID, Player player, DestinationDeck destinationDeck);
    void getGameHistory(String authToken, String gameID);
}
