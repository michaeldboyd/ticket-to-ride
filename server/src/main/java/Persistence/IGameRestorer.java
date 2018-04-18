package Persistence;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.User;

import java.util.List;

public interface IGameRestorer {

    void run();

    void initFromDB();

    List<Game> getGames();
    List<Command> getCommandsForGame(String gameID);
    void simulateGame(List<Command> commands);
    void pushGameToServerModel(Game game) throws Exception;
    void addCommandForGame(Command command, Game game);
}
