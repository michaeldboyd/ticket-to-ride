package NonRelational;

import com.example.sharedcode.interfaces.persistence.*;

import java.io.File;
import java.nio.file.Files;

public class DatabaseFactory implements IDatabaseFactory{

    @Override
    public void initializeDatabase() {
        createConnectionManager();
        createUserDAO();
        createGameDAO();
        createCommandDAO();

        System.out.println("initialized non-relational database");
    }

    @Override
    public IConnectionManager createConnectionManager() {
        return new ConnectionManager();
    }

    @Override
    public IUserDAO createUserDAO() {
        return new UserDAO();
    }

    @Override
    public IGameDAO createGameDAO() {
        return new GameDAO();
    }

    @Override
    public ICommandDAO createCommandDAO() {
        return new CommandDAO();
    }

    @Override
    public void clearDatabase() {
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
