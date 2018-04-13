package com.example.sharedcode.interfaces.persistence;

public interface IDatabaseFactory {
    void initializeDatabase();
    IConnectionManager createConnectionManager();
    IUserDAO createUserDAO();
    IGameDAO createGameDAO();
    ICommandDAO createCommandDAO();
    void clearDatabase();
}
