package Relational;

import com.example.sharedcode.interfaces.persistence.IUserDAO;
import com.example.sharedcode.model.User;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDAO implements IUserDAO {
    @Override
    public User getUser(String un) {
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;
        User user = null;
        byte[] userBytes;
        try {
            String sql = "select user from user where userName=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, un);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                userBytes = (byte[]) rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(userBytes);
                ObjectInputStream ois = new ObjectInputStream(baip);
                user = (User) ois.readObject();
            }

            stmt.close();

            conn.commit();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } return user;

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User addUser(User user) {
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;
        String userName = user.getUsername();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);

            byte[] userBytes = baos.toByteArray();
            ByteArrayInputStream bais  = new ByteArrayInputStream(userBytes);

            String sql = "insert into user (userName, user) values (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setBinaryStream(2, bais, userBytes.length);
            stmt.execute();
            stmt.close();

            conn.commit();
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public String login(String userName, String authToken) {
        return null;
    }

    @Override
    public boolean logout(String userName) {
        return false;
    }

    @Override
    public boolean joinGame(String userName, String gameID) {
        return false;
    }

    @Override
    public boolean leaveGame(String userName, String gameID) {
        return false;
    }

}
