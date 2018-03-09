package e.mboyd6.tickettoride.Model;

import android.content.Context;
import android.widget.Toast;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.Score;
import com.example.sharedcode.model.TrainType;

import java.lang.reflect.Array;
import java.util.ArrayList;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.Views.Activities.GameActivity;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/8/2018.
 */

public class Autoplayer {
    //****** GETTERS & SETTERS *****//
    private static Autoplayer ourInstance = new Autoplayer();

    public static Autoplayer getInstance() {
        return ourInstance;
    }

    public int step = 0;
    public ClientModel model = ClientModel.getInstance();
    public Game game = model.getCurrentGame();
    public IGamePresenter presenter;

    public void autoplay(Context context, BoardFragment boardFragment) {
        String stepText = "";
        Score score = game.getPlayers().get(0).getScore();
        presenter = boardFragment.getmGamePresenter();

        switch(step) {
            case 0:
                // Changing score

                score = game.getPlayers().get(0).getScore();

                score.setPoints(123);

                presenter.updateBoard();

                stepText = "Step " + step + " - Player " + model.getCurrentPlayer().getName() + "'s points set to 123";
                break;
            case 1:
                //drawing cards test

                int count = 0;
                while(model.getCurrentPlayer().getHand().get(TrainType.BOX) < 5){
                    count += 2;
                    presenter.drawTrainCards(0, 1, 0);
                }

                stepText = "Step " + step + " - Drew an additional " + count + " cards from the deck";
                break;
            case 2:
                //Claiming route test

                Route route = new Route("Rosette", "Lucin", 2, TrainType.BOX);

                presenter.claimRoute(route);

                stepText = "Step " + step + " - Claimed route from Rosette to Lucin\n" +
                        "Player score is now updated to " + score.getPoints() + " points.\n" +
                        "Number of train cards for player " + model.getCurrentPlayer().getName() + " is now" + score.getCards() + ".\n" +
                        "Number of train cards in deck is " + game.getTrainCardDeck().size() + ".\n" +
                        "Number of trains is now " + score.getTrains() + ".";
                break;
            case 3:
                //drawing face up cards test

                presenter.drawTrainCards(0, 1, 0);

                stepText = "Step " + step + " - Drew 2 face-up cards\n" +
                        "Number of face-up cards is " + game.getFaceUpDeck().size() + ".\n" +
                        "Number of cards in face-down train cards deck is " + game.getTrainCardDeck().size() + ".";
                break;
            case 4:
                ArrayList<DestinationCard> results = presenter.drawDestinationCards();
                ArrayList<DestinationCard> discard = new ArrayList<>();
                discard.add(results.remove(2));
                presenter.chooseDestinationCards(results, discard);

                stepText = "Step " + step + " - Drew 3 destination cards.\n" +
                        "Kept 2\n" +
                        "discarded 1\n" +
                        "Number of destination cards in deck is " + game.getDestinationDeck().size() + ".";
                break;
            case 5:
                //change player turn
                game.setCurrentTurnPlayerID(game.getPlayers().get(1).getName());
                presenter.onUpdateTurn();

                stepText = "Step " + step + " - Changed player's turn to player " + game.getPlayers().get(1).getName() + ".";
                step = -1;
                break;
        }

        Toast.makeText(context, stepText, Toast.LENGTH_LONG)
                .show();
        step++;
    }

    public void autoAutoplay(Context context, BoardFragment boardFragment) {
        for(int i = 0; i < 6; i++) {
            autoplay(context, boardFragment);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
