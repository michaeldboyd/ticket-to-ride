package Relational;
import java.sql.*;
import com.example.sharedcode.interfaces.persistence.IConnectionManager;

import javax.xml.crypto.Data;

public class ConnectionManager implements IConnectionManager {

    private Connection conn;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }



    public Connection getConnection()
    {
        return conn;
    }
    @Override
    public void openConnection(){
        try {
            final String CONNECTION_URL = "jdbc:sqlite:../relational/rel_db.db";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        boolean commit = true;
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        try
        {
            ConnectionManager cm = new ConnectionManager();
            cm.openConnection();
            cm.dropTables();
            cm.createTables();
            cm.closeConnection();
            System.out.println("DB Commands executed");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    // create the tables
    public void createTables() {

        // a slightly hacky way of getting the file into a string in one line

        try(Statement stmt = conn.createStatement()){
            stmt.execute("create table if not exists game\n" +
                    "(\n" +
                    "	 gameID text not null primary key,\n" +
                            "game blob not null " +
                    ");");
            stmt.execute("create table if not exists command\n" +
                    "(\n" +
                    "    commandID integer not null primary key autoincrement,\n" +
                    "    command blob not null,\n" +
                    "    gameID text not null\n" +
                    ");");
            stmt.execute("create table if not exists user\n" +
                    "(\n" +
                    "    userName text not null primary key,\n" +
                    "    user blob not null," +
                    "    gameID text" +
                    ");");
        } catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            e.printStackTrace();
        } catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void dropTables()
    {
        try(Statement stmt = conn.createStatement()){
            //drop tables
            stmt.execute("DROP TABLE if exists game");
            stmt.execute("DROP TABLE if exists user");
            stmt.execute("DROP TABLE if exists command");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean clearTables()
    {
        openConnection();

        try(Statement stmt = conn.createStatement())
        {
            stmt.execute("delete from game");
            stmt.execute("delete from user");
            stmt.execute("delete from command");
        } catch(Exception e) {
            e.printStackTrace();
            return false;
         } finally {
            closeConnection();
        }

        return true;
    }

}

