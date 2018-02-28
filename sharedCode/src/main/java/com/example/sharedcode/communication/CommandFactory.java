package com.example.sharedcode.communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.model.UpdateType;

/**
 * Created by eric on 2/5/18.
 */

public class CommandFactory {

    /**
     *
     * @param authToken - always NULL from the client because authToken should be sent in a paramValues
     * @param className - the class on which the method will be called
     * @param methodName - name of method to be called on the class
     * @param paramTypesStringNames - String[] of class names of all parameters for the method to be called
     * @param paramValues - Object[] all actual parameters for the method to be called
     * @return - Command object
     */
    public static Command createCommand(String authToken, String className, String methodName, String[] paramTypesStringNames, Object[] paramValues) {
        return new Command(authToken, className, methodName, paramTypesStringNames, paramValues);
    }

}
