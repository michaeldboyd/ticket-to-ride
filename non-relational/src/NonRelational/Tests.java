package NonRelational;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.User;

public class Tests {

    public void run() {
        testOpenConnection();
        createTestUserDao();
        updateUser();
        createMultipleUsers();
        printResults();
        gameTest();
        commandTest();
    }

    public void testOpenConnection() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.openConnection();
    }

    public void createTestUserDao() {
        User fakeUser = new User();
        fakeUser.setUsername("ladeedah");
        fakeUser.setPassword("password");

        UserDAO userDAO = new UserDAO();
        userDAO.addUser(fakeUser);
    }


    public void updateUser() {
        User fakeUser = new User();
        fakeUser.setUsername("ladeedah");
        fakeUser.setPassword("password");
        fakeUser.setAuthtoken("HAS_ONE");

        UserDAO userDAO = new UserDAO();
        userDAO.updateUser("ladeedah", fakeUser, "game1");
    }

    public void printResults() {
        UserDAO userDAO = new UserDAO();

        System.out.println("getUser(\"ladeedah\") = " + userDAO.getUser("ladeedah").getUsername());
        System.out.println("getAllUsers() = ");
        for(User user : userDAO.getAllUsers()) {
            System.out.println(user.getUsername());
        }
    }

    public void createMultipleUsers() {
        UserDAO userDAO = new UserDAO();

        User fakeUser = new User();

        fakeUser.setUsername("hunter");
        fakeUser.setPassword("001");

        userDAO.addUser(fakeUser);

        fakeUser.setUsername("ali");
        fakeUser.setPassword("002");

        userDAO.addUser(fakeUser);
    }

    public void gameTest() {
        GameDAO gameDAO = new GameDAO();
        Game fakeGame = new Game();
        fakeGame.setGameID("Game1");

        gameDAO.addGame(fakeGame);
        fakeGame.setGameID("Game2");
        gameDAO.addGame(fakeGame);
        fakeGame.setGameID("Game3");
        gameDAO.addGame(fakeGame);

        Game game1 = gameDAO.getGame("Game1");

        System.out.println("Game1: " + game1 + " " + game1.getGameID());
        System.out.println("getAllGames() = ");
        for(Game game : gameDAO.getAllGames()) {
            System.out.println(game.getGameID() + ": " + game);
        }
    }

    public void commandTest() {
        CommandDAO commandDAO = new CommandDAO();
        CommandFactory commandFactory = new CommandFactory();
        Command command = CommandFactory.createCommand("1", "Classy class", "methody method", null, null);
        commandDAO.storeGameCommand(command, "Game1");
        command = CommandFactory.createCommand("2", "Classy class", "methody method", null, null);
        commandDAO.storeGameCommand(command, "Game1");
        command = CommandFactory.createCommand("3", "Classy class", "methody method", null, null);
        commandDAO.storeGameCommand(command, "Game1");

        command = CommandFactory.createCommand("1", "Classy class", "methody method", null, null);
        commandDAO.storeGameCommand(command, "Game2");
        command = CommandFactory.createCommand("2", "Classy class", "methody method", null, null);
        commandDAO.storeGameCommand(command, "Game2");
        command = CommandFactory.createCommand("3", "Classy class", "methody method", null, null);
        commandDAO.storeGameCommand(command, "Game2");

        for(Command gameCommand : commandDAO.getCommands("Game1")) {
            System.out.println(gameCommand.get_authToken() + " " + gameCommand);
        }

        System.out.println("deleting");

        commandDAO.clearGameCommands("Game1");

        for(Command gameCommand : commandDAO.getCommands("Game1")) {
            System.out.println(gameCommand.get_authToken() + " " + gameCommand);
        }
    }
}
