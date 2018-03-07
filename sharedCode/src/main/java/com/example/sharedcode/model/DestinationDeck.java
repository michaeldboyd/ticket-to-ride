package com.example.sharedcode.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ali on 2/24/2018.
 */

public class DestinationDeck extends ArrayList<DestinationCard> {

    DestinationDeck(){

    }

    public void shuffleDeck(){
        Collections.shuffle(this);
    }

    //check params for this
    public DestinationCard drawCard(){
        // remove the first card on the deck, and return it to the user.
        if(size() > 0) {
            return remove(0);
        } else {
            return null;
        }

    }

    //check params for this
    public void returnDiscarded(DestinationCard card){
        // add the discarded card to the end of the deck.
        add(card);
    }

}
