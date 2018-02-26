package com.example.sharedcode.model;

import java.util.Map;

/**
 * Created by Ali on 2/24/2018.
 */

public class GameInitializer {

    public Game initializeGame(Game game){
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

    /*private Map<Route, String> initializeRoutes(){

    }*/

    private FaceUpDeck initializeFaceUpDeck(){
       FaceUpDeck deck = new FaceUpDeck();
       return deck;
    }

}
