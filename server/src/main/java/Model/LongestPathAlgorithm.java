package Model;

import com.example.sharedcode.model.City;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LongestPathAlgorithm {

    public static Game updateLongestPathsInGame(Game game) {
        Map<String, SimpleWeightedGraph<String, DefaultWeightedEdge>> playerGraphs = buildGraphs(game);

        // find the longest path for each player

        return null;
    }

    private static Map<String, SimpleWeightedGraph<String, DefaultWeightedEdge>> buildGraphs(Game game) {
        Map<String, SimpleWeightedGraph<String, DefaultWeightedEdge>> playerGraphs = new HashMap<>();
        for(Player player : game.getPlayers()) {
            //init the player graphs
            playerGraphs.put(
                    player.getName(),
                    new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class)
            );
        }
        for( Map.Entry<Route, Player> entry : game.getRoutesClaimed().entrySet()) {
            // put the route in the right player's graph
            String playerName = entry.getValue().getName();
            Route route = entry.getKey();

            //set vertices
            playerGraphs.get(playerName).addVertex(route.getCity1());
            playerGraphs.get(playerName).addVertex(route.getCity2());

            // add edge
            playerGraphs.get(playerName).addEdge(route.getCity1(), route.getCity2());

            //set edge weight
            DefaultWeightedEdge edge = playerGraphs.get(playerName).getEdge(route.getCity1(), route.getCity2());
            playerGraphs.get(playerName).setEdgeWeight(edge, route.getNumberTrains());

        }
        playerGraphs.toString();
        return playerGraphs;
    }

    private static void findLongestPath(Map<String, SimpleWeightedGraph<String, DefaultWeightedEdge>> playerGraphs) {
        for(SimpleWeightedGraph<String, DefaultWeightedEdge> graph : playerGraphs.values()) {
            Iterator<String> iter = new DepthFirstIterator<>(graph);
            SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> dfsTree =
                    new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

            while (iter.hasNext()) {

               // depth first search
            }
        }

    }
}

/**
 * make my own tree for each person. with connected cities
 * iterate through each tree to see who's is longestp
 */

