package com.example.sharedcode.model;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class Score {
    int points = 0;
    int trains = 0;
    int cards = 0;
    int routes = 0;
    boolean longestRoute = false;

    public Score() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTrains() {
        return trains;
    }

    public void setTrains(int trains) {
        this.trains = trains;
    }

    public int getCards() {
        return cards;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }

    public int getRoutes() {
        return routes;
    }

    public void setRoutes(int routes) {
        this.routes = routes;
    }

    public boolean isLongestRoute() {
        return longestRoute;
    }

    public void setLongestRoute(boolean longestRoute) {
        this.longestRoute = longestRoute;
    }


    public void addPoints(Route route) {
        this.points += computePoints(route);
    }

    public int computePoints(Route route){
        int points = 0;
        switch(route.getNumberTrains()){
            case 1:
                points = 1;
                break;
            case 2:
                points = 2;
                break;
            case 3:
                points = 4;
                break;
            case 4:
                points = 7;
                break;
            case 5:
                points = 10;
                break;
            case 6:
                points = 15;
                break;
        }

        return points;
    }
}
