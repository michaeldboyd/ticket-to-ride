package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.DestinationDeck;
import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainCardDeck;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public interface IServerGameplayFacade {
    void discardDestinationCard(String authToken, String gameID, Player player, DestinationDeck discardCards);
    void claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed);
    void updateTrainCards(String authToken, String gameID, Player player, FaceUpDeck faceUpDeck, TrainCardDeck trainCardDeck, TrainCardDeck trainDiscardDeck);
    void updateDestinationCards(String authToken, String gameID, Player player, DestinationDeck destinationDeck);
    void endGameEarly(String authToken, String gameID);
}
