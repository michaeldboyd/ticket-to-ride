package Model;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;

import java.util.ArrayList;
import java.util.Map;

public class LongestPathAlgorithm {

    public static Game updateLongestPathsInGame(Game game) {
        ArrayList<Player> players = game.getPlayers();
        for(Map.Entry<Route, Player> e : game.getRoutesClaimed().entrySet()) {
            // get the routes into a map with each route and all

            //every player has their own graph of routes claimed. our goal is to figure out which person has the longest dfs
        }

        return null;
    }
}

/**
 * make my own tree for each person. with connected cities
 * iterate through each tree to see who's is longestp
 */
