package com.example.sharedcode.interfaces.persistence;

import com.example.sharedcode.communication.Command;

import java.util.ArrayList;
import java.util.List;

public interface  ICommandDAO {
    ArrayList<Command> getCommands(String gameID);
    void storeGameCommand(Command command, String gameID);
    List<Command> getGameCommands(String gameID);
    boolean clearGameCommands(String gameID);
}
