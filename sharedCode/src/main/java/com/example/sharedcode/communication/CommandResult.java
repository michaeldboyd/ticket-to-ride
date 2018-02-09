package com.example.sharedcode.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael boyd on 1/31/2018.
 */

public class CommandResult {
    Command command;
    String message;
    Boolean success;


    /**
     * @param message
     * @param command
     */
    public CommandResult(String message, Command command)
    {
        this.message = message;
        this.command = command;
        this.success = false; // false by default --> updated later by Command.execute()
    }

}
