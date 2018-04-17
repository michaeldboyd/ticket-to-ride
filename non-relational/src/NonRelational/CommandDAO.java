package NonRelational;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.interfaces.persistence.ICommandDAO;
import com.example.sharedcode.model.Game;
import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandDAO implements ICommandDAO {

    private final String gamesFolderPath = "games";

    @Override
    public ArrayList<Command> getCommands(String gameID) {

        ArrayList<Command> commands = new ArrayList<>();

        String path = gamesFolderPath + File.separator + gameID + File.separator + "commands";

        File commandsFolder = new File(path);
        File[] commandFiles = commandsFolder.listFiles();

        int arrayLength = Array.getLength(commandFiles);

        for(int i = 0; i < arrayLength; i++) {
            File commandFile = new File(path + i + ".json");
            commands.add(loadCommandFromFile(commandFile));
        }

        return commands;

    }

    @Override
    public void storeGameCommand(Command command, String gameID) {

        String path = gamesFolderPath + File.separator + gameID + File.separator + "commands";
        File commandsFolder = new File(path);

        File[] commandFiles = commandsFolder.listFiles();
        int index_next_file = Array.getLength(commandFiles);
        String commandFilePath = path + File.separator + index_next_file + ".json";
        writeCommandToFile(command, gameID);
    }

    @Override
    public boolean clearGameCommands(String gameID) {

        String path = gamesFolderPath + File.separator + gameID + File.separator + "commands";
        File commandsFolder = new File(path);

        return commandsFolder.delete();
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
        commandFile.mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(commandFile);
            JsonWriter jw = new JsonWriter(fileOutputStream);
            jw.write(command);
            jw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}