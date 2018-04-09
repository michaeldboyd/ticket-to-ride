package com.example.sharedcode.interfaces.persistence;

public interface IDatabaseFactory {
    IConnectionManager createConnectionManager();
    IUserDAO createUserDAO();
    IGameDAO createGameDAO();
    ICommandDAO createCommandDAO();
    void clearDatabase();
}
