package com.example.sharedcode.communication;

import java.util.ArrayList;
import java.util.List;


public class CommandMessage {
    Command command;
    String message;
    Boolean success;


    /**
     * @param message
     * @param command
     */
    public CommandMessage(String message, Command command)
    {
        this.message = message;
        this.command = command;
        this.success = false; // false by default --> updated later by Command.execute()
    }

    public Command getCommand() {
        return command;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }
}
