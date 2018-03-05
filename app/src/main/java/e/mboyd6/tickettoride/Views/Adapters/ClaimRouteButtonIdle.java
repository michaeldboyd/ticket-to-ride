package e.mboyd6.tickettoride.Views.Adapters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Button;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;
import e.mboyd6.tickettoride.Views.Fragments.BoardIdle;
import e.mboyd6.tickettoride.Views.Fragments.BoardSelecting;

/**
 * Created by hunte on 3/4/2018.
 */

public class ClaimRouteButtonIdle extends ClaimRouteButtonState {
    public void enter(BoardFragment boardFragment, Button claimRouteButton) {
        claimRouteButton.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icon_claim, 0);
        claimRouteButton.setText(R.string.claim_route);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(BoardFragment boardFragment) {

        if (boardFragment.getBoardState().getClass().equals(BoardIdle.class) &&
                boardFragment.isMyTurn()) {
            boardFragment.setBoardState(new BoardSelecting());
            boardFragment.getmGamePresenter().updateBoard();
            boardFragment.setClaimRouteButtonState(new ClaimRouteButtonSelecting());
        } else {
            boardFragment.setBoardState(new BoardIdle());
            boardFragment.getmGamePresenter().updateBoard();
        }
    }
}
