package NonRelational;

import com.example.sharedcode.interfaces.persistence.*;

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
        return null;
    }

    @Override
    public IUserDAO createUserDAO() {
        return null;
    }

    @Override
    public IGameDAO createGameDAO() {
        return null;
    }

    @Override
    public ICommandDAO createCommandDAO() {
        return null;
    }

    @Override
    public void clearDatabase() {

    }
}
