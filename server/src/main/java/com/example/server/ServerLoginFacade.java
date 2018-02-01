package com.example.server;

import com.example.sharedcode.communication.CommandResult;
import com.example.sharedcode.interfaces.ILoginFacade;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLoginFacade implements ILoginFacade {

  /*
    Login will check if the user is currently in the Model's user list, and if not, add the user.
   */
  //TODO: Probably should be a singleton eventually

  @Override
  public CommandResult login(String username, String password) {

    return null;
  }

  @Override
  public CommandResult register(String username, String password) {
    return null;
  }

}
