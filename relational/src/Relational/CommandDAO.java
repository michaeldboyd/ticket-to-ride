package Relational;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.interfaces.persistence.ICommandDAO;
import com.example.sharedcode.model.User;


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

public class CommandDAO implements ICommandDAO {
    @Override
    public ArrayList<Command> getCommands(String gameID) {
        ArrayList<Command> cl = new ArrayList<>();
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;
        Command command = null;
        byte[] commandBytes;
        try {
            String sql = "select command from command where gameID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, gameID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                commandBytes = (byte[]) rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(commandBytes);
                ObjectInputStream ois = new ObjectInputStream(baip);
                command = (Command) ois.readObject();
                cl.add(command);
            }

            stmt.close();

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cl;

    }

    @Override
    public void storeGameCommand(Command command, String gameID) {
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(command);

            byte[] commandBytes = baos.toByteArray();
            ByteArrayInputStream bais  = new ByteArrayInputStream(commandBytes);

            String sql = "insert into command (command, gameID) values (?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setBinaryStream(1, bais, commandBytes.length);
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

    }

    @Override
    public boolean clearGameCommands(String gameID) {
        ConnectionManager cm = new ConnectionManager();
        cm.openConnection();
        Connection conn = cm.getConnection();

        PreparedStatement stmt = null;
        try {

            String sql = "delete from command where gameID=?";
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
