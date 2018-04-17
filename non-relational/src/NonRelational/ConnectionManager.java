package NonRelational;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.interfaces.persistence.IConnectionManager;

import java.io.File;
import java.io.FileReader;

public class ConnectionManager implements IConnectionManager {

    private final String usersFolderPath = "jsonDB" + File.separator + "users";
    private final String gamesFolderPath = "jsonDB" + File.separator + "games";
    @Override
    public void openConnection() {
        File usersFolder = new File(usersFolderPath);
        File gamesFolder = new File(gamesFolderPath);

        usersFolder.mkdirs();
    }

    @Override
    public void closeConnection() {

    }
}