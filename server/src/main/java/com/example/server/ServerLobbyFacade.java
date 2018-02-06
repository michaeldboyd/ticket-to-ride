package com.example.server;

import com.example.sharedcode.communication.CommandResult;
import com.example.sharedcode.interfaces.ILobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import java.util.ArrayList;
import java.util.Map;

import Model.serverModel;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLobbyFacade implements ILobbyFacade {

    int playerLimit = 5;

  @Override
  public CommandResult createGame(String gameName) {
    //create commandresult
    if(serverModel.instance().games.containsKey(gameName)){

      //Commandresult.setMessage("Game already exists")

    }
    else{

        Game newGame = new Game();
        newGame.setGameID(gameName);
        serverModel.instance().games.put(gameName, newGame);

    }

    return null;
  }

  @Override
  public CommandResult getGames() {
     Map gameMap = serverModel.instance().games;

     return null;
  }

  @Override
  public CommandResult joinGame(String gameID, String username) {

      if(serverModel.instance().games.containsKey(gameID)){

          if(serverModel.instance().games.get(gameID).addPlayer(username)){

              //command

          }
          else{

              //error: "could not add player to game"
              // either to many players or player already in game. may want to specify

          }

      }
      return null;
  }

  @Override
    public CommandResult leaveGame(String gameID, String username){
      if(serverModel.instance().games.containsKey(gameID)){

          if(serverModel.instance().games.get(gameID).removePlayer(username)){

              //command

          }
          else{

              //error: "couldn't remove player because wasn't in game yet"

          }

      }
      return null;
    }


    @Override
  public void startGame(String gameID) {

  }

  @Override
  public CommandResult getPlayersForGame(String gameID) {
      //create commandresult
      if(serverModel.instance().games.containsKey(gameID)){

          ArrayList players = serverModel.instance().games.get(gameID).getPlayerIDs();

          //put arraylist in command and return

      }
      else{

        //error: game does not exist

      }

      return null;
  }
}
