package com.example.sharedcode.interfaces.persistence;

import com.example.sharedcode.model.User;

import java.util.List;

public interface IUserDAO {
    User getUser(String userName);
    List<User> getAllUsers();
    User addUser(String userName, String password);
    /** Returns authtoken **/
    String login(String userName, String authToken);
    boolean logout(String userName);
    boolean joinGame(String userName, String gameID);
    boolean leaveGame(String userName, String gameID);
}