package e.mboyd6.tickettoride.Presenters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Button;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

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
    public void enterGame(ArrayList<TrainCard> trainCardsReceived, ArrayList<DestinationCard> initialDestinationCards) {
        super.enterGame(trainCardsReceived, initialDestinationCards);
        String myName = ClientModel.getInstance().getPlayerName();
        ClientModel.getInstance().getCurrentGame().getHistory().add(myName + " entered the game.");
        updateBoard();
    }

    @Override
    public void completeTurn() {
        super.completeTurn();
        String myName = ClientModel.getInstance().getPlayerName();
        ClientModel.getInstance().getCurrentGame().getHistory().add(myName + " finished their turn.");
        updateBoard();
    }

    @Override
    public void onUpdateTurn() {
        super.onUpdateTurn();
    }

    @Override
    public void autoplay() {
        super.autoplay();
    }

    @Override
    public void drawTrainCards(int index1, int index2, int numberFromDeck) {
        super.drawTrainCards(index1, index2, numberFromDeck);
        String myName = ClientModel.getInstance().getPlayerName();
        ClientModel.getInstance().getCurrentGame().getHistory().add(myName + " drew train cards.");

        updateBoard();
    }

    @Override
    public ArrayList<DestinationCard> drawDestinationCards() {
        String myName = ClientModel.getInstance().getPlayerName();
        ClientModel.getInstance().getCurrentGame().getHistory().add(myName + " drew destination cards.");
        updateBoard();
        return super.drawDestinationCards();

    }

    @Override
    public void claimRoute(Route route) {
        super.claimRoute(route);
        String myName = ClientModel.getInstance().getPlayerName();
        ClientModel.getInstance().getCurrentGame().getHistory().add(myName + " claimed a route.");
        updateBoard();
    }

    @Override
    public void enter(Button serverOnButton) {
        // Poll the server to see if the game exists. if the game does not exist, transition back into
        // normal GamePresenter state.
        serverOnButton.setBackgroundResource(R.drawable.button_red_bg);
        serverOnButton.setText(R.string.server_off);
        String myName = ClientModel.getInstance().getPlayerName();
        if (ClientModel.getInstance().getCurrentGame() != null)
            ClientModel.getInstance().getCurrentGame().getHistory().add(myName + " entered server off mode.");
        updateBoard();
    }

    @Override
    public void exit() {
        super.exit();
        String myName = ClientModel.getInstance().getPlayerName();
        ClientModel.getInstance().getCurrentGame().getHistory().add(myName + " exited the game.");
        updateBoard();


    }

}