package Test;

import Relational.UserDAO;
import com.example.sharedcode.model.User;

public class UserDaoTest {

    public static void main(String args[]) {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        UserDAO dao = new UserDAO();
        dao.addUser(user);
        user = dao.getUser("test");

        dao.getAllUsers();
        dao.updateUser(user.getUsername(), user, null);
    }
}
