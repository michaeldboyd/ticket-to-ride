package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public interface ILobbyPresenter {

    // View -> Facade
    void logOut();

    void joinGame(String gameID);

    void createGame();

    // Model -> View
    void gameCreated(String response);

    void updateGameList();

    void logoutResponse(String message);

    void gameJoined(String message);

    void detachView();
}
