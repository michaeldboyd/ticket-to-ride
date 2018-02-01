package com.example.sharedcode.interfaces;

import com.example.sharedcode.communication.CommandResult;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public interface ILoginFacade {

  public CommandResult login(String username, String password);

  public CommandResult register(String username, String password);

}
