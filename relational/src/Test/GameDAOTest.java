package Test;

import Relational.GameDAO;
import com.example.sharedcode.model.Game;

public class GameDAOTest {


    public static void main(String args[]) {
        Game game = new Game();
        game.setGameID("test");
        GameDAO dao = new GameDAO();
        dao.addGame(game);
        game = dao.getGame("test");

        dao.getAllGames();
        dao.updateGame("test", game);
    }
}
