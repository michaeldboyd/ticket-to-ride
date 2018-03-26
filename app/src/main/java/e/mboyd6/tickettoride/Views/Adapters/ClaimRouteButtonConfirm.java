package e.mboyd6.tickettoride.Views.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainType;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;
import e.mboyd6.tickettoride.Views.Fragments.BoardIdle;
import e.mboyd6.tickettoride.Views.Fragments.BoardSelecting;

/**
 * Created by hunte on 3/4/2018.
 */

public class ClaimRouteButtonConfirm extends ClaimRouteButtonState {

    int wildCardsToUse = 0;

    public void enter(BoardFragment boardFragment, Button claimRouteButton) {
        claimRouteButton.setVisibility(View.VISIBLE);
        claimRouteButton.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.thumbs_up, 0);
        claimRouteButton.setText(R.string.claim_route_button_confirm);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(BoardFragment boardFragment) {

        if (boardFragment.getBoardState().getClass().equals(BoardSelecting.class)
                && selectedRoute != null) {
            if (selectedRoute.getNumberTrains() > player.getHand().get(selectedRoute.getTrainType())) {
                int min = selectedRoute.getNumberTrains() - player.getHand().get(selectedRoute.getTrainType());
                int max = player.getHand().get(TrainType.LOCOMOTIVE) > selectedRoute.getNumberTrains() ? selectedRoute.getNumberTrains() : player.getHand().get(TrainType.LOCOMOTIVE);
                wildCardDialog(boardFragment, min, max);
            } else {
                claimRoute(boardFragment, wildCardsToUse);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setRoute(BoardFragment boardFragment, Route route, Player player) {
        this.player = player;
        if (route == null) {
            boardFragment.setClaimRouteButtonState(new ClaimRouteButtonSelecting());
        } else {
            selectedRoute = route;
        }
    }

    private void wildCardDialog(final BoardFragment boardFragment, int min, int max) {
        if(!boardFragment.isAdded())
            return;

        final Dialog d = new Dialog(boardFragment.getActivity());
        d.setTitle("You have wildcards");
        d.setContentView(R.layout.dialog_wildcard);
        Button useWildcards = (Button) d.findViewById(R.id.dialog_use_wildcards);
        Button cancel = (Button) d.findViewById(R.id.dialog_cancel_wildcards);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(max); // max value 100
        np.setMinValue(min);   // min value 0
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                wildCardsToUse = newVal;
            }
        });
        useWildcards.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                claimRoute(boardFragment, wildCardsToUse);
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                wildCardsToUse = 0;
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void claimRoute(BoardFragment boardFragment, int howManyWildcardsToUse) {
        boardFragment.setBoardState(new BoardIdle());
        boardFragment.claimRoute(selectedRoute, howManyWildcardsToUse);
        boardFragment.setClaimRouteButtonState(new ClaimRouteButtonIdle());
    }
}
