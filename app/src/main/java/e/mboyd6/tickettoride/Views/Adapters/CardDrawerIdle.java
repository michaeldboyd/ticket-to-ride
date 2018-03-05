package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.widget.ViewFlipper;

import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerIdle extends CardDrawerState {

    public void enter(Context context, BoardFragment boardFragment, ViewFlipper viewFlipper) {
        viewFlipper.setDisplayedChild(0);
    }
}
