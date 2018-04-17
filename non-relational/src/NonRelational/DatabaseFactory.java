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

    }
}
