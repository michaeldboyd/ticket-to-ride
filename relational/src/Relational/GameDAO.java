package Relational;

import com.example.sharedcode.interfaces.persistence.IGameDAO;
import com.example.sharedcode.model.Game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAO implements IGameDAO {
    @Override
    public Game getGame(String gameID) {
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;
        Game game = null;
        byte[] userBytes;
        try {
            String sql = "select game from game where gameID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, gameID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                userBytes = (byte[]) rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(userBytes);
                ObjectInputStream ois = new ObjectInputStream(baip);
                game = (Game) ois.readObject();
            }

            stmt.close();

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> ul = new ArrayList<>();
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;
        Game game = null;
        byte[] userBytes;
        try {
            String sql = "select game from game";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                userBytes = (byte[]) rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(userBytes);
                ObjectInputStream ois = new ObjectInputStream(baip);
                game = (Game) ois.readObject();
                ul.add(game);
            }

            stmt.close();

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ul;
    }

    @Override
    public String addGame(Game game){
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;
        String gameID = game.getGameID();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(game);

            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream bais  = new ByteArrayInputStream(bytes);

            String sql = "insert into game (gameID, game) values (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, gameID);
            stmt.setBinaryStream(2, bais, bytes.length);
            stmt.execute();
            stmt.close();

            conn.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return game.getGameID();
    }

    @Override
    public boolean updateGame(String gameID, Game game) {
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(game);

            byte[] gameBytes = baos.toByteArray();
            ByteArrayInputStream bais  = new ByteArrayInputStream(gameBytes);

            String sql = "update game set game=? where gameID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setBinaryStream(1, bais, gameBytes.length);
            stmt.setString(2, gameID);

            stmt.execute();
            stmt.close();

            conn.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean removeGame(String gameID) {
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;
        try {

            String sql = "delete from game where gameID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, gameID);
            stmt.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
