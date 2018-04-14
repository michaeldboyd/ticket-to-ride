package com.example.sharedcode.interfaces.persistence;

import com.example.sharedcode.model.User;

public interface IUserDAO {
    User addUser(String userName, String password);
    /** Returns authtoken **/
    String login(String userName, String authToken);
    boolean logout(String userName);
    boolean joinGame(String userName, String gameID);
    boolean leaveGame(String userName, String gameID);
}