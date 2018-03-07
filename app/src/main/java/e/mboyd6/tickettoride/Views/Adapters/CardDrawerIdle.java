package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.view.View;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.Game;

import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class CardDrawerIdle extends CardDrawerState {

    @Override
    public void enter(Context context, BoardFragment boardFragment, View layout, ViewFlipper viewFlipper){
        viewFlipper.setDisplayedChild(0);
    }
}
