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

public class GamePresenterServerOn extends GamePresenter {

    public GamePresenterServerOn(IBoardFragment boardFragment) {
        super(boardFragment);
    }

    public GamePresenterServerOn(IHandFragment handFragment) {
        super(handFragment);
    }

    public GamePresenterServerOn(IScoreFragment scoreFragment) {
        super(scoreFragment);
    }

    public GamePresenterServerOn(IHistoryFragment historyFragment) {
        super(historyFragment);
    }

    public Player getCurrentPlayer() {
        return ClientModel.getInstance().getCurrentPlayer();
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return super.getPlayers();
    }

    @Override
    public void updateBoard() {
        super.updateBoard();
    }

    @Override
    public void enterGame(ArrayList<TrainCard> trainCardsReceived, ArrayList<DestinationCard> initialDestinationCards) {
        super.enterGame(trainCardsReceived, initialDestinationCards);
    }

    @Override
    public void completeTurn() {
        super.completeTurn();
        // Communicate with the server to tell them that the turn is complete
    }

    @Override
    public void onUpdateTurn() {
        super.onUpdateTurn();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void autoplay() {
        super.autoplay();
        // If autoplay is pressed, it will transition out of GamePresenterServerOn
        if (gameActivityFragment instanceof IBoardFragment) {
            BoardFragment currentFragment = (BoardFragment) gameActivityFragment;
            currentFragment.setGamePresenterState(new GamePresenterServerOff(currentFragment));
        }
    }

    @Override
    public void drawTrainCards(int index1, int index2, int numberFromDeck) {
        super.drawTrainCards(index1, index2, numberFromDeck);
        // Communicate with the server to tell them that trainCards have been drawn
    }

    @Override
    public void receiveTrainCards(ArrayList<TrainCard> trainCardsReceived) {
        super.receiveTrainCards(trainCardsReceived);
    }

    @Override
    public ArrayList<DestinationCard> drawDestinationCards() {
        return super.drawDestinationCards();
        // Tell the server that the client wants to draw destination cards
    }

    @Override
    public void receiveDestinationCards(ArrayList<DestinationCard> destinationCards) {
        super.receiveDestinationCards(destinationCards);
    }

    @Override
    public void chooseDestinationCards(ArrayList<DestinationCard> chosen, ArrayList<DestinationCard> discarded) {
        super.chooseDestinationCards(chosen, discarded);
        // Tell the server that the client has chosen which destination cards to keep and which ones
        // to discard to the bottom of the deck
    }

    @Override
    public void claimRoute(String routeName) {
        super.claimRoute(routeName);
        // Tell the server that the client has clicked on a route to claim
    }

    @Override
    public void receiveRouteClaimed(String routeName) {
        super.receiveRouteClaimed(routeName);
    }

    @Override
    public void enter(Button serverOnButton) {
        // Poll the server to see if the game exists. if the game does not exist, transition back into
        // normal GamePresenter state.
        serverOnButton.setBackgroundResource(R.drawable.button_green_bg);
        serverOnButton.setText(R.string.server_on);
    }
}