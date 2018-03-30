package e.mboyd6.tickettoride.Views.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerDrawTrainCards extends CardDrawerState {

    private Context context;
    private ArrayList<ImageView> trainCardImages = new ArrayList<>();
    private Deque<Integer> selectedCards = new ArrayDeque<>();
    private int howManyDeckCards = 0;
    private TextView selectTrainCardsText;
    private Button DrawTrainCardsButton;
    private Button DrawDestinationCardsButton;
    private int maximumCards = 2;
    private BoardFragment boardFragment;
    private GamePresenter gamePresenter;

    private int selectedCard1Index = -1;
    private int selectedCard2Index = -1;
    private int numFromDeck = 0;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void enter(final Context context, BoardFragment boardFragment, ViewFlipper viewFlipper, DrawerSlider drawerSlider, GamePresenter gamePresenter) {

        this.context = context;
        this.boardFragment = boardFragment;
        game = game == null ? boardFragment.getLatestLoadedGame() : game;
        faceUpDeck = game.getFaceUpDeck() == null ? new FaceUpDeck() : game.getFaceUpDeck();
        this.gamePresenter = gamePresenter;

        ImageView trainCard1 = viewFlipper.findViewById(R.id.draw_train_cards_train_card_1);
        ImageView trainCard2 = viewFlipper.findViewById(R.id.draw_train_cards_train_card_2);
        ImageView trainCard3 = viewFlipper.findViewById(R.id.draw_train_cards_train_card_3);
        ImageView trainCard4 = viewFlipper.findViewById(R.id.draw_train_cards_train_card_4);
        ImageView trainCard5 = viewFlipper.findViewById(R.id.draw_train_cards_train_card_5);
        ImageView trainCardDeck = viewFlipper.findViewById(R.id.draw_train_cards_train_card_deck);

        trainCardImages.add(trainCard1);
        trainCardImages.add(trainCard2);
        trainCardImages.add(trainCard3);
        trainCardImages.add(trainCard4);
        trainCardImages.add(trainCard5);
        trainCardImages.add(trainCardDeck);

        for(int i = 0; i < trainCardImages.size(); i++) {
            final int i_final = i;
            trainCardImages.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTrainCardSelect(i_final);
                }
            });
        }

        selectTrainCardsText = viewFlipper.findViewById(R.id.select_train_cards_text);

        DrawTrainCardsButton = viewFlipper.findViewById(R.id.draw_cards_button);
        DrawTrainCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDrawTrainCardsButton();
            }
        });
        DrawDestinationCardsButton = viewFlipper.findViewById(R.id.switch_to_destination);
        DrawDestinationCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDrawDestinationCardsButton();
            }
        });



        reDrawUI();
        viewFlipper.setDisplayedChild(1);
    }

    public void onTrainCardSelect(int index) {
        //NOTE: -1 = not selected

        boolean isWildcard = false;
        if(index < faceUpDeck.size()) {
            isWildcard = faceUpDeck.get(index) == TrainType.LOCOMOTIVE;
        }
        boolean isFromDeck = index >= faceUpDeck.size();

        boolean wildcardSelected = (selectedCard1Index != -1 && // is selected
                selectedCard1Index != 5 && // Not deak card
                faceUpDeck.get(selectedCard1Index) == TrainType.LOCOMOTIVE) ||
                (selectedCard2Index != -1 && // is selected
                selectedCard2Index != 5 && // Not deak card
                faceUpDeck.get(selectedCard2Index) == TrainType.LOCOMOTIVE);

        if (isFromDeck){
            if(howManyDeckCards == 0 || howManyDeckCards == 1){
                if(selectedCard1Index == -1 && selectedCard2Index == -1){
                    selectedCard1Index = index;
                } else if(selectedCard1Index == -1 && selectedCard2Index != -1){
                    selectedCard1Index = selectedCard2Index;
                    selectedCard2Index = index;
                } else if (selectedCard1Index != -1 && selectedCard2Index == -1){
                    selectedCard2Index = index;
                } else if(selectedCard1Index != -1 && selectedCard2Index != -1){
                    selectedCard1Index = selectedCard2Index;
                    selectedCard2Index = index;
                }
            } else { // two selected from deck
                selectedCard1Index = selectedCard2Index;
                selectedCard2Index = -1;
            }

            if (wildcardSelected) {
                selectedCard1Index = index;
                selectedCard2Index = -1;
            }

        } else if (selectedCard1Index == index){
            selectedCard1Index = selectedCard2Index;
            selectedCard2Index = -1;
        } else if (selectedCard2Index == index){    // reselecting 2
            selectedCard2Index = -1;
        } else{ // choosing new card
            if (isWildcard || wildcardSelected) {
                selectedCard1Index = index;
                selectedCard2Index = -1;
            } else{
                if(selectedCard1Index == -1 && selectedCard2Index == -1){
                    selectedCard1Index = index;
                } else if(selectedCard1Index == -1 && selectedCard2Index != -1){
                    selectedCard1Index = selectedCard2Index;
                    selectedCard2Index = index;
                } else if (selectedCard1Index != -1 && selectedCard2Index == -1){
                    selectedCard2Index = index;
                } else if(selectedCard1Index != -1 && selectedCard2Index != -1){
                    selectedCard1Index = selectedCard2Index;
                    selectedCard2Index = index;
                }
            }
        }

        howManyDeckCards = 0;
        howManyDeckCards += (selectedCard1Index == 5) ? 1 : 0;
        howManyDeckCards += (selectedCard2Index == 5) ? 1 : 0;

        selectedCards = new ArrayDeque<>();

        if (selectedCard1Index != -1){
            selectedCards.add(selectedCard1Index);
        }
        if (selectedCard2Index != -1){
            selectedCards.add(selectedCard2Index);
        }
        reDrawUI();
    }

    @Override
    public void reDrawUI() {
        for(int i = 0; i < faceUpDeck.size(); i++) {
            setCardBackground(i, selectedCards.contains(i), faceUpDeck.get(i));
        }

        setDeckBackground();
        enableDisbableUIElements();
    }

    public void setCardBackground(int index, boolean selected, int trainType) {
        int idle_bg = 0;
        int selected_bg = 0;

        switch(trainType) {
            case TrainType.BOX:
                idle_bg = R.drawable.card_box;
                selected_bg = R.drawable.card_box_selected;
                break;
            case TrainType.CABOOSE:
                idle_bg = R.drawable.card_caboose;
                selected_bg = R.drawable.card_caboose_selected;
                break;
            case TrainType.COAL:
                idle_bg = R.drawable.card_coal;
                selected_bg = R.drawable.card_coal_selected;
                break;
            case TrainType.FREIGHT:
                idle_bg = R.drawable.card_freight;
                selected_bg = R.drawable.card_freight_selected;
                break;
            case TrainType.HOPPER:
                idle_bg = R.drawable.card_hopper;
                selected_bg = R.drawable.card_hopper_selected;
                break;
            case TrainType.LOCOMOTIVE:
                idle_bg = R.drawable.card_locomotive;
                selected_bg = R.drawable.card_locomotive_selected;
                break;
            case TrainType.PASSENGER:
                idle_bg = R.drawable.card_passenger;
                selected_bg = R.drawable.card_passenger_selected;
                break;
            case TrainType.REEFER:
                idle_bg = R.drawable.card_reefer;
                selected_bg = R.drawable.card_reefer_selected;
                break;
            case TrainType.TANKER:
                idle_bg = R.drawable.card_tanker;
                selected_bg = R.drawable.card_tanker_selected;
                break;
        }
        trainCardImages.get(index).setImageResource(selected ? selected_bg : idle_bg);
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
        trainCardImages.get(trainCardImages.size() - 1).setImageResource(background);
    }

    public void applyLocomotiveRulesIfPresent() {
        if (locomotivePresent()) {
            selectTrainCardsText.setText(R.string.select_train_cards_text_locomotive_rules);
        }
    }

    private boolean locomotiveSelected() {
        if (faceUpDeck.size() == 0 ||
                faceUpDeck.size() < selectedCard1Index + 1 ||
                faceUpDeck.size() < selectedCard2Index + 1)
            return false;
        return (selectedCard1Index != -1 && faceUpDeck.get(selectedCard1Index) == TrainType.LOCOMOTIVE) ||
                (selectedCard2Index != -1 && faceUpDeck.get(selectedCard2Index) == TrainType.LOCOMOTIVE);
    }

    private boolean locomotivePresent() {
        for (Integer i : faceUpDeck) {
            if (i == TrainType.LOCOMOTIVE) {
                return true;
            }
        }
        return false;
    }

    public void enableDisbableUIElements() {

        int amountOfCards = (selectedCard1Index == 5 ? -1 : 0) + (selectedCard2Index == 5 ? -1 : 0) + selectedCards.size() + howManyDeckCards;

        if (amountOfCards < 2 && !locomotiveSelected()) {
            selectTrainCardsText.setText(R.string.select_two_face_up_cards);
            DrawTrainCardsButton.setClickable(false);
            DrawTrainCardsButton.setAlpha(0.5f);
            DrawDestinationCardsButton.setClickable(true);
            DrawDestinationCardsButton.setAlpha(1f);
        } else {
            String trainCardsText = String.valueOf(amountOfCards) + " cards are selected";
            selectTrainCardsText.setText(trainCardsText);
            DrawTrainCardsButton.setClickable(true);
            DrawTrainCardsButton.setAlpha(1f);
            DrawDestinationCardsButton.setClickable(false);
            DrawDestinationCardsButton.setAlpha(0.5f);
        }
        applyLocomotiveRulesIfPresent();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onDrawTrainCardsButton() {
        //Toast.makeText(context, "onDrawTrainCardsButton()", Toast.LENGTH_SHORT).show();
        int index1 = selectedCards.size() > 0 ? selectedCards.pop() : -1;
        int index2 = selectedCards.size() > 0 ? selectedCards.pop() : -1;
        ArrayList<Integer> cardsAdded = boardFragment.getmGamePresenter().drawTrainCards(index1 == 5 ? -1 : index1, index2 == 5 ? -1 : index2, howManyDeckCards);
        selectedCards.clear();
        howManyDeckCards = 0;
        selectedCard1Index = -1;
        selectedCard2Index = -1;

        reDrawUI();
        boardFragment.completeTurn();

        StringBuilder sb = new StringBuilder();
        sb.append("Added ");
        if (cardsAdded.size() > 0)
            sb.append(cardsAdded.size() < 2 ? TrainType.typeToName(cardsAdded.get(0)) + " (" + TrainType.typeToColor(cardsAdded.get(0)) + ")" + " card" : TrainType.typeToName(cardsAdded.get(0)) + " (" + TrainType.typeToColor(cardsAdded.get(0)) + ")" + " and " + TrainType.typeToName(cardsAdded.get(1)) + " (" + TrainType.typeToColor(cardsAdded.get(1)) + ")" + " cards");
        sb.append(" to hand");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Drew train cards!");
        builder.setMessage(sb.toString());
        builder.setNegativeButton(null, null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onDrawDestinationCardsButton() {
        Toast.makeText(context, "onDrawDestinationCardsButton()", Toast.LENGTH_SHORT).show();
        boardFragment.setCardDrawerState(new CardDrawerDrawDestinationCards());
    }
}
