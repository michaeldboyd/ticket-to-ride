package com.example.sharedcode.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by jonathanlinford on 3/19/18.
 */

public class GameTest {

    Game game = null;

    @Before
    public void setUp(){
        game = new Game();

        for(int i = 0; i < 4; i++){
            String name = "player" + i;
            Player p = new Player(name, name, i);
            game.addPlayer(p);
        }
    }

    @After
    public void tearDown(){
        game = null;
    }

    private void initGame(){
        GameInitializer init = new GameInitializer();

        init.initializeGame(game);
    }

    @Test
    public void createGameTest(){
        initGame();

        assertTrue(game.getPlayers().get(0).getName().equals(game.getCurrentTurnPlayerName()));

        game.changeTurnForGame();

        assertTrue(game.getPlayers().get(1).getName().equals(game.getCurrentTurnPlayerName()));
    }

    @Test
    public void gameEndTest(){
        initGame();

        //change the next to have 1 train
        game.getPlayers().get(0).setTrains(1);

        //Player0
        assertTrue(game.getPlayers().get(0).getName().equals(game.getCurrentTurnPlayerName()));

        //Player1
        game.changeTurnForGame();
        assertTrue(game.getPlayers().get(1).getName().equals(game.getCurrentTurnPlayerName()));
        assertTrue(game.isLastRound());

        //Player2
        game.changeTurnForGame();
        assertTrue(game.getPlayers().get(2).getName().equals(game.getCurrentTurnPlayerName()));
        assertTrue(game.isLastRound());

        //Player3
        game.changeTurnForGame();
        assertTrue(game.getPlayers().get(3).getName().equals(game.getCurrentTurnPlayerName()));
        assertTrue(game.isLastRound());

        //Player0 - Player0 gets one last turn, even though he caused the end game
        game.changeTurnForGame();
        assertTrue(game.getPlayers().get(0).getName().equals(game.getCurrentTurnPlayerName()));
        assertTrue(game.isLastRound());

        //game should be over
        game.changeTurnForGame();
        assertTrue(game.isDone());
    }
}
