package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Player;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerDrawDestinationCards extends CardDrawerState {

    private BoardFragment boardFragment;
    private Context context;
    private ArrayList<DestinationCard> destinationCards;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void enter(Context context, BoardFragment boardFragment, ViewFlipper viewFlipper, DrawerSlider drawerSlider, Player currentPlayer) {
        this.context = context;
        this.boardFragment = boardFragment;
        game = game == null ? boardFragment.getLatestLoadedGame() : game;
        // TODO: This needs to be a method on the boardFragment that can be called to draw destinationCards
        destinationCards = currentPlayer.getDestinationCards() == null ? new ArrayList<DestinationCard>() : currentPlayer.getDestinationCards();
        
        viewFlipper.setDisplayedChild(2);
        drawerSlider.open();
        drawerSlider.setLocked(true);
        boardFragment.setUILocked(true);

        ArrayList<TextView> names = new ArrayList<>();
        ArrayList<TextView> values = new ArrayList<>();

        View destinationCard1 = viewFlipper.findViewById(R.id.destination_card_1);
        TextView destinationCard1Name = destinationCard1.findViewById(R.id.destination_name_value);
        names.add(destinationCard1Name);
        TextView destinationCard1Value = destinationCard1.findViewById(R.id.destination_amount_value);
        values.add(destinationCard1Value);
        
        View destinationCard2 = viewFlipper.findViewById(R.id.destination_card_2);
        TextView destinationCard2Name = destinationCard2.findViewById(R.id.destination_name_value);
        names.add(destinationCard2Name);
        TextView destinationCard2Value = destinationCard2.findViewById(R.id.destination_amount_value);
        values.add(destinationCard2Value);

        View destinationCard3 = viewFlipper.findViewById(R.id.destination_card_3);
        TextView destinationCard3Name = destinationCard3.findViewById(R.id.destination_name_value);
        names.add(destinationCard3Name);
        TextView destinationCard3Value = destinationCard3.findViewById(R.id.destination_amount_value);
        names.add(destinationCard3Value);

        names.get(0).setText(String.format("%s to %s", destinationCards.get(0).getStartCity(), destinationCards.get(0).getEndCity()));
        names.get(1).setText(String.format("%s to %s", destinationCards.get(1).getStartCity(), destinationCards.get(1).getEndCity()));
        names.get(2).setText(String.format("%s to %s", destinationCards.get(1).getStartCity(), destinationCards.get(2).getEndCity()));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void exit(Context context, BoardFragment boardFragment, View layout, ViewFlipper viewFlipper, DrawerSlider drawerSlider) {
        drawerSlider.setLocked(false);
        boardFragment.setUILocked(false);
    }


}
