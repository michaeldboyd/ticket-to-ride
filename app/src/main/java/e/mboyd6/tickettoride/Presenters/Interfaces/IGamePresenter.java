package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public interface IGamePresenter {

    void drawDestinationCards();
    void chooseDestinationCards();
    void drawTrainCards();
    void sendDestinationCards();
    void onNewTurn(String PlayerID);

    void enterGame(String PlayerID);
    void leaveGame(String PlayerID);
}
