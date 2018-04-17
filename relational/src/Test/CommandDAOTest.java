package Test;

import Relational.CommandDAO;
import com.example.sharedcode.communication.Command;

import java.util.ArrayList;

public class CommandDAOTest {

    public static void main(String args[]) {
        Command command = new Command("test", "test", "test", null, null);
        CommandDAO dao = new CommandDAO();
        dao.storeGameCommand(command, "test");
        ArrayList<Command> commands = dao.getCommands("test");

        dao.clearGameCommands("test");
    }
}
