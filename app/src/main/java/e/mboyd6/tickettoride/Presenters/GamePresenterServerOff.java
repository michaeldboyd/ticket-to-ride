package e.mboyd6.tickettoride.Presenters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Button;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.TrainCard;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IBoardFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHandFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHistoryFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IScoreFragment;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class GamePresenterServerOff extends GamePresenter {

    public GamePresenterServerOff(IBoardFragment boardFragment) {
        super(boardFragment);
    }

    public GamePresenterServerOff(IHandFragment handFragment) {
        super(handFragment);
    }

    public GamePresenterServerOff(IScoreFragment scoreFragment) {
        super(scoreFragment);
    }

    public GamePresenterServerOff(IHistoryFragment historyFragment) {
        super(historyFragment);
    }

    @Override
    public void enter(Button serverOnButton) {
        // Poll the server to see if the game exists. if the game does not exist, transition back into
        // normal GamePresenter state.
        serverOnButton.setBackgroundResource(R.drawable.button_red_bg);
        serverOnButton.setText(R.string.server_off);
    }
}
