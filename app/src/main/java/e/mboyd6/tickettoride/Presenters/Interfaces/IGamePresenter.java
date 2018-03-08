package e.mboyd6.tickettoride.Presenters.Interfaces;

import android.widget.Button;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.TrainCard;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public interface IGamePresenter {
    /** Called UPWARDS from Model **/
    void updateBoard();

    /** A way to get the data of the current player without the UI calling the model directly **/
    Player getCurrentPlayer();

    /** A way to get the list of players in the game **/
    ArrayList<Player> getPlayers();

    /** Called UPWARDS from Model **/
    void enterGame(ArrayList<TrainCard> trainCardsReceived, ArrayList<DestinationCard> initialDestinationCards);

    /** Called DOWNWARDS from Presenter **/
    void completeTurn();

    /** Called UPWARDS from Model **/
    void onUpdateTurn();

    /** Called DOWNWARDS from Presenter **/
    void autoplay();

    /** Called DOWNWARDS from Presenter **/
    void drawTrainCards(int index1, int index2, int numberFromDeck);

    /** Called UPWARDS from Model **/
    void receiveTrainCards(ArrayList<TrainCard> trainCards);

    /** Called DOWNWARDS from Presenter **/
    ArrayList<DestinationCard> drawDestinationCards();

    /** Called UPWARDS from Model **/
    void receiveDestinationCards(ArrayList<DestinationCard> destinationCards);

    /** Called DOWNWARDS from Presenter **/
    void chooseDestinationCards(ArrayList<DestinationCard> chosen, ArrayList<DestinationCard> discarded);

    /** Called DOWNWARDS from Presenter **/
    void claimRoute(String routeName);

    /** Called UPWARDS from Model **/
    void receiveRouteClaimed(String routeName);

    void detachView();

    void enter(Button serverOnButton);

    void exit();
}
