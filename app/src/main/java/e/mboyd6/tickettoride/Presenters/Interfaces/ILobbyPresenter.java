package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public interface ILobbyPresenter {

    void logOut();
    void joinGame(String gameID);

    void createGame();

    //Observer Methods
    void updateGameList();


    void logoutResponse(String message);
}
