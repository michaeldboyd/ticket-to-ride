package Facades;

import Communication.SocketManager;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerGameplayFacade;
import com.example.sharedcode.model.*;
import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;
import java.util.Map;

public class ServerGameplay implements IServerGameplayFacade {
    private static ServerGameplay ourInstance = new ServerGameplay();

    public static ServerGameplay getInstance() {
        return ourInstance;
    }

    private ServerGameplay() {
    }

    private final String CLASS_NAME = "e.mboyd6.tickettoride.Facades.ClientGameplay";

    public static void _claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed) {
        ourInstance.claimRoute(authToken, gameID, player, routesClaimed);
    }

    public static void _updateTrainCards(String authToken, String gameID, String playerID) {
        //ourInstance.drawTrainCards(authToken, gameID, playerID);
    }

    public static void _updateDestinationCards(String authToken, String gameID, String playerID) {
        //ourInstance.drawDestinationCard(authToken, gameID, playerID);
    }

    @Override
    public void claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed) {
        String message = "";

        Game currentGame = null;
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            currentGame = ServerModel.instance().getGames().get(gameID);

            currentGame.setRoutesClaimed(routesClaimed);
            currentGame.updatePlayer(player);
            currentGame.getHistory().add(player.getName() + " claimed a route.");
            currentGame.changeTurnForGame();
        } else {
            message = "Game not found on server";
        }

        String[] paramTypes = {currentGame.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {currentGame, message};
        Command command = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_updateGame", paramTypes, paramValues);
        SocketManager.instance().notifyPlayersInGame(gameID, command);
    }

    @Override
    public void updateTrainCards(String authToken, String gameID, Player player, FaceUpDeck faceUpDeck, TrainCardDeck trainCardDeck, TrainCardDeck trainDiscardDeck) {
        String message = "";

        Game currentGame = null;
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            currentGame = ServerModel.instance().getGames().get(gameID);

            currentGame.updatePlayer(player);
            currentGame.setFaceUpDeck(faceUpDeck);
            currentGame.setTrainCardDeck(trainCardDeck);
            currentGame.setTrainDiscardDeck(trainDiscardDeck);
            currentGame.getHistory().add(player.getName() + " drew train cards.");
            currentGame.changeTurnForGame();
        } else {
            message = "Game not found on server";
        }

        String[] paramTypes = {currentGame.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {currentGame, message};
        Command command = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_updateGame", paramTypes, paramValues);
        SocketManager.instance().notifyPlayersInGame(gameID, command);
    }

    @Override
    public void updateDestinationCards(String authToken, String gameID, Player player, DestinationDeck destinationDeck) {
        String message = "";

        Game currentGame = null;
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            currentGame = ServerModel.instance().getGames().get(gameID);
            //get the card count
            Player oldPlayer = currentGame.getPlayer(player.getName());
            int cardCount = player.getDestinationCards().size() - oldPlayer.getDestinationCards().size();

            currentGame.updatePlayer(player);
            currentGame.setDestinationDeck(destinationDeck);
            currentGame.changeTurnForGame();

            if(cardCount > 0)
                currentGame.getHistory().add(player.getName() + " drew " + cardCount + " destination card(s).");
            else if(cardCount < 0)
                currentGame.getHistory().add(player.getName() + " discarded " + cardCount + " destination card(s).");
        } else {
            message = "Game not found on server";
        }

        String[] paramTypes = {currentGame.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {currentGame, message};
        Command command = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_updateGame", paramTypes, paramValues);
        SocketManager.instance().notifyPlayersInGame(gameID, command);
    }

}

