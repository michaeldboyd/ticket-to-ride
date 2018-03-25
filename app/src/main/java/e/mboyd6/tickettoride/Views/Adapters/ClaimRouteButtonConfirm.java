package e.mboyd6.tickettoride.Views.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;
import e.mboyd6.tickettoride.Views.Fragments.BoardIdle;
import e.mboyd6.tickettoride.Views.Fragments.BoardSelecting;

/**
 * Created by hunte on 3/4/2018.
 */

public class ClaimRouteButtonConfirm extends ClaimRouteButtonState {
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
                wildCardDialog(boardFragment);
            } else {
                claimRoute(boardFragment, 0);
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

    private void wildCardDialog(final BoardFragment boardFragment) {
        if(!boardFragment.isAdded())
            return;
        boardFragment.getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(boardFragment.getActivity());
        builder.setTitle("You have wildcards");
        builder.setNegativeButton("Don't use", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //fill with stuff
                claimRoute(boardFragment, 0);
            }
        });

        builder.setPositiveButton("Use", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //fill with stuff
                claimRoute(boardFragment, 0);
            }
        });
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void claimRoute(BoardFragment boardFragment, int howManyWildcardsToUse) {
        boardFragment.setBoardState(new BoardIdle());
        boardFragment.claimRoute(selectedRoute, howManyWildcardsToUse);
        boardFragment.setClaimRouteButtonState(new ClaimRouteButtonIdle());
    }
}
