package NonRelational;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.interfaces.persistence.IGameDAO;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.User;

import java.io.*;

public class GameDAO implements IGameDAO {

    private final String gamesFolderPath = "games";

    @Override
    public Game getGame(String gameID) {

        String path = gamesFolderPath + File.separator + gameID;
        File gameFile = new File(path);
        return loadGameFromFile(gameFile);
    }

    @Override
    public String addGame(Game game) {

        String path = gamesFolderPath + File.separator + game.getGameID();
        writeGameToFile(game, path);

        return game.getGameID();
    }

    @Override
    public boolean updateGame(String gameID, Game game) {
        
        return false;
    }

    @Override
    public boolean removeGame(String gameID) {
        return false;
    }

    private Game loadGameFromFile(File gameFile) {
        Game game = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(gameFile);
            game = (Game) JsonReader.jsonToJava(fileInputStream, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return game;
    }

    private void writeGameToFile(Game game, String path) {
        File gameFile = new File(path);
        gameFile.mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(gameFile);
            JsonWriter jw = new JsonWriter(fileOutputStream);
            jw.write(game);
            jw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
