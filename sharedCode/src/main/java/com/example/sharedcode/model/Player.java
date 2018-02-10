package com.example.sharedcode.model;

import java.awt.Color;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Player {

    public Player(String playerID, String name, PlayerColors color) {
        this.playerID = playerID;
        this.name = name;
        this.color = color;
    }

    private String playerID;
    private String name;
    private PlayerColors color;

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
}