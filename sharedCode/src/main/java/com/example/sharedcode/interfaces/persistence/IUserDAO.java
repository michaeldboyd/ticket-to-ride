package com.example.sharedcode.interfaces.persistence;

import com.example.sharedcode.model.User;

public interface IUserDAO {
    User addUser(String userName, String password);
    /** Returns authtoken **/
    String login(String userName, String password);
    boolean logout(String authToken);
    boolean joinGame(String gameID);
    void clearDatabase();
}