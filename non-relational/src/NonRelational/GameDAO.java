package NonRelational;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.interfaces.persistence.IGameDAO;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO implements IGameDAO {

    private final String gamesFolderPath = "jsonDB" + File.separator +  "games";

    @Override
    public Game getGame(String gameID) {

        String path = gamesFolderPath + File.separator + gameID;
        File gameFile = new File(path);
        return loadGameFromFolder(gameFile);
    }

    @Override
    public List<Game> getAllGames() {
        String path = gamesFolderPath;
        File gamesDirectory = new File(path);

        File[] filesInDirectory = gamesDirectory.listFiles();

        ArrayList<Game> allGames = new ArrayList<>();

        for (File gameFolder : filesInDirectory) {
            path = gameFolder.getAbsolutePath();
            File gameFile = new File(path);
            Game game = loadGameFromFolder(gameFile);
            if (game != null) {
                allGames.add(game);
            }
        }

        return allGames;
    }

    @Override
    public String addGame(Game game) {

        String path = gamesFolderPath + File.separator + game.getGameID();
        writeGameToFolder(game, path);

        return game.getGameID();
    }

    @Override
    public boolean updateGame(String gameID, Game game) {

        String path = gamesFolderPath + File.separator + game.getGameID();
        writeGameToFolder(game, path);

        return false;
    }

    @Override
    public boolean removeGame(String gameID) {
        return false;
    }

    private Game loadGameFromFolder(File gameFolder) {
        Game game = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(gameFolder + File.separator + "game.json");
            game = (Game) JsonReader.jsonToJava(fileInputStream, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return game;
    }

    private void writeGameToFolder(Game game, String path) {
        File gameFile = new File(path + File.separator + "game.json");
        gameFile.getParentFile().mkdirs();

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
