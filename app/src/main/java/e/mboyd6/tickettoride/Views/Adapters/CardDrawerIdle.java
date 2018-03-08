package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.TrainType;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerIdle extends CardDrawerState {

    private ArrayList<ImageView> trainCardImages = new ArrayList<>();
    private Context context;
    private BoardFragment boardFragment;
    private GamePresenter gamePresenter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void enter(Context context, BoardFragment boardFragment, ViewFlipper viewFlipper, DrawerSlider drawerSlider, GamePresenter gamePresenter){

        this.context = context;
        this.boardFragment = boardFragment;
        game = game == null ? boardFragment.getLatestLoadedGame() : game;
        faceUpDeck = game.getFaceUpDeck() == null ? new FaceUpDeck() : game.getFaceUpDeck();
        this.gamePresenter = gamePresenter;

        gamePresenter.updateBoard();
        ImageView trainCard1 = viewFlipper.findViewById(R.id.train_card_1);
        ImageView trainCard2 = viewFlipper.findViewById(R.id.train_card_2);
        ImageView trainCard3 = viewFlipper.findViewById(R.id.train_card_3);
        ImageView trainCard4 = viewFlipper.findViewById(R.id.train_card_4);
        ImageView trainCard5 = viewFlipper.findViewById(R.id.train_card_5);
        ImageView trainCardDeck = viewFlipper.findViewById(R.id.train_card_deck);

        trainCardImages.add(trainCard1);
        trainCardImages.add(trainCard2);
        trainCardImages.add(trainCard3);
        trainCardImages.add(trainCard4);
        trainCardImages.add(trainCard5);
        trainCardImages.add(trainCardDeck);

        reDrawUI();
        viewFlipper.setDisplayedChild(0);
    }

    @Override
    public void reDrawUI() {
        for(int i = 0; i < faceUpDeck.size(); i++) {
            setCardBackground(i, faceUpDeck.get(i));
        }
    }

    public void setCardBackground(int index, int trainType) {
        if (index >= trainCardImages.size())
            return;
        int idle_bg = 0;
        int selected_bg = 0;

        switch(trainType) {
            case TrainType.BOX:
                idle_bg = R.drawable.card_box;
                break;
            case TrainType.CABOOSE:
                idle_bg = R.drawable.card_caboose;
                break;
            case TrainType.COAL:
                idle_bg = R.drawable.card_coal;
                break;
            case TrainType.FREIGHT:
                idle_bg = R.drawable.card_freight;
                break;
            case TrainType.HOPPER:
                idle_bg = R.drawable.card_hopper;
                break;
            case TrainType.LOCOMOTIVE:
                idle_bg = R.drawable.card_locomotive;
                break;
            case TrainType.PASSENGER:
                idle_bg = R.drawable.card_passenger;
                break;
            case TrainType.REEFER:
                idle_bg = R.drawable.card_reefer;
                break;
            case TrainType.TANKER:
                idle_bg = R.drawable.card_tanker;
                break;
        }

        trainCardImages.get(index).setImageResource(idle_bg);
    }
}
