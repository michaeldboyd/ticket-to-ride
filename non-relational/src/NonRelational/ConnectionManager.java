package NonRelational;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.interfaces.persistence.IConnectionManager;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

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
        String path = "jsonDB";
        deleteDir(new File(path));
    }

    void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }
}