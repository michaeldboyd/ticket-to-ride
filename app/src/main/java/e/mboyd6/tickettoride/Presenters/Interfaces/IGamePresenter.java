package e.mboyd6.tickettoride.Presenters.Interfaces;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.TrainCard;

import java.util.ArrayList;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public interface IGamePresenter {
    /** Called UPWARDS from Model **/
    void updateBoard();

    /** Called UPWARDS from Model **/
    void enterGame(ArrayList<TrainCard> trainCardsReceived, ArrayList<DestinationCard> initialDestinationCards);

    /** Called DOWNWARDS from Presenter **/
    void completeTurn();

    /** Called UPWARDS from Model **/
    void onNewTurn(String PlayerID);

    /** Called DOWNWARDS from Presenter **/
    void autoplay();

    /** Called DOWNWARDS from Presenter **/
    void drawTrainCards(int index1, int index2, int numberFromDeck);

    /** Called UPWARDS from Model **/
    void receiveTrainCards(ArrayList<TrainCard> trainCards);

    /** Called DOWNWARDS from Presenter **/
    void drawDestinationCards();

    /** Called UPWARDS from Model **/
    void receiveDestinationCards(ArrayList<DestinationCard> destinationCards);

    /** Called DOWNWARDS from Presenter **/
    void chooseDestinationCards(ArrayList<DestinationCard> chosen, ArrayList<DestinationCard> discarded);

    /** Called DOWNWARDS from Presenter **/
    void claimRoute(String routeName);

    /** Called UPWARDS from Model **/
    void receiveRouteClaimed(String routeName);

}
