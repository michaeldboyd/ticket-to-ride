package com.example.sharedcode.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael boyd on 1/31/2018.
 */

public class CommandResult {
    List<Command> commands = new ArrayList<Command>();
    String message;
    Boolean success;


    /**
     * @param message
     * @param commands
     */
    public CommandResult(String message, List<Command> commands)
    {
        this.message = message;
        this.commands = commands;
        this.success = false; // false by default --> updated later by Command.execute()
    }

}
