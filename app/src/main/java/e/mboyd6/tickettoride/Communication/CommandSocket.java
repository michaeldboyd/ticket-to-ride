package e.mboyd6.tickettoride.Communication;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandMessage;
import com.google.gson.Gson;


import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import e.mboyd6.tickettoride.Model.ClientModel;

@ClientEndpoint
@ServerEndpoint(value="/echo/")
public class CommandSocket
{
    Gson gson = new Gson();
    @OnOpen
    public void onWebSocketConnect(Session sess)
    {
        System.out.println("Client Connected!");
        // TODO: linke each session with the appropriate user. this is where it all starts
    }

    @OnMessage
    public void onWebSocketText(String message)
    {
        Command res = gson.fromJson(message, Command.class);
        try {
            res.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("Socket Closed: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }

    // NOTE: Dont use these function right now, use the other ones. They need to be here though
}
