package NonRelational;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.interfaces.persistence.IConnectionManager;

import java.io.File;
import java.io.FileReader;

public class ConnectionManager implements IConnectionManager {

    private final String usersFolderPath = "users";
    private final String gamesFolderPath = "games";
    private final String commandsFolderPath = "commands";
    @Override
    public void openConnection() {
        File usersFolder = new File(usersFolderPath);
        File gamesFolder = new File(gamesFolderPath);
        File commandsFolder = new File(commandsFolderPath);

        usersFolder.mkdirs();
        gamesFolder.mkdirs();
        commandsFolder.mkdirs();
    }

    @Override
    public void closeConnection() {

    }
}