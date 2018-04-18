package Persistence;

import Facades.ServerUtility;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.interfaces.persistence.ICommandDAO;
import com.example.sharedcode.interfaces.persistence.IGameDAO;
import com.example.sharedcode.interfaces.persistence.IUserDAO;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.User;

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
                //loads the game to the server model
                pushGameToServerModel(g);
                //executes the game on the server model
                simulateGame(commandsByGame.get(g.getGameID()));
            }


        } catch(Exception e) {
            errorOccured(e, "Game restoration failed. Server model cleared");
        }
    }

    @Override
    public void initFromDB() {
        games = PersistenceManager.getInstance().getDatabaseFactory().createGameDAO().getAllGames();

        for (Game g : games) {
            if(g.isStarted()) {
                ServerModel.instance().getStartedGames().put(g.getGameID(), g);
            }
            List<Command> commandsTemp = PersistenceManager.getInstance().getDatabaseFactory().createCommandDAO().getCommands(g.getGameID());
            commandsByGame.put(g.getGameID(), commandsTemp);
        }
        // users

        // init all users
        // init logged in users

        // init authToken to username
        IUserDAO ud = PersistenceManager.getInstance().getDatabaseFactory().createUserDAO();
        List<User> users = ud.getAllUsers();
        for(User u : users) {
            ServerModel.instance().getAllUsers().put(u.getUsername(), u);
            ServerModel.instance().getAuthTokenToUsername().put(u.getAuthtoken(), u.getUsername());
            if(u.getAuthtoken() != null && !u.getUsername().equals("")) {
                ServerModel.instance().getLoggedInUsers().put(u.getUsername(), u);
            }
        }

        // init users in lobby
        // init started games



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

    @Override
    public void addCommandForGame(Command command, Game game) {
        String gameID = game.getGameID();

        if (!commandsByGame.containsKey(gameID)) {
            commandsByGame.put(gameID, new ArrayList<>());
        }

        if (commandsByGame.get(gameID).size() == PersistenceManager.getInstance().getTimesBetweenStorage()) {
            // Remove all commands
            commandsByGame.get(gameID).clear();
            ICommandDAO commandDAO = PersistenceManager.getInstance().getDatabaseFactory().createCommandDAO();
            commandDAO.clearGameCommands(gameID);

            // Save the game in the database
            IGameDAO gameDAO = PersistenceManager.getInstance().getDatabaseFactory().createGameDAO();
            gameDAO.updateGame(gameID, game);
        } else {
            commandsByGame.get(gameID).add(command);
            ICommandDAO commandDAO = PersistenceManager.getInstance().getDatabaseFactory().createCommandDAO();
            commandDAO.storeGameCommand(command, gameID);
        }
    }

    private void errorOccured(Exception e, String messageToPrint){
        ServerUtility.instance().clearServer(ServerModel.instance().getTestPassword());

        e.printStackTrace();
        System.out.println(messageToPrint);
    }



}
