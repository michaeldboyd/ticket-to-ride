package Model;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class LongestPathAlgorithm {

    public static Game update(Game game) {
        Map<String, SimpleWeightedGraph<Vertex, DefaultWeightedEdge>> playerGraphs = buildGraphs(game);

        //build new graph with vertices
        Map<String, SimpleWeightedGraph<Vertex, DefaultWeightedEdge>> vertGraphs = new HashMap<>();
        for(Map.Entry<String, SimpleWeightedGraph<Vertex, DefaultWeightedEdge>> e : playerGraphs.entrySet()) {
        }
        Map<String, Integer> longestPaths = findLongestPaths(playerGraphs); // find the longest path for each player

        // set longest paths for each player
        String lpPlayer = "";
        int max = 0;
        for(Player player : game.getPlayers()) {
            int longestPath = longestPaths.get(player.getName());
            player.setLongestPath(longestPath);
            if(longestPath > max) {
                max = longestPath;
                lpPlayer = player.getName();
            }
        }

        //set who has the overall longest path
        for(Player player : game.getPlayers()) {
            if(lpPlayer.equals(player.getName())) {
                player.setHasLongestPath(true);
            } else {
                player.setHasLongestPath(false);
            }
        }
        return game;
    }

    private static Map<String, SimpleWeightedGraph<Vertex, DefaultWeightedEdge>> buildGraphs(Game game) {
        Map<String, SimpleWeightedGraph<Vertex, DefaultWeightedEdge>> playerGraphs = new HashMap<>();
        for(Player player : game.getPlayers()) {
            //init the player graphs
            playerGraphs.put(
                    player.getName(),
                    new SimpleWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class)
            );
        }
        for( Map.Entry<Route, Player> entry : game.getRoutesClaimed().entrySet()) {
            // put the route in the right player's graph
            if(entry.getValue() != null)
            {
                //if the city is in the set, don't add it to the lsit.
                String playerName = entry.getValue().getName();
                Route route = entry.getKey();
                Vertex vertex1 = new Vertex<>(route.getCity1());
                Vertex vertex2 = new Vertex<>(route.getCity2());

                if(!playerGraphs.get(playerName).containsVertex(vertex1)) {
                    playerGraphs.get(playerName).addVertex(vertex1);
                }

                if(!playerGraphs.get(playerName).containsVertex(vertex2))
                    playerGraphs.get(playerName).addVertex(vertex2);

                // add edge
                playerGraphs.get(playerName).addEdge(vertex1, vertex2);

                //set edge weight
                DefaultWeightedEdge edge = playerGraphs.get(playerName).getEdge(vertex1, vertex2);
                playerGraphs.get(playerName).setEdgeWeight(edge, route.getNumberTrains());
            }


        }
        return playerGraphs;
    }

    private static Map<String, Integer> findLongestPaths(Map<String, SimpleWeightedGraph<Vertex, DefaultWeightedEdge>> playerGraphs) {
        Map<String, Integer> longestPaths  = new HashMap<>();
        for(Map.Entry<String, SimpleWeightedGraph<Vertex, DefaultWeightedEdge>> e : playerGraphs.entrySet()) {
            // get the longest path for each graph
            Set<Vertex> vertices = e.getValue().vertexSet();
            double max = 0;
            for(Vertex v : vertices) {
                double temp = getLongestPath(v, e.getValue());
                if(temp > max) {
                    max = temp;
                }
            }
            longestPaths.put(e.getKey(),(int) max );
        }
        return longestPaths;
    }


    private static double getLongestPath(Vertex v, SimpleWeightedGraph<Vertex, DefaultWeightedEdge> graph) {
        v.visited = true;
        double dist, max = 0;
        Set<DefaultWeightedEdge> edges = graph.edgesOf(v);
        for(DefaultWeightedEdge e : edges) {
            Vertex target = graph.getEdgeTarget(e);
            Vertex source = graph.getEdgeSource(e);
            if(target.val.equals(v.val)) { // if the target value is the same, the source is the target
                assert (!source.val.equals(v.val));
                target = source;
            }

            if(!target.visited) {
                dist = graph.getEdgeWeight(e) + getLongestPath(target, graph);
                if(dist > max) {
                    max = dist;
                }
            }
        }
        v.visited = false;
        return max;
    }


}

class Vertex<V> {
    V val;
    boolean visited = false;

    Vertex(V val) {
        this.val = val;
    }



    @Override
    public boolean equals(Object o) {
        if(o == null  || o.getClass() != this.getClass())
            return false;
        Vertex v = (Vertex) o;

        if(v.val.equals(this.val)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ((String) this.val).length();
    }
}

