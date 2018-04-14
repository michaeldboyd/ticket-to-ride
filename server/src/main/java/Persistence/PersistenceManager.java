package Persistence;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.interfaces.persistence.IDatabaseFactory;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

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

    public void loadPlugin(String pluginName) throws Exception {
        // Search the plugins folder for the JSON file that has information
        // about the plugin
        // Loads that JSON object
        // Accesses the data member "JAR-JAR PATH"
        // URLClassLoader to bring it in
        // It knows the main type based on the JSON file
        // Sets IDatabaseFactory to be of the main type

        // Getting the jar URL which contains target class
        // This exists in the root directory of the project, which is one level above the server
        String json = readFile("../plugins.json");
        PluginInformation[] plugins = (PluginInformation[]) JsonReader.jsonToJava(json);

        System.out.println(System.getProperty("user.dir"));
        PluginInformation selectedPlugin = null;
        for (PluginInformation plugin : plugins) {
            if (pluginName.equals(plugin.getName())) {
                selectedPlugin = plugin;
            }
        }

        if (selectedPlugin == null) {
            // TODO: - Might need to do some sort of error handling for not finding a valid plugin for the name entered
            return;
        }

        URL[] classLoaderUrls = {
                new URL(selectedPlugin.getFilePath())
        };

        // Create a new URLClassLoader
        URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

        // Load the target class
        System.out.println();
        Class<?> databaseFactoryClass = urlClassLoader.loadClass(selectedPlugin.getClassPath());

        // Create a new instance from the loaded class
        Constructor<?> constructor = databaseFactoryClass.getConstructor();
        Object databaseFactory = constructor.newInstance();

        // Getting a method from the loaded class and invoke it
        Method method = databaseFactoryClass.getMethod("initializeDatabase");
        method.invoke(databaseFactory) ;
    }

    String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
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