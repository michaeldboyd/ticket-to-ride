package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerDrawTrainCards extends CardDrawerState {

    private ArrayList<ImageView> trainCardImages = new ArrayList<>();
    private Deque<TrainCard> selectedCards = new ArrayDeque<>();
    private int howManyDeckCards = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void enter(final Context context, BoardFragment boardFragment, View layout, ViewFlipper viewFlipper) {
        game = boardFragment.getLatestLoadedGame();
        faceUpDeck = game.getFaceUpDeck() == null ? new FaceUpDeck() : game.getFaceUpDeck();
        Toast.makeText(context, "Entered CardDrawerDrawTrainCards state", Toast.LENGTH_SHORT).show();
        viewFlipper.setDisplayedChild(1);
        this.trainCardImages = boardFragment.trainCardImages;
        for(int i = 0; i < trainCardImages.size(); i++) {
            final int i_final = i;
            trainCardImages.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTrainCardSelect(i_final);
                    Toast.makeText(context, "Clicked" + i_final, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void onTrainCardSelect(int index) {
        if (index < 6) {
            if (!selectedCards.contains(faceUpDeck.get(index))) {
                selectedCards.add(faceUpDeck.get(index));
                setCardBackground(index, true, (faceUpDeck.get(index)).getTrainCarType());
            } else {
                selectedCards.remove(faceUpDeck.get(index));
                setCardBackground(index, false, (faceUpDeck.get(index)).getTrainCarType());
            }
        } else {
            if (howManyDeckCards == 2) {
                howManyDeckCards = 0;
            } else if (howManyDeckCards == 1) {
                howManyDeckCards = 2;
                if (selectedCards.size() == 1) {
                    selectedCards.pop();
                }
            } else if (howManyDeckCards == 0) {
                howManyDeckCards = 1;
                if (selectedCards.size() == 2) {
                    selectedCards.pop();
                }
            }
            setDeckBackground();
        }
    }

    public void setCardBackground(int index, boolean selected, int trainType) {
        int selectedBg = 0;
        int normalBg = 0;

        switch(trainType) {
            case TrainType.BOX:
                selectedBg = R.drawable.card_box;
                normalBg = R.drawable.card_selected;
                break;
            case TrainType.CABOOSE:
                selectedBg = R.drawable.card_caboose;
                normalBg = R.drawable.card_selected;
                break;
            case TrainType.COAL:
                selectedBg = R.drawable.card_coal;
                normalBg = R.drawable.card_selected;
                break;
            case TrainType.FREIGHT:
                selectedBg = R.drawable.card_freight;
                normalBg = R.drawable.card_selected;
                break;
            case TrainType.HOPPER:
                selectedBg = R.drawable.card_hopper;
                normalBg = R.drawable.card_selected;
                break;
            case TrainType.LOCOMOTIVE:
                selectedBg = R.drawable.card_locomotive;
                normalBg = R.drawable.card_selected;
                break;
            case TrainType.PASSENGER:
                selectedBg = R.drawable.card_passenger;
                normalBg = R.drawable.card_selected;
                break;
            case TrainType.REEFER:
                selectedBg = R.drawable.card_reefer;
                normalBg = R.drawable.card_selected;
                break;
            case TrainType.TANKER:
                selectedBg = R.drawable.card_tanker;
                normalBg = R.drawable.card_selected;
                break;
        }
        trainCardImages.get(index).setBackgroundResource(selected ? selectedBg : normalBg);
    }

    public void setDeckBackground() {
        int background = 0;
        switch(howManyDeckCards) {
            case 0:
                background = R.drawable.card_deck_0;
                break;
            case 1:
                background = R.drawable.card_deck_1;
                break;
            case 2:
                background = R.drawable.card_deck_2;
                break;
        }
        trainCardImages.get(6).setBackgroundResource(background);
    }
}
