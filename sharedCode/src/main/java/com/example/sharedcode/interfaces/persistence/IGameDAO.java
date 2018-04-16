package com.example.sharedcode.interfaces.persistence;

import com.example.sharedcode.model.Game;

import java.util.List;

public interface IGameDAO {
     Game getGame(String gameID);
     List<Game> getAllGames();
     String addGame(Game game);
     boolean updateGame(String gameID, Game game);
     boolean removeGame(String gameID);
}
