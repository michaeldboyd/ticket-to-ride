package com.example.sharedcode.interfaces.persistence;

import com.example.sharedcode.model.Game;

public interface IGameDAO {
     String addGame(Game game);
     boolean updateGame(String gameID, Game game);
     boolean removeGame(String gameID);
}
