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
            final String CONNECTION_URL = "jdbc:sqlite:rel_db.db";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);
            createTables();
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
            stmt.execute("create table if not exists user\n" +
                    "(\n" +
                    "	 userName text not null primary key,\n" +
                    "    personID text not null,\n" +
                    "    password text not null,\n" +
                    "    email text not null,\n" +
                    "    firstName text not null,\n" +
                    "    lastName text not null, \n" +
                    "    gender text not null,\n" +
                    "    constraint ck_gender check (gender in ('m', 'f'))\n" +
                    ");");
            stmt.execute("create table if not exists token\n" +
                    "(\n" +
                    "    userName text not null,\n" +
                    "    password text not null,\n" +
                    "    authToken text not null\n" +
                    ");");
            stmt.execute("create table if not exists person\n" +
                    "(\n" +
                    "    personID text not null primary key,\n" +
                    "    descendant text not null,"
                    +"   --foreign key (decendant) references user (username),\n" +
                    "    firstName text not null,\n" +
                    "    lastName text not null,\n" +
                    "    gender text not null,\n" +
                    "    --constraint ck_gender check (gender in('m', 'f')),\n" +
                    "    father text,\n" +
                    "    mother text,\n" +
                    "    spouse text\n" +
                    ");");
            stmt.execute("create table if not exists event\n" +
                    "(\n" +
                    "    eventID text not null primary key,\n" +
                    "    descendant text not null,"
                    + "  --foreign key (decendant) references user (username),\n" +
                    "    personID text not null,"
                    + "  --foreign key (personID) references person(personID),\n" +
                    "    latitude real not null,\n" +
                    "    longitude real not null,\n" +
                    "    country text not null,\n" +
                    "    city text not null,\n" +
                    "    eventType text not null,\n" +
                    "    year text not null\n" +
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
            stmt.execute("DROP TABLE if exists token");
            stmt.execute("DROP TABLE if exists event");
            stmt.execute("DROP TABLE if exists person");
            stmt.execute("DROP TABLE if exists user");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean clearTables()
    {
        try(Statement stmt = conn.createStatement())
        {
            stmt.execute("delete from token");
            stmt.execute("delete from event");
            stmt.execute("delete from person");
            stmt.execute("delete from user");
        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

