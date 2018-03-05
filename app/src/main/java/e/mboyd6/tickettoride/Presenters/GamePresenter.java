package e.mboyd6.tickettoride.Presenters;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.TrainCard;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.Views.Interfaces.IBoardFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivityFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHandFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHistoryFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IScoreFragment;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class GamePresenter implements IGamePresenter {

    private IGameActivityFragment gameActivityFragment;

    public GamePresenter(IBoardFragment boardFragment) {

        gameActivityFragment = boardFragment;
    }

    public GamePresenter(IHandFragment handFragment) {
        gameActivityFragment = handFragment;
    }

    public GamePresenter(IScoreFragment scoreFragment) {
        gameActivityFragment = scoreFragment;
    }

    public GamePresenter(IHistoryFragment historyFragment) {
        gameActivityFragment = historyFragment;
    }

    public Player getCurrentPlayer() {
        return ClientModel.getInstance().getCurrentPlayer();
    }

    @Override
    public ArrayList<Player> getPlayers() {
        if (ClientModel.getInstance().getCurrentGame() != null)
            return ClientModel.getInstance().getCurrentGame().getPlayers();
        else
            return null;
    }

    /** This method updates whatever presenter is active with relevant information
     * NOTE: This method is called by the Model, but it is also called by the UI
     * when a new Fragment is inflated, so that the View can fill its elements with
     * information for the first time.
     * NOTE: It is important that not only CurrentGame, but CurrentPlayer remains
     * up-to-date to give relevant information. **/
    @Override
    public void updateBoard() {
        Game currentGame = ClientModel.getInstance().getCurrentGame();
        if (gameActivityFragment instanceof IBoardFragment)
            ((IBoardFragment) gameActivityFragment).updateBoard(ClientModel.getInstance().getCurrentGame());
        else if (gameActivityFragment instanceof IHandFragment) {
            ((IHandFragment) gameActivityFragment).updateHand(ClientModel.getInstance().getCurrentPlayer());
        }
    }

    /** When the game is started, let me know what train cards I was dealt as my starting hand.
     * Let me know which destination cards are possible for me to pick at the very beginning. **/
    @Override
    public void enterGame(ArrayList<TrainCard> trainCardsReceived, ArrayList<DestinationCard> initialDestinationCards) {
        if (gameActivityFragment instanceof IBoardFragment)
            ((IBoardFragment) gameActivityFragment).enterGame(
                    trainCardsReceived,
                    initialDestinationCards);
    }

    @Override
    public void completeTurn() {
        // Communicate with the server to tell them that the turn is complete
    }

    /** Called upwards ON the UI when a new turn is started. If it's not the player's own ID, then they are locked out
     * of actions. **/
    @Override
    public void onUpdateTurn() {
        if (gameActivityFragment instanceof IBoardFragment)
            ((IBoardFragment) gameActivityFragment).onUpdateTurn(ClientModel.getInstance().getPlayerTurn());
    }

    @Override
    public void autoplay() {
        // Communicate with the server to tell them that autoplay has been pressed
    }

    @Override
    public void drawTrainCards(int index1, int index2, int numberFromDeck) {
        // Communicate with the server to tell them that trainCards have been drawn
    }

    /** Called upwards ON the UI when a player successfully draws train cards. A toast is displayed. The updateBoard method
     * is called on the CardDrawerState and the new face-up cards are displayed.**/
    @Override
    public void receiveTrainCards(ArrayList<TrainCard> trainCardsReceived) {
        if (gameActivityFragment instanceof IBoardFragment) {
            updateBoard();
            ((IBoardFragment) gameActivityFragment).receiveTrainCards(trainCardsReceived);
        }
    }

    @Override
    public void drawDestinationCards() {
        // Tell the server that the client wants to draw destination cards
    }

    /** Called upwards ON the UI when Destination Cards have been successfully drawn.**/
    @Override
    public void receiveDestinationCards(ArrayList<DestinationCard> destinationCards) {
        if (gameActivityFragment instanceof IBoardFragment) {
            updateBoard();
            ((IBoardFragment) gameActivityFragment).receiveDestinationCards(destinationCards);
        }
    }

    @Override
    public void chooseDestinationCards(ArrayList<DestinationCard> chosen, ArrayList<DestinationCard> discarded) {
        // Tell the server that the client has chosen which destination cards to keep and which ones
        // to discard to the bottom of the deck
    }

    @Override
    public void claimRoute(String routeName) {
        // Tell the server that the client has clicked on a route to claim
    }

    /** Called upwards ON the UI to notify a player when they've successfully claimed a route **/
    @Override
    public void receiveRouteClaimed(String routeName) {
        if (gameActivityFragment instanceof IBoardFragment) {
            updateBoard();
            ((IBoardFragment) gameActivityFragment).receiveRouteClaimed(routeName);
        }
    }
}
