package com.example.sharedcode.facades;

import com.example.sharedcode.communication.CommandResult;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerFacade implements ILobbyFacade, ILoginFacade {

  /*
    Login will check if the user is currently in the Model's user list, and if not, add the user.
   */
  //TODO: Probably should be a singleton eventually

  @Override
  public CommandResult login(String username, String password) {

    return null;
  }

  @Override
  public CommandResult register(String username, String password) {
    return null;
  }

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
