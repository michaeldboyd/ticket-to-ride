package e.mboyd6.tickettoride.Views.Interfaces;

/**
 * Created by jonathanlinford on 2/2/18.
 */

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;

import java.util.ArrayList;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */

public interface IBoardFragment extends IGameActivityFragment {
    void updateBoard(Game game);
    /** When the game is started, let me know what train cards I was dealt as my starting hand.
     * Let me know which destination cards are possible for me to pick at the very beginning. **/
    void enterGame(ArrayList<TrainCard> trainCardsReceived, ArrayList<DestinationCard> initialDestinationCards);

    /** Called downwards FROM the UI when all actions are completed. **/
    void completeTurn();

    /** Called upwards ON the UI when a new turn is started. If it's not the player's own ID, then they are locked out
     * of actions. **/
    void onUpdateTurn(String PlayerID);

    /** Called downwards FROM the UI when the Autoplay button is pressed. **/
    void autoplay();

    /** Called downwards FROM the UI when a player selects a combination of face-up cards and cards from the deck to draw. **/
    void drawTrainCards(int index1, int index2, int numberFromDeck);

    /** Called upwards ON the UI when a player successfully draws train cards. A toast is displayed. The updateBoard method
     * is called on the CardDrawerState and the new face-up cards are displayed.**/
    void receiveTrainCards(ArrayList<TrainCard> trainCardsReceived);

    /** Called downwards FROM the UI when a player draws destination cards.**/
    void drawDestinationCards();

    /** Called upwards ON the UI when DestinationCard Cards have been successfully drawn.**/
    void receiveDestinationCards(ArrayList<DestinationCard> destinationCards);

    /** Called downwards FROM the UI when a player chooses which destination cards to keep**/
    void chooseDestinationCards(ArrayList<DestinationCard> chosen, ArrayList<DestinationCard> discarded);

    /** Called downwards FROM the UI when a player claims a route **/
    void claimRoute(Route route);

    /** Called upwards ON the UI to notify a player when they've successfully claimed a route **/
    void receiveRouteClaimed(String routeName);
}