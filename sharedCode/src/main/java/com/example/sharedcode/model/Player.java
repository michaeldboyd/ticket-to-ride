package com.example.sharedcode.model;

import java.awt.Color;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Player {

    public Player(String playerID, String name, int color) {
        this.playerID = playerID;
        this.name = name;
        this.color = color;
    }

    private String playerID;
    private String name;
    private int color;
    private Score score = new Score();

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}