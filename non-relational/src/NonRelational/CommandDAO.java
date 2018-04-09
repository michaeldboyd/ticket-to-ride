package NonRelational;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.interfaces.persistence.ICommandDAO;

import java.util.List;

public class CommandDAO implements ICommandDAO {
    @Override
    public void storeGameCommand(Command command, String gameID) {

    }

    @Override
    public List<Command> getGameCommands(String gameID) {
        return null;
    }

    @Override
    public boolean clearGameCommands(String gameID) {
        return false;
    }
}