package com.example.sharedcode.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Ali on 2/24/2018.
 */

public class TrainCardDeck extends ArrayList<Integer> implements Serializable {

    TrainCardDeck(){

    }

    public void shuffleDeck(){
        Collections.shuffle(this);
    }

    //check params for this
    public int drawCard() throws Exception {
        if(size() > 0)
            return remove(0);
        else
            throw new Exception("Train Card Deck should not be size 0");
    }

    //check params for this
    public void returnDiscarded(int card){
        add(card);
    }
}
