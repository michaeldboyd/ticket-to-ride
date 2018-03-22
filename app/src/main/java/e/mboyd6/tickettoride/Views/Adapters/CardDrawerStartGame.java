package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerStartGame extends CardDrawerState {

    private BoardFragment boardFragment;
    private Context context;
    private Deque<Integer> selectedCards = new ArrayDeque<>();
    private ArrayList<DestinationCard> destinationCards = new ArrayList<>();
    private ArrayList<View> destinationCardViews = new ArrayList<>();
    private GamePresenter gamePresenter;
    private Button drawSelectedCardsButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void enter(Context context, BoardFragment boardFragment, ViewFlipper viewFlipper, DrawerSlider drawerSlider, GamePresenter gamePresenter) {
        this.context = context;
        this.boardFragment = boardFragment;
        game = game == null ? boardFragment.getLatestLoadedGame() : game;
        // TODO: This needs to be a method on the boardFragment that can be called to draw destinationCards
        destinationCards = new ArrayList<DestinationCard>();
        destinationCards.addAll(gamePresenter.getStartDestinationCards());
        this.gamePresenter = gamePresenter;

        viewFlipper.setDisplayedChild(2);
        drawerSlider.open();
        drawerSlider.setLocked(true);
        boardFragment.setUILocked(true);

        ArrayList<TextView> names = new ArrayList<>();
        ArrayList<TextView> values = new ArrayList<>();

        View destinationCard1 = viewFlipper.findViewById(R.id.destination_card_1);
        destinationCardViews.add(destinationCard1.findViewById(R.id.border));
        TextView destinationCard1Name = destinationCard1.findViewById(R.id.destination_name_value);
        names.add(destinationCard1Name);
        TextView destinationCard1Value = destinationCard1.findViewById(R.id.destination_amount_value);
        values.add(destinationCard1Value);

        View destinationCard2 = viewFlipper.findViewById(R.id.destination_card_2);
        destinationCardViews.add(destinationCard2.findViewById(R.id.border));
        TextView destinationCard2Name = destinationCard2.findViewById(R.id.destination_name_value);
        names.add(destinationCard2Name);
        TextView destinationCard2Value = destinationCard2.findViewById(R.id.destination_amount_value);
        values.add(destinationCard2Value);

        View destinationCard3 = viewFlipper.findViewById(R.id.destination_card_3);
        destinationCardViews.add(destinationCard3.findViewById(R.id.border));
        TextView destinationCard3Name = destinationCard3.findViewById(R.id.destination_name_value);
        names.add(destinationCard3Name);
        TextView destinationCard3Value = destinationCard3.findViewById(R.id.destination_amount_value);
        names.add(destinationCard3Value);

        if (destinationCards.size() >= 3) {

            names.get(0).setText(String.format("%s to %s", destinationCards.get(0).getStartCity(), destinationCards.get(0).getEndCity()));
            names.get(1).setText(String.format("%s to %s", destinationCards.get(1).getStartCity(), destinationCards.get(1).getEndCity()));
            names.get(2).setText(String.format("%s to %s", destinationCards.get(1).getStartCity(), destinationCards.get(2).getEndCity()));
        }
        for(int i = 0; i < destinationCardViews.size(); i++) {
            final int i_final = i;
            destinationCardViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDestinationCardSelect(i_final);
                }
            });
        }

        drawSelectedCardsButton = viewFlipper.findViewById(R.id.select_destination_cards);
        drawSelectedCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectDestinationCards();
            }
        });

        reDrawUI();
    }

    @Override
    public void reDrawUI() {
        for(int i = 0; i < destinationCardViews.size(); i++) {
            if (selectedCards.contains(i)) {
                destinationCardViews.get(i).setBackgroundResource(R.drawable.card_destination_selected);
            } else {
                destinationCardViews.get(i).setBackgroundResource(R.drawable.card_destination);
            }
        }

        if (selectedCards.size() >= 2) {
            drawSelectedCardsButton.setAlpha(1f);
        } else {
            drawSelectedCardsButton.setAlpha(0.5f);
        }
    }

    private void onDestinationCardSelect(int index) {
        if(!selectedCards.contains(index)) {
            selectedCards.add(index);
        } else {
            selectedCards.remove(index);
        }
        reDrawUI();
        //Toast.makeText(context, "Destination card selected: " + index, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSelectDestinationCards() {
        ArrayList<DestinationCard> chosen = new ArrayList<>();
        DestinationCard discarded = null;

        for(int i = 0; i < destinationCards.size(); i++) {
            if (selectedCards.contains(i))
                chosen.add(destinationCards.get(i));
            else
                discarded = destinationCards.get(i);
        }
        if (selectedCards.size() >= 2) {
            gamePresenter.chooseDestinationCard(chosen, discarded);
            boardFragment.completeTurn();
            if (gamePresenter.isMyTurn()) {
                boardFragment.setCardDrawerState(new CardDrawerDrawTrainCards());
            } else {
                boardFragment.setCardDrawerState(new CardDrawerIdle());
            }
        }

        gamePresenter.updateBoard();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void exit(Context context, BoardFragment boardFragment, ViewFlipper viewFlipper, DrawerSlider drawerSlider, GamePresenter gamePresenter) {
        drawerSlider.setLocked(false);
        boardFragment.setUILocked(false);
    }


}
