package NonRelational;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.interfaces.persistence.ICommandDAO;
import com.example.sharedcode.model.Game;
import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandDAO implements ICommandDAO {

    private final String gamesFolderPath = "jsonDB" + File.separator + "games";

    @Override
    public ArrayList<Command> getCommands(String gameID) {

        ArrayList<Command> commands = new ArrayList<>();

        String path = gamesFolderPath + File.separator + gameID + File.separator + "commands";

        File commandsFolder = new File(path);
        File[] commandFiles = commandsFolder.listFiles();

        int arrayLength = 0;
        if (commandFiles != null) {
            arrayLength = Array.getLength(commandFiles);
        }

        for(int i = 0; i < arrayLength; i++) {
            File commandFile = new File(path + File.separator + i + ".json");
            commands.add(loadCommandFromFile(commandFile));
        }

        return commands;

    }

    @Override
    public void storeGameCommand(Command command, String gameID) {

        String path = gamesFolderPath + File.separator + gameID + File.separator + "commands";
        File commandsFolder = new File(path);
        commandsFolder.mkdirs();

        File[] commandFiles = commandsFolder.listFiles();
        int index_next_file = 0;

        if (commandFiles != null) {
            index_next_file = Array.getLength(commandFiles);
        }

        String commandFilePath = path + File.separator + index_next_file + ".json";
        writeCommandToFile(command, commandFilePath);
    }

    @Override
    public boolean clearGameCommands(String gameID) {

        String path = gamesFolderPath + File.separator + gameID + File.separator + "commands";
        File commandsFolder = new File(path);

        deleteDir(commandsFolder);

        return true;
    }

    private Command loadCommandFromFile(File commandFile) {
        Command command = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(commandFile);
            command = (Command) JsonReader.jsonToJava(fileInputStream, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return command;
    }

    private void writeCommandToFile(Command command, String path) {
        File commandFile = new File(path);
        commandFile.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(commandFile);
            JsonWriter jw = new JsonWriter(fileOutputStream);
            jw.write(command);
            jw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }
}