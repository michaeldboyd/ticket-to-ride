package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainCardDeck;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerAdapter {
    public CardDrawerAdapter() {}

    public void inflate(Context context, BoardFragment boardFragment, ViewFlipper viewFlipper) {}
    public void updateBoard(Game game) {}
    public void updateFaceUpCards(ArrayList<TrainCard> faceUpCards) {}
    public void receiveDestinationCards() {}
}
