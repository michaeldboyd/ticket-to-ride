package com.example.sharedcode.communication;

import com.example.sharedcode.communication.Command;

/**
 * Created by eric on 2/5/18.
 */

public class CommandFactory {

    public static Command createCommand(String className, String methodName, Object[] paramTypesStringNames, Object[] paramValues) {
        return new Command(className, methodName, paramTypesStringNames, paramValues);
    }

}
