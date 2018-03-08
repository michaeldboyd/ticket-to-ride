package e.mboyd6.tickettoride.Presenters;

import android.widget.Button;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.DestinationDeck;
import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.Score;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainCardDeck;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.Proxies.GameplayProxy;
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

public class GamePresenter implements IGamePresenter, Observer {

    protected IGameActivityFragment gameActivityFragment;

    public GamePresenter(IBoardFragment boardFragment) {
        gameActivityFragment = boardFragment;
        ClientModel.getInstance().addObserver(this);
    }

    public GamePresenter(IHandFragment handFragment) {
        gameActivityFragment = handFragment;
        ClientModel.getInstance().addObserver(this);
    }

    public GamePresenter(IScoreFragment scoreFragment) {
        gameActivityFragment = scoreFragment;
        ClientModel.getInstance().addObserver(this);
    }

    public GamePresenter(IHistoryFragment historyFragment) {
        gameActivityFragment = historyFragment;
        ClientModel.getInstance().addObserver(this);
    }

    public String getCurrentPlayer() {
        return ClientModel.getInstance().getPlayerName();
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
        else if (gameActivityFragment instanceof IScoreFragment) {
            ((IScoreFragment) gameActivityFragment).updateScore(currentGame.getPlayers());
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
            ((IBoardFragment) gameActivityFragment).onUpdateTurn(ClientModel.getInstance().getCurrentGame().getCurrentTurnPlayerID());
    }

    @Override
    public void autoplay() {
        // Communicate with the server to tell them that autoplay has been pressed
    }

    @Override
    public void drawTrainCards(int index1, int index2, int numberFromDeck) {
        // Communicate with the server to tell them that trainCards have been drawn
        FaceUpDeck faceUpDeck = ClientModel.getInstance().getCurrentGame().getFaceUpDeck();
        TrainCardDeck trainCardDeck = ClientModel.getInstance().getCurrentGame().getTrainCardDeck();
        Map<Integer, Integer> hand = ClientModel.getInstance().getCurrentPlayer().getHand();

        if (index1 != -1) {
            int card1 = faceUpDeck.get(index1);
            try {
                faceUpDeck.set(index1, trainCardDeck.drawCard());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Card1 value: " + card1);
            int amount1;
            if (hand.get(card1) != null) {
                amount1 = hand.get(card1);
                amount1++;
                hand.put(card1, amount1);
            } else {
                hand.put(card1, 1);
            }
        }
        if (index2 != -1) {
            int card2 = faceUpDeck.get(index2);
            try {
                faceUpDeck.set(index2, trainCardDeck.drawCard());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Card1 value: " + card2);
            int amount2;
            if (hand.get(card2) != null) {
                amount2 = hand.get(card2);
                amount2++;
                hand.put(card2, amount2);
            } else {
                hand.put(card2, 1);
            }
        }

        for(int i = 0; i < numberFromDeck; i++) {
            try {
                int card = trainCardDeck.drawCard();
                if (hand.get(card) != null) {
                    int amount = hand.get(card);
                    amount++;
                    hand.put(card, amount);
                } else {
                    hand.put(card, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        updateBoard();
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
    public ArrayList<DestinationCard> drawDestinationCards() {
        // Tell the server that the client wants to draw destination cards
        ArrayList<DestinationCard> result = new ArrayList<>();
        result.add(ClientModel.getInstance().getCurrentGame().getDestinationDeck().drawCard());
        result.add(ClientModel.getInstance().getCurrentGame().getDestinationDeck().drawCard());
        result.add(ClientModel.getInstance().getCurrentGame().getDestinationDeck().drawCard());
        return result;
    }

    /** Called upwards ON the UI when DestinationCard Cards have been successfully drawn.**/
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

        Game currentGame = ClientModel.getInstance().getCurrentGame();

        // Find the current player object
        Player currentPlayer = ClientModel.getInstance().getCurrentPlayer();

        if (currentPlayer != null) {
            currentPlayer.setDestinationCards(chosen);


                // Put the discarded cards at the bottom of the destination deck
                DestinationDeck destinationDeck = currentGame.getDestinationDeck();
                for (DestinationCard card :
                        discarded) {
                    destinationDeck.returnDiscarded(card);
                }

                int index = currentGame.getPlayers().indexOf(currentPlayer);

                currentGame.getPlayers().get(index).getDestinationCards().addAll(chosen);
        }
    }

    @Override
    public void claimRoute(Route route) {
        // Tell the server that the client has clicked on a route to claim/**/
        Game currentGame = ClientModel.getInstance().getCurrentGame();

        // Find the current player object
        Player currentPlayer = ClientModel.getInstance().getCurrentPlayer();

        if (currentPlayer != null) {

            currentGame.getRoutesClaimed().put(route, currentPlayer);

            Score score = new Score();
            score.setPoints(currentPlayer.getScore().getPoints() + score.computePoints(route));
            score.setTrains(currentPlayer.getTrains() - route.getNumberTrains());
            score.setCards(currentPlayer.removeFromHand(route));
            for(int i = 0; i < route.getNumberTrains(); i++) {
                currentGame.getTrainDiscardDeck().returnDiscarded(route.getTrainType());
            }
            score.setRoutes(currentPlayer.getDestinationCards().size());
            currentPlayer.setScore(score);

            String authToken = ClientModel.getInstance().getAuthToken();
            GameplayProxy.getInstance().claimRoute(authToken, currentGame.getGameID(), currentPlayer, currentGame.getRoutesClaimed());
        }
    }

    /** Called upwards ON the UI to notify a player when they've successfully claimed a route **/
    @Override
    public void receiveRouteClaimed(String routeName) {
        if (gameActivityFragment instanceof IBoardFragment) {
            updateBoard();
            ((IBoardFragment) gameActivityFragment).receiveRouteClaimed(routeName);
        }
    }

    @Override
    public void detachView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void enter(Button serverOnButton) {

    }

    @Override
    public void exit() {
        detachView();
    }

    @Override
    public Player getCurrentPlayerObject() {
        return ClientModel.getInstance().getCurrentPlayer();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
