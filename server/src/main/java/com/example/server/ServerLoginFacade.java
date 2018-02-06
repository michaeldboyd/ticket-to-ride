package com.example.server;

import com.example.sharedcode.communication.CommandResult;
import com.example.sharedcode.interfaces.ILoginFacade;
import com.example.sharedcode.model.User;

import java.util.UUID;

import Model.serverModel;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLoginFacade implements ILoginFacade {


  //TODO: Probably should be a singleton eventually


    /**
     * Checks to see if user is already logged in. If so, returns error message.
     *
     * If not, checks to make sure user already exists. If not, returns error message.
     *
     * If so, checks to make sure passwords match. If so, logs in user.
     *  If not, returns error message.
     *
     * @param username
     * @param password
     * @return
     */
  @Override
  public CommandResult login(String username, String password) {
      //New command result
      if (serverModel.instance().loggedInUsers.containsKey(username)){

          //CommandResult.setMessage("User already logged in.");

      }
      else{

          if(serverModel.instance().allUsers.containsKey(username)) {

              User user = serverModel.instance().allUsers.get(username);

              if(user.getPassword().equals(password)) {

                  //Do we want to reset authtoken each time?
                  UUID uuid = UUID.randomUUID();
                  serverModel.instance().allUsers.get(username).setAuthtoken(uuid.toString());
                  serverModel.instance().loggedInUsers.put(user.getUsername(), user);
                  //CommandResult result = new CommandResult();

              }
              else{

                  //CommandResult.setMessage("Incorrect password.");

              }
          }
          else{
              //CommandResult.setMessage("User does not exist.");

          }

      }

      return null;
  }


    /**
     * Checks if username is already taken. If it is, returns error message.
     *
     * If not, creates a new user and adds user to map of all the users and
     *  list of logged in users.
     *
     * @param username
     * @param password
     * @return
     */
  @Override
  public CommandResult register(String username, String password) {
      //New command result
      if (serverModel.instance().allUsers.containsKey(username)){
            //CommandResult.setMessage("Username already registered.");
      }
      else{
          User user = new User();
          user.setUsername(username);
          user.setPassword(password);
          UUID uuid = UUID.randomUUID();
          user.setAuthtoken(uuid.toString());
          UUID uuid2 = UUID.randomUUID();
          user.setUserID(uuid2.toString());
          serverModel.instance().allUsers.put(username, user);

          serverModel.instance().loggedInUsers.put(username, user);

      }

      return null;
  }

}
