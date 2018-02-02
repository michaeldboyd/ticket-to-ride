package com.example.server;

import com.example.sharedcode.communication.CommandResult;
import com.example.sharedcode.interfaces.ILoginFacade;

import Model.serverModel;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLoginFacade implements ILoginFacade {


  //TODO: Probably should be a singleton eventually

  /*
    Login will check if the user is currently in the Model's user list, and if not, add the user.
   */
  @Override
  public CommandResult login(String username, String password) {

    return null;
  }

  @Override
  public CommandResult register(String username, String password) {
      if (serverModel.instance().allUsers.containsKey(username)){
          //return error
      }
      else{
          serverModel.instance().allUsers.put(username, password);
          
      }

      return null;
  }

}
