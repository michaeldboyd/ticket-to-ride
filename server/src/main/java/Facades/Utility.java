package Facades;

import Model.ServerModel;
import com.example.sharedcode.interfaces.IUtility;

public class Utility implements IUtility{

    public static void _clearServer(String password)
    {
        new Utility().clearServer(password);
    }
    @Override
    public void clearServer(String password) {
        if(password.equals(ServerModel.instance().getTestPassword()))
        {
            //Clears the entirety of the root server model
            ServerModel.instance().getAllUsers().clear();
            ServerModel.instance().getLoggedInUsers().clear();
            ServerModel.instance().getLoggedInSessions().clear();
            ServerModel.instance().getAuthTokenToUsername().clear();
            ServerModel.instance().getGames().clear();
            ServerModel.instance().getChatMessagesForGame().clear();
        }

    }
}
