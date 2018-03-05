package e.mboyd6.tickettoride.Views.Adapters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;
import e.mboyd6.tickettoride.Views.Fragments.BoardIdle;
import e.mboyd6.tickettoride.Views.Fragments.BoardSelecting;

/**
 * Created by hunte on 3/4/2018.
 */

public class ClaimRouteButtonMissing extends ClaimRouteButtonState {
    public void enter(BoardFragment boardFragment, Button claimRouteButton) {
        claimRouteButton.setVisibility(View.INVISIBLE);
    }
}
