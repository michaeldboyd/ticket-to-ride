package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ViewFlipper;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerStartGame extends CardDrawerState {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void enter(Context context, BoardFragment boardFragment, ViewFlipper viewFlipper, DrawerSlider drawerSlider, GamePresenter gamePresenter) {
        viewFlipper.setDisplayedChild(2);
        drawerSlider.open();
        drawerSlider.setLocked(true);
        boardFragment.setUILocked(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void exit(Context context, BoardFragment boardFragment, ViewFlipper viewFlipper, DrawerSlider drawerSlider, GamePresenter gamePresenter) {
        drawerSlider.setLocked(false);
        boardFragment.setUILocked(false);
    }
}