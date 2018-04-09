package Relational;

import com.example.sharedcode.interfaces.persistence.IGameDAO;
import com.example.sharedcode.model.Game;

public class GameDAO implements IGameDAO {
    @Override
    public String addGame(Game game) {
        return null;
    }

    @Override
    public boolean updateGame(String gameID, Game game) {
        return false;
    }

    @Override
    public boolean removeGame(String gameID) {
        return false;
    }
}
