package com.example.sharedcode.interfaces.persistence;

import com.example.sharedcode.model.User;

import java.util.List;

public interface IUserDAO {
    User getUser(String userName);
    List<User> getAllUsers();
    User addUser(User user);

    void updateUser(String userName, User user, String gameID);

    /** Returns authtoken **/
    String login(String userName, String authToken);
    boolean logout(String userName);
    boolean joinGame(String userName, String gameID);
    boolean leaveGame(String userName, String gameID);
}