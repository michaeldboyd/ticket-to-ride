package e.mboyd6.tickettoride.Presenters;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Button;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.DestinationDeck;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Communication.Proxies.GameplayProxy;
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

    public GamePresenterServerOn(IBoardFragment boardFragment, Activity activity) {
        super(boardFragment, activity);
    }

    public GamePresenterServerOn(IHandFragment handFragment, Activity activity) {
        super(handFragment, activity);
    }

    public GamePresenterServerOn(IScoreFragment scoreFragment, Activity activity) {
        super(scoreFragment, activity);
    }

    public GamePresenterServerOn(IHistoryFragment historyFragment, Activity activity) {
        super(historyFragment, activity);
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
            currentFragment.setGamePresenterState(new GamePresenterServerOff(currentFragment, null));
        }
    }

    @Override
    public ArrayList<Integer> drawTrainCards(int index1, int index2, int numberFromDeck) {
        ArrayList<Integer> result = super.drawTrainCards(index1, index2, numberFromDeck);
        // Communicate with the server to tell them that trainCards have been drawn
        String authToken = ClientModel.getInstance().getAuthToken();
        Game currentGame = ClientModel.getInstance().getCurrentGame();
        Player currentPlayer = ClientModel.getInstance().getCurrentPlayer();

        GameplayProxy.getInstance().updateTrainCards(authToken,
                currentGame.getGameID(),
                currentPlayer,
                currentGame.getFaceUpDeck(),
                currentGame.getTrainCardDeck(),
                currentGame.getTrainDiscardDeck());

        return result;
    }

    @Override
    public ArrayList<DestinationCard> drawDestinationCards() {
        return super.drawDestinationCards();
    }

    @Override
    public void chooseDestinationCards(ArrayList<DestinationCard> chosen, ArrayList<DestinationCard> discarded) {
        super.chooseDestinationCards(chosen, discarded);
        // Only tell the Server to discard a card if there is one
        if (discarded != null) {

            Game currentGame = ClientModel.getInstance().getCurrentGame();
            Player currentPlayer = ClientModel.getInstance().getCurrentPlayer();

            String authToken = ClientModel.getInstance().getAuthToken();
            DestinationDeck discardDeck = (DestinationDeck)discarded;
            GameplayProxy.getInstance().discardDestinationCard(authToken, currentGame.getGameID(), currentPlayer, discardDeck);
        }
    }

    @Override
    public void claimRoute(Route routeName, int howManyWildcardsToUse) {
        super.claimRoute(routeName, howManyWildcardsToUse);
        // Tell the server that the client has clicked on a route to claim
        String authToken = ClientModel.getInstance().getAuthToken();
        Game currentGame = ClientModel.getInstance().getCurrentGame();
        Player currentPlayer = ClientModel.getInstance().getCurrentPlayer();

        GameplayProxy.getInstance().claimRoute(authToken, currentGame.getGameID(), currentPlayer, currentGame.getRoutesClaimed());
    }

    @Override
    public void enter(Button serverOnButton) {
        // Poll the server to see if the game exists. if the game does not exist, transition back into
        // normal GamePresenter state.
        serverOnButton.setBackgroundResource(R.drawable.button_green_bg);
        serverOnButton.setText(R.string.server_on);
    }

    @Override
    public void endGame() {
        super.endGame();

        String authToken = ClientModel.getInstance().getAuthToken();
        Game currentGame = ClientModel.getInstance().getCurrentGame();

        GameplayProxy.getInstance().endGameEarly(authToken, currentGame.getGameID());
    }
}
