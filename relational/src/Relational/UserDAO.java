package Relational;

import com.example.sharedcode.interfaces.persistence.IUserDAO;
import com.example.sharedcode.model.User;

public class UserDAO implements IUserDAO {
    @Override
    public User addUser(String userName, String password) {
        return null;
    }

    @Override
    public String login(String userName, String password) {
        return null;
    }

    @Override
    public boolean logout(String authToken) {
        return false;
    }

    @Override
    public boolean joinGame(String gameID) {
        return false;
    }

    @Override
    public void clearDatabase() {

    }
}
