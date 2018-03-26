package Facades;

import Communication.SocketManager;
import Model.LongestPathAlgorithm;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerGameplayFacade;
import com.example.sharedcode.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerGameplay implements IServerGameplayFacade {
    private static ServerGameplay ourInstance = new ServerGameplay();
    private static ServerModel model = ServerModel.instance();

    public static ServerGameplay getInstance() {
        return ourInstance;
    }

    private ServerGameplay() {
    }

    private final String CLASS_NAME = "e.mboyd6.tickettoride.Facades.ClientGameplay";

    public static void _discardDestinationCard(String authToken, String gameID, Player player, ArrayList<DestinationCard> discardCards) {
        ourInstance.discardDestinationCard(authToken, gameID, player, discardCards);
    }

    public static void _claimRoute(String authToken, String gameID, Player player, HashMap routesClaimed) {
        Map<Route, Player> routesClaimedCasted = (Map<Route, Player>) routesClaimed;
        ourInstance.claimRoute(authToken, gameID, player, routesClaimedCasted);
    }

    public static void _updateTrainCards(String authToken, String gameID, Player player, FaceUpDeck faceUpDeck, TrainCardDeck trainCardDeck, TrainCardDeck trainDiscardDeck) {
        ourInstance.updateTrainCards(authToken, gameID, player, faceUpDeck, trainCardDeck, trainDiscardDeck);
    }

    public static void _updateDestinationCards(String authToken, String gameID, Player player, DestinationDeck destinationDeck) {
        ourInstance.updateDestinationCards(authToken, gameID, player, destinationDeck);
    }

    public static void _endGameEarly(String authToken, String gameID) {
        ourInstance.endGameEarly(authToken, gameID);
    }



    @Override
    public void discardDestinationCard(String authToken, String gameID, Player player, ArrayList<DestinationCard> discardCards) {
        String message = "";

        Game currentGame = null;
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            currentGame = ServerModel.instance().getGames().get(gameID);

            for (DestinationCard card: discardCards) {
                currentGame.getDestinationDeck().returnDiscarded(card);
            }

            currentGame.updatePlayer(player);

            if(player.hasStartedTurns) {
                currentGame.changeTurnForGame();
            }
            else {
                player.hasStartedTurns = true;
            }

            currentGame.getHistory().add(player.getName() + " drew "+ (3-discardCards.size()) + " destination card.");
        } else {
            message = "Game not found on server";
        }

        sendGameUpdate(authToken,currentGame, message);
    }

    @Override
    public void claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed) {
        //System.out.println("claimRoute called");
        String message = "";

        Game currentGame = null;
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            currentGame = ServerModel.instance().getGames().get(gameID);

            currentGame.setRoutesClaimed(routesClaimed);

            // maybe put this line into the end game function if it ends up taking too long.
            currentGame = LongestPathAlgorithm.update(currentGame);

            currentGame.updatePlayer(player);
            currentGame.getHistory().add(player.getName() + " claimed a route.");
            currentGame.checkFinalRound();

            //This method will end the game if it is needed
            currentGame.changeTurnForGame();
        } else {
            message = "Game not found on server";
        }

        if(currentGame != null && currentGame.isDone()) {
            endGame(authToken, currentGame, message);
        } else {
            sendGameUpdate(authToken,currentGame, message);
        }
    }

    @Override
    public void updateTrainCards(String authToken, String gameID, Player player, FaceUpDeck faceUpDeck, TrainCardDeck trainCardDeck, TrainCardDeck trainDiscardDeck) {
        //System.out.println("updateTrainCards called");

        String message = "";

        Game currentGame = null;
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            currentGame = ServerModel.instance().getGames().get(gameID);

            currentGame.updatePlayer(player);
            currentGame.setFaceUpDeck(faceUpDeck);
            currentGame.setTrainCardDeck(trainCardDeck);
            currentGame.setTrainDiscardDeck(trainDiscardDeck);
            currentGame.getHistory().add(player.getName() + " drew train cards.");
            //This method will end the game if it is needed
            currentGame.changeTurnForGame();
        } else {
            message = "Game not found on server";
        }

        if(currentGame != null && currentGame.isDone()) {
            endGame(authToken, currentGame, message);
        } else {
            sendGameUpdate(authToken,currentGame, message);
        }
    }

    @Override
    public void updateDestinationCards(String authToken, String gameID, Player player, DestinationDeck destinationDeck) {
       //System.out.println("updateDestCards called");

        String message = "";

        Game currentGame = null;
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            currentGame = ServerModel.instance().getGames().get(gameID);
            //get the card count
            Player oldPlayer = currentGame.getPlayer(player.getName());
            int cardCount = player.getDestinationCards().size() - oldPlayer.getDestinationCards().size();

            currentGame.updatePlayer(player);
            currentGame.setDestinationDeck(destinationDeck);

            //This method will end the game if it is needed
            currentGame.changeTurnForGame();

            if(cardCount > 0)
                currentGame.getHistory().add(player.getName() + " drew " + cardCount + " destination card(s).");
            else if(cardCount < 0)
                currentGame.getHistory().add(player.getName() + " discarded " + cardCount + " destination card(s).");
        } else {
            message = "Game not found on server";
        }

        if(currentGame != null && currentGame.isDone()) {
            endGame(authToken, currentGame, message);
        } else {
            sendGameUpdate(authToken,currentGame, message);
        }
    }

    @Override
    public void endGameEarly(String authToken, String gameID) {
        String message = "";
        Game currentGame = null;

        if (ServerModel.instance().getGames().containsKey(gameID)) {
            currentGame = ServerModel.instance().getGames().get(gameID);

            currentGame.setDone(true);
        } else {
            message = "Game not found on server";
        }

        endGame(authToken, currentGame, message);
    }

    public void endGame(String authToken, Game currentGame, String message) {
        //calculate the destination card scores and longest path score and
        currentGame = calculateFinalScores(currentGame);
        String[] paramTypes = {currentGame.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {currentGame, message};
        Command command = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_endGame", paramTypes, paramValues);
        SocketManager.instance().notifyPlayersInGame(currentGame.getGameID(), command);
    }


    public void sendGameUpdate(String authToken, Game currentGame, String message) {
        //System.out.println("sendGameUpdate called");

        String[] paramTypes = {currentGame.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {currentGame, message};
        Command command = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_updateGame", paramTypes, paramValues);
        SocketManager.instance().notifyPlayersInGame(currentGame.getGameID(), command);
    }

    private Game calculateFinalScores(Game currentGame) {
        for(Player p : currentGame.getPlayers()) {
            // add points if dest card was complete, remove if not.
            for(DestinationCard card : p.getDestinationCards()) {
                if(card.isCompleted()) {
                    p.getScore().destCardPoints += card.getPoints();
                } else {
                    p.getScore().destCardDeductions += card.getPoints();
                }
            }
        }

        return currentGame;
    }
}

