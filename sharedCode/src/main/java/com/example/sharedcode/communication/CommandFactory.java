package com.example.sharedcode.communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.model.UpdateType;

/**
 * Created by eric on 2/5/18.
 */

public class CommandFactory {

    public static Command createCommand(String authToken, String className, String methodName, String[] paramTypesStringNames, Object[] paramValues) {
        return new Command(authToken, className, methodName, paramTypesStringNames, paramValues);
    }

}
