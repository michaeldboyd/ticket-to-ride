package com.example.sharedcode.model;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class Score {
    public int destCardPoints = 0;
    public int destCardDeductions = 0;
    private int routePoints = 0;
    private int trains = 0;
    private int cards = 0;
    private int routes = 0;
    private boolean longestRoute = false;

    public int getPoints() {
        return routePoints;
    }

    public void setPoints(int routePoints) {
        this.routePoints = routePoints;
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
        this.routePoints += computePoints(route);
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
