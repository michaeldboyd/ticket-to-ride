package e.mboyd6.tickettoride.Views.Adapters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

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
            boardFragment.setBoardState(new BoardIdle());
            boardFragment.claimRoute(selectedRoute.getName());
            boardFragment.setClaimRouteButtonState(new ClaimRouteButtonIdle());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setRoute(BoardFragment boardFragment, Route route) {
        if (route == null) {
            boardFragment.setClaimRouteButtonState(new ClaimRouteButtonSelecting());
        }
    }
}
