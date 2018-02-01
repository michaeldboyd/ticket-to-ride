package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.facades.ILobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLobbyFacade implements ILobbyFacade {

  private static ClientLobbyFacade _instance = new ClientLobbyFacade();
  private ClientLobbyFacade() {}

  public static ClientLobbyFacade instance() { return _instance; }

  @Override
  public void createGame() {

  }

  @Override
  public Game[] getGames() {
    return new Game[0];
  }

  @Override
  public boolean joinGame(String gameID) {
    return false;
  }

  @Override
  public void startGame(String gameID) {

  }

  @Override
  public Player[] getPlayersForGame(String gameID) {
    return new Player[0];
  }
}
