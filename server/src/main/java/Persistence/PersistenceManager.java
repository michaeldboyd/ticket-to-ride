package Persistence;

import com.example.sharedcode.interfaces.persistence.IDatabaseFactory;

public class PersistenceManager {
    private static PersistenceManager ourInstance = new PersistenceManager();

    public static PersistenceManager getInstance() {
        return ourInstance;
    }

    private PersistenceManager() {
    }

    private IDatabaseFactory databaseFactory;

    private int timesBetweenStorage = 10;

    private String dbName = "";

    void loadPlugin(String pluginName) {
        // Search the plugins folder for the JSON file that has information
        // about the plugin
        // Loads that JSON object
        // Accesses the data member "JAR-JAR PATH"
        // URLClassLoader to bring it in
        // It knows the main type based on the JSON file
        // Sets IDatabaseFactory to be of the main type
    }

    public IDatabaseFactory getDatabaseFactory() {
        return databaseFactory;
    }

    public void setDatabaseFactory(IDatabaseFactory databaseFactory) {
        this.databaseFactory = databaseFactory;
    }

    public int getTimesBetweenStorage() {
        return timesBetweenStorage;
    }

    public void setTimesBetweenStorage(int timesBetweenStorage) {
        this.timesBetweenStorage = timesBetweenStorage;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}