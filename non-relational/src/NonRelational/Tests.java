package NonRelational;

import com.example.sharedcode.model.User;

public class Tests {
    public void testOpenConnection() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.openConnection();
    }

    public void createTestUserDao() {
        User fakeUser = new User();
        fakeUser.setUsername("ladeedah");
        fakeUser.setPassword("password");

        UserDAO userDAO = new UserDAO();
        userDAO.addUser(fakeUser);
    }
}
