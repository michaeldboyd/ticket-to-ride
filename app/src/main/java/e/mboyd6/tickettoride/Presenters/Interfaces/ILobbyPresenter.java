package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public interface ILobbyPresenter {


    void updateGameList();

    void joinGame(String gameID);

    boolean createGame(String name, int numOfPlayers);


}
