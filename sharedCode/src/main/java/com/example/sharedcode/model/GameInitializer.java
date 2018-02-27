package com.example.sharedcode.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 2/24/2018.
 */

public class GameInitializer {

    private static int DEST_DECK_NUM = 30;
    private static int TRAIN_CARD_DECK_NUM = 110;
    private static int NON_LOCOMOTIVE_CARD = 12;
    private static int LOCOMOTIVE_CARD = 14;

    public Game initializeGame(Game game){
        game.setDestinationDeck(initializeDestinationDeck());
        game.setTrainCardDeck(initializeTrainCardDeck());
        game.setFaceUpDeck(initializeFaceUpDeck());
        game.setRoutesClaimed(initializeRoutes());
        return game;
    }

    private DestinationDeck initializeDestinationDeck(){
        DestinationDeck deck = new DestinationDeck();

        return deck;
    }

    private TrainCardDeck initializeTrainCardDeck(){
        TrainCardDeck deck = new TrainCardDeck();
        return deck;
    }

    private Map<Route, Player> initializeRoutes(){
        Map<Route, Player> routes = new HashMap<>();
        return routes;
    }

    private FaceUpDeck initializeFaceUpDeck(){
       FaceUpDeck deck = new FaceUpDeck();
       return deck;
    }

}
