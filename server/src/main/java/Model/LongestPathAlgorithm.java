package Model;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import org.jgrapht.EdgeFactory;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class LongestPathAlgorithm {

    public static Game update(Game game) {
        Map<String, RouteGraph> playerGraphs = buildGraphs(game);


        // set longest paths for each player
        String lpPlayer = "";
        int max = 0;
        for(Player player : game.getPlayers()) {
            RouteGraph playerGraph = playerGraphs.get(player.getName());
            int longestPath = playerGraph.getLongestPath();
            player.setLongestPath(longestPath);
            // go through all destination cards and mark any of them that have been completed.
            player = playerGraph.checkDestCards(player);
            if(longestPath > max) {
                max = longestPath;
                lpPlayer = player.getName();
            }
        }
        //set who has the overall longest path
        for(Player player : game.getPlayers()) {
            if(lpPlayer.equals(player.getName())
                    || player.getLongestPath() == max) {
                player.setLongestPath(true);
            } else{
                player.setLongestPath(false);
            }
        }
        return game;
    }

    private static Map<String, RouteGraph> buildGraphs(Game game) {
        Map<String, RouteGraph> playerGraphs = new HashMap<>();
        for(Player player : game.getPlayers()) {
            //init the player graphs
            playerGraphs.put(
                    player.getName(),
                    new RouteGraph(RouteEdge.class)
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
                RouteEdge edge = new RouteEdge();
                if(!playerGraphs.get(playerName).containsVertex(vertex1)) {
                    playerGraphs.get(playerName).addVertex(vertex1);
                }

                if(!playerGraphs.get(playerName).containsVertex(vertex2))
                    playerGraphs.get(playerName).addVertex(vertex2);

                // add edge
                playerGraphs.get(playerName).addEdge(vertex1, vertex2, edge);

                //set edge weight
                edge = playerGraphs.get(playerName).getEdge(vertex1, vertex2);
                playerGraphs.get(playerName).setEdgeWeight(edge, route.getNumberTrains());
            }


        }
        return playerGraphs;
    }



  /*  private static double getLongestPath(Vertex v, SimpleWeightedGraph<Vertex, RouteEdge> graph) {
        v.visited = true;
        double dist, max = 0;
        Set<RouteEdge> edges = graph.edgesOf(v);
        for(RouteEdge e : edges) {
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
    }*/


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


class RouteEdge extends DefaultWeightedEdge {
    private boolean visited = false;

    public boolean isVisited()
    {
        return visited;
    }

    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }
}

class RouteGraph extends SimpleWeightedGraph<Vertex, RouteEdge> {
    private ConnectivityInspector<Vertex, RouteEdge> inspector;

    public RouteGraph(EdgeFactory<Vertex, RouteEdge> ef)
    {
        super(ef);
        initInspector();
    }

    public RouteGraph(Class<? extends RouteEdge> edgeClass)
    {
        this(new ClassBasedEdgeFactory<Vertex, RouteEdge>(edgeClass));
        initInspector();
    }

    /**
     * Initialize the inspector for the graph
     */
    private void initInspector()
    {
        inspector = new ConnectivityInspector<>(this);
    }

    public int getLongestPath() {
        Map<String, Integer> longestPaths  = new HashMap<>();

            Set<Vertex> vertices = vertexSet();
            int max = 0;
            for(Vertex v : vertices) {
                int temp = getLongestPathRec(v);
                if(temp > max) {
                    max = temp;
                }
            }
        return max;
    }


    private int getLongestPathRec(Vertex v) {
        int max = 0;
        Set<RouteEdge> edges = edgesOf(v);
        for(RouteEdge e : edges) {
            int dist = 0;
            if(!e.isVisited()) {
                e.setVisited(true);
                Vertex target = getEdgeTarget(e);
                if(target.equals(v)) {
                    target = getEdgeSource(e);
                }
                int callLength = (int) (getEdgeWeight(e));
                dist = callLength + getLongestPathRec(target);
                if(dist > max) {
                    max = dist;
                }
                e.setVisited(false);
            }
        }
        return max;
    }

    public Player checkDestCards(Player player) {

        for(DestinationCard card : player.getDestinationCards()) {
            boolean completed = checkCard(card);
            card.setCompleted(completed);
        }
        return player;
    }

    private boolean checkCard(DestinationCard card) {
        // check to see if the cards start and end routes are linked.
        String startCity = card.getStartCity();
        String endCity = card.getEndCity();
        Vertex startV = null;
        Vertex endV = null;

        boolean completed = false;

        for (Vertex v : vertexSet()) {
            if (startCity.equals(v.val)) {
                startV = v;
            } else if (endCity.equals(v.val)) {
                endV = v;
            }
        }
        // check if start and end v are connected.
        if(startV != null && endV != null)
        {
            DijkstraShortestPath<Vertex, RouteEdge> dijk = new DijkstraShortestPath<>(this);
            GraphPath<Vertex, RouteEdge> path = dijk.getPath(startV, endV);
            if(path != null) {
                completed = true;
            }
        }

        return completed;
    }
}
