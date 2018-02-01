package com.example.server;

import com.example.sharedcode.interfaces.ILobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLobbyFacade implements ILobbyFacade {

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
