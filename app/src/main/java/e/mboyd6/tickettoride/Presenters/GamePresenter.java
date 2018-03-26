package e.mboyd6.tickettoride.Presenters;

import android.app.Activity;
import android.widget.Button;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.DestinationDeck;
import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.Score;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainCardDeck;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Facades.ClientGameplay;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.Views.Activities.GameActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IBoardFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivityFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHandFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHistoryFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IScoreFragment;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class GamePresenter implements IGamePresenter, Observer {

    protected IGameActivityFragment gameActivityFragment;
    protected Activity activity;
    protected Game game = ClientModel.getInstance().getCurrentGame();

    public GamePresenter(IBoardFragment boardFragment, Activity activity) {
        gameActivityFragment = boardFragment;
        this.activity = activity;
        ClientModel.getInstance().addObserver(this);
    }

    public GamePresenter(IHandFragment handFragment, Activity activity) {
        gameActivityFragment = handFragment;
        this.activity = activity;
        ClientModel.getInstance().addObserver(this);
    }

    public GamePresenter(IScoreFragment scoreFragment, Activity activity) {
        gameActivityFragment = scoreFragment;
        this.activity = activity;
        ClientModel.getInstance().addObserver(this);
    }

    public GamePresenter(IHistoryFragment historyFragment, Activity activity) {
        gameActivityFragment = historyFragment;
        this.activity = activity;
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
        else if (gameActivityFragment instanceof IHistoryFragment) {
            ((IHistoryFragment) gameActivityFragment).updateHistory(currentGame.getHistory());
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
        if (gameActivityFragment instanceof IBoardFragment) {
            if (ClientModel.getInstance().getCurrentGame() != null) {
                ((IBoardFragment) gameActivityFragment).onUpdateTurn(ClientModel.getInstance().getCurrentGame().getCurrentTurnPlayerName());
            }
        }
    }

    @Override
    public void autoplay() {
        // Communicate with the server to tell them that autoplay has been pressed
    }

    @Override
    public ArrayList<Integer> drawTrainCards(int index1, int index2, int numberFromDeck) {
        ArrayList<Integer> result = new ArrayList<>();

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
                result.add(card1);
            } else {
                hand.put(card1, 1);
                result.add(card1);
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
                result.add(card2);
            } else {
                hand.put(card2, 1);
                result.add(card2);
            }
        }

        for(int i = 0; i < numberFromDeck; i++) {
            try {
                int card = trainCardDeck.drawCard();
                if (hand.get(card) != null) {
                    int amount = hand.get(card);
                    amount++;
                    hand.put(card, amount);
                    result.add(card);
                } else {
                    hand.put(card, 1);
                    result.add(card);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Update the score
        Player player = ClientModel.getInstance().getCurrentPlayer();
        Score score = player.getScore();
        score.setCards(player.cardsInHand());

        if(game.checkAndResolveTooManyWildcardsInFaceUp()){
            ((GameActivity) activity).sendToast("Too many locomotives in face up deck. They've all been discarded and face up was recreated.");
        }

        return result;
    }

    @Override
    public ArrayList<DestinationCard> getStartDestinationCards() {
        if (ClientModel.getInstance().getCurrentGame() != null &&
                ClientModel.getInstance().getCurrentPlayer() != null &&
                ClientModel.getInstance().getCurrentPlayer().getDestinationCards() != null)
        {
            return ClientModel.getInstance().getCurrentPlayer().getDestinationCards();
        }
        else return new ArrayList<DestinationCard>();
    }

    public void discardStartDestinationCards(ArrayList<DestinationCard> discardedCards) {
        if (ClientModel.getInstance().getCurrentGame() != null &&
                ClientModel.getInstance().getCurrentPlayer() != null &&
                ClientModel.getInstance().getCurrentPlayer().getDestinationCards() != null)
        {
            ClientModel.getInstance().getCurrentPlayer().getDestinationCards().removeAll(discardedCards);
            String myName = ClientModel.getInstance().getPlayerName();
            ClientModel.getInstance().getCurrentGame().getHistory().add(myName + " discarded " + discardedCards.size() + " destination cards.");
            //GameplayProxy.getInstance().discardDestinationCard(
                //    ClientModel.getInstance().getAuthToken(), ClientModel.getInstance().getCurrentGame().getGameID(),
              //      ClientModel.getInstance().getCurrentPlayer()
            //);
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


    @Override
    public void chooseDestinationCards(ArrayList<DestinationCard> chosen, DestinationCard discarded) {
        // Tell the server that the client has chosen which destination cards to keep and which ones
        // to discard to the bottom of the deck
        Game currentGame = ClientModel.getInstance().getCurrentGame();

        // Find the current player object
        Player currentPlayer = ClientModel.getInstance().getCurrentPlayer();

        if (currentPlayer != null) {
            currentPlayer.setDestinationCards(chosen);


            // Put the discarded cards at the bottom of the destination deck
            if (discarded != null) {
                DestinationDeck destinationDeck = currentGame.getDestinationDeck();
                destinationDeck.returnDiscarded(discarded);
            }

            // Update the score
            Player player = ClientModel.getInstance().getCurrentPlayer();
            Score score = player.getScore();
            score.setRoutes(player.getDestinationCards().size());
        }
    }

    @Override
    public void claimRoute(Route route, int howManyWildcardsToUse) {
        // Tell the server that the client has clicked on a route to claim/**/
        Game currentGame = ClientModel.getInstance().getCurrentGame();

        // Find the current player object
        Player currentPlayer = ClientModel.getInstance().getCurrentPlayer();

        if (currentPlayer != null) {
            currentGame.getRoutesClaimed().put(route, currentPlayer);

            int numTrainsBefore = currentPlayer.getTrains();
            int numTrainsUsed = route.getNumberTrains();
            int numTrainsAfter = numTrainsBefore - numTrainsUsed;

            Score score = currentPlayer.getScore();
            score.addPoints(route);
            score.setTrains(numTrainsAfter);
            score.setCards(currentPlayer.removeFromHand(route));

            currentPlayer.setTrains(numTrainsAfter);

            for(int i = 0; i < route.getNumberTrains(); i++) {
                currentGame.getTrainDiscardDeck().returnDiscarded(route.getTrainType());
            }

            // Increment the current score.routes by 1
            score.setRoutes(currentPlayer.getDestinationCards().size());
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
    public boolean isMyTurn() {
        String currentTurnPlayerID = ClientModel.getInstance().getCurrentGame().getCurrentTurnPlayerName();
        return (getCurrentPlayer() != null && getCurrentPlayer().equals(currentTurnPlayerID));
    }

    @Override
    public void finalRound(){
        if(gameActivityFragment instanceof IBoardFragment){
            ((IBoardFragment) gameActivityFragment).setFinalRound();
        }
    }

    @Override
    public void gameFinished(){
        List<Player> playerListByScore = ClientModel.getInstance().getCurrentGame().getPlayerListByScore();

        if (activity instanceof GameActivity) {
            ((IGameActivity) activity).changeToVictoryActivity(playerListByScore);

            ((GameActivity) activity).sendToast("GAME ENDED!");
        }
    }

    @Override
    public void endGame() {

    }

    @Override
    public void update(Observable observable, Object o) {

        Assert.assertEquals(o.getClass(), UpdateArgs.class);
        UpdateArgs args = (UpdateArgs) o;

        switch(args.type){
            case FINAL_ROUND:
                finalRound();
                break;
            case GAME_DONE:
                gameFinished();
                break;
            case NEW_PLAYER_TURN: // TODO: What is this?
                // NOTE: this is no longer used... We go through the Game_updated enum now.
                onUpdateTurn();
                break;
            case GAME_INITIALIZED:
                // THIS IS NOT AN ERROR, THIS IS ACTUAL SYNTAX
                // So that we can have the same functionality for GAME_INITIALIZED AND GAME_UPDATED
            case GAME_UPDATED:
                updateBoard();
                onUpdateTurn();
                break;
            case SERVER_DISCONNECT:
                //TO JONNY, WITH LOVE, FROM MICHAEL <3
                break;
        }
    }
}
