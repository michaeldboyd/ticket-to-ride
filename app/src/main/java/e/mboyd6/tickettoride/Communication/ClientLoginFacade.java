package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.CommandResult;
import com.example.sharedcode.interfaces.ILoginFacade;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLoginFacade implements ILoginFacade{
  private static ClientLoginFacade instance = new ClientLoginFacade();

  private ClientLoginFacade() {}

  //Static methods are so that we don't need to call the .instance() getter.
  public static CommandResult _login(String username, String password) {
    return instance.login(username, password);
  }
  public static CommandResult _register(String username, String password) {
    return instance.register(username, password);
  }


  @Override
  public CommandResult login(String username, String password) {
    //TODO use websocket to connect to server
    return null;
  }

  @Override
  public CommandResult register(String username, String password) {
    //TODO
    return null;
  }
}
