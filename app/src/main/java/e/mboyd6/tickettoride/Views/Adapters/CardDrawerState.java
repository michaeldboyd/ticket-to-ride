package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainCardDeck;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerState {
    public Game game;
    public FaceUpDeck faceUpDeck;

    public CardDrawerState() {
    }

    public void enter(Context context, BoardFragment boardFragment, View layout, ViewFlipper viewFlipper, DrawerSlider drawerSlider) {}
    public void exit(Context context, BoardFragment boardFragment, View layout, ViewFlipper viewFlipper, DrawerSlider drawerSlider) {}

    public void updateBoard(Game game) {
        this.game = game;
        this.faceUpDeck = game.getFaceUpDeck();
        reDrawUI();
    }
    public void updateFaceUpCards(ArrayList<TrainCard> faceUpCards) {}
    public void receiveDestinationCards() {}
    public void reDrawUI() {}
}
