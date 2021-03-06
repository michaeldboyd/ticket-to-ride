import Model.LongestPathAlgorithm;
import com.example.sharedcode.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LongestPathAlgTest {

    @Before
    public void init() {




    }

    private Game getGame() {
        Game game = new Game();

        Player p1 = new Player("001", "001", PlayerColors.BLUE);
        Player p2 = new Player("002", "002", PlayerColors.RED);
        Player p3 = new Player("003", "003", PlayerColors.ORANGE);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game = new GameInitializer().initializeGame(game);

        return game;
    }

    @Test
    public void testFullBoard() {
        Game game = getGame();
        for(Map.Entry<Route, Player> e : game.getRoutesClaimed().entrySet()) {
            int player = new Random().nextInt(2);
            e.setValue(game.getPlayers().get(player));
        }
        
        game = LongestPathAlgorithm.update(game);
    }

    @Test
    public void testFullBoardSinglePlayer() {
        Game game = getGame();
        int count = 45;
        for(Map.Entry<Route, Player> e : game.getRoutesClaimed().entrySet()) {
            e.setValue(game.getPlayers().get(0));
            count--;
            if(count == 0)
                break;
        }
        game = LongestPathAlgorithm.update(game);

        Assert.assertTrue(game.getPlayers().get(0).hasLongestPath());
    }

    @Test
    public void testSmallBoard() {
        Game game = getGame();
        for (Player p : game.getPlayers()) {
            p.getDestinationCards().clear();
        }
        Player p = game.getPlayers().get(0);
        Map<Route, Player> routes = new HashMap<>();
        routes.put(new Route("Rosette", "Lucin", 2, TrainType.BOX), p);
        routes.put(new Route("Rosette", "Aragonite", 2, TrainType.PASSENGER), null);
        routes.put(new Route("Rosette", "Salt Lake City", 6, TrainType.TANKER, true), null);
        routes.put(new Route("Salt Lake City", "Rosette", 6, TrainType.REEFER, true), null);
        routes.put(new Route("Rosette", "Paradise", 4, TrainType.FREIGHT), p);
        routes.put(new Route("Rosette", "Randolph", 6, TrainType.HOPPER), null);
        routes.put(new Route("Lucin", "Gold Hill", 5, TrainType.COAL), p);
        routes.put(new Route("Aragonite", "Gold Hill", 3, TrainType.CABOOSE), null);
        routes.put(new Route("Aragonite", "Dugway", 2, TrainType.BOX), null);
        routes.put(new Route("Aragonite", "Salt Lake City", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Salt Lake City", "Dugway", 2, TrainType.TANKER), null);
        routes.put(new Route("Salt Lake City", "Provo", 2, TrainType.REEFER), null);
        routes.put(new Route("Salt Lake City", "Park City", 1, TrainType.FREIGHT), null);
        routes.put(new Route("Salt Lake City", "Paradise", 4, TrainType.HOPPER), null);
        routes.put(new Route("Paradise", "Randolph", 1, TrainType.COAL), null);
        routes.put(new Route("Randolph", "Tabiona", 5, TrainType.CABOOSE), null);
        routes.put(new Route("Randolph", "Manila", 5, TrainType.BOX), null);
        routes.put(new Route("Gold Hill", "Gandy", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Gold Hill", "Black Rock", 6, TrainType.TANKER, true), null);
        routes.put(new Route("Black Rock", "Gold Hill", 6, TrainType.COAL, true), null);
        game.setRoutesClaimed(routes);
        game = LongestPathAlgorithm.update(game);

        Assert.assertEquals(11, game.getPlayers().get(0).getLongestPath());
    }

    @Test
    public void testDestCards() {
        Game game = getGame();
        for (Player p : game.getPlayers()) {
            p.getDestinationCards().clear();
        }

        game.getPlayers().get(0).getDestinationCards().add(new DestinationCard("Provo", "Gold Hill",5));
        Player p = game.getPlayers().get(0);

        Map<Route, Player> routes = new HashMap<>();
        routes.put(new Route("Rosette", "Lucin", 2, TrainType.BOX), p);
        routes.put(new Route("Rosette", "Aragonite", 2, TrainType.PASSENGER), null);
        routes.put(new Route("Rosette", "Salt Lake City", 6, TrainType.TANKER, true), null);
        routes.put(new Route("Salt Lake City", "Rosette", 6, TrainType.REEFER, true), null);
        routes.put(new Route("Rosette", "Paradise", 4, TrainType.FREIGHT), p);
        routes.put(new Route("Rosette", "Randolph", 6, TrainType.HOPPER), null);
        routes.put(new Route("Lucin", "Gold Hill", 5, TrainType.COAL), p);
        routes.put(new Route("Aragonite", "Gold Hill", 3, TrainType.CABOOSE), null);
        routes.put(new Route("Aragonite", "Dugway", 2, TrainType.BOX), null);
        routes.put(new Route("Aragonite", "Salt Lake City", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Salt Lake City", "Dugway", 2, TrainType.TANKER), null);
        routes.put(new Route("Salt Lake City", "Provo", 2, TrainType.REEFER), null);
        routes.put(new Route("Salt Lake City", "Park City", 1, TrainType.FREIGHT), null);
        routes.put(new Route("Salt Lake City", "Paradise", 4, TrainType.HOPPER), null);
        routes.put(new Route("Paradise", "Randolph", 1, TrainType.COAL), null);
        routes.put(new Route("Randolph", "Tabiona", 5, TrainType.CABOOSE), null);
        routes.put(new Route("Randolph", "Manila", 5, TrainType.BOX), null);
        routes.put(new Route("Gold Hill", "Gandy", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Gold Hill", "Black Rock", 6, TrainType.TANKER, true), null);
        routes.put(new Route("Black Rock", "Gold Hill", 6, TrainType.COAL, true), null);
        routes.put(new Route("Gold Hill", "Gandy", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Gold Hill", "Black Rock", 6, TrainType.TANKER, true), null);
        routes.put(new Route("Black Rock", "Gold Hill", 6, TrainType.COAL, true), null);
        routes.put(new Route("Gold Hill", "Dugway", 3, TrainType.REEFER), p);
        routes.put(new Route("Dugway", "Black Rock", 6, TrainType.FREIGHT), null);
        routes.put(new Route("Dugway", "Scipio", 4, TrainType.HOPPER), null);
        routes.put(new Route("Dugway", "Provo", 2, TrainType.COAL), p);
        routes.put(new Route("Provo", "Scipio", 4, TrainType.CABOOSE), null);
        routes.put(new Route("Provo", "Elmo", 4, TrainType.BOX), null);
        game.setRoutesClaimed(routes);
        game = LongestPathAlgorithm.update(game);
        Assert.assertTrue(p.getDestinationCards().get(0).isCompleted());

    }

}
