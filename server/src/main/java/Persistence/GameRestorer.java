package Persistence;

import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GameRestorer implements IGameRestorer {

    //SINGLETON PORTION
    private static GameRestorer ourInstance = new GameRestorer();

    public static GameRestorer getInstance() {
        return ourInstance;
    }
    //SINGLETON PORTION

    private static Logger log;

    private List<Game> games = new ArrayList<>();
    private Map<String, List<Command>> commandsByGame = new HashMap<>();
    private ServerModel model = ServerModel.instance();

    private GameRestorer() {
    }

    @Override
    public void run(){
        try {
            initFromDB();

            for (Game g : games) {
                pushGameToServerModel(g);
            }


        } catch(Exception e) {
            errorOccured(e, "Game restoration failed. Server model cleared");
        }
    }

    @Override
    public void initFromDB() {
        //TODO: Implement when DAOs are working
        //games = DAO.getGames();
        //for (Game g : games) {
        //  List<Command> commandsTemp = DAO.GetCommands(g.getGameID());
        //  commandsByGame.set(g.getGameID(), commandsTemp);
        //{
    }

    @Override
    public List<Game> getGames() {
        return games;
    }

    @Override
    public List<Command> getCommandsForGame(String gameID) {
        return commandsByGame.get(gameID);
    }

    @Override
    public void simulateGame(List<Command> commands) {
        try {
            for (Command c : commands) {
                c.execute();
            }
        } catch (Exception e) {
            errorOccured(e, "Game simulation failed. Server model cleared.");
        }

    }

    private void errorOccured(Exception e, String messageToPrint){
        //TODO: Create a method on the ServerModel to clear the database. This prevents a duplicate games from being created.

        e.printStackTrace();
        System.out.println(messageToPrint);
    }

    @Override
    public void pushGameToServerModel(Game game) throws Exception {
        model.getGames().put(game.getGameID(), game);

        //Check that the game was stored correctly
        Game checkGame = model.getGames().get(game.getGameID());

        if (checkGame == null){
            throw new Exception("Game not stored in model correctly: Game null after withdrawn.");
        } else if (!game.getGameID().equals(checkGame.getGameID())){
            throw new Exception("Game not stored in model correctly: Game ID not consistent.");
        } else if (game.getPlayers().size() != checkGame.getPlayers().size()){
            throw new Exception("Game not stored in model correctly: Player List size not equal.");
        }
    }
}
