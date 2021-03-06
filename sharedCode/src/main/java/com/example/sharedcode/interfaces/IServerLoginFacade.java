package com.example.sharedcode.interfaces;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public interface IServerLoginFacade {

  void login(String username, String password, String socketID);
  void register(String username, String password, String socketID);
  void logout(String authToken);

}
