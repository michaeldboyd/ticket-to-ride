package com.example.sharedcode.communication;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.UpdateType;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ali on 2/2/2018.
 */


interface ICommand {
    void execute() throws Exception;
}


public class Command implements ICommand, Serializable {
    private String _className;
    private String _methodName;
    private String[] _paramTypesStringNames;
    private Object[] _paramValues;
    private String _authToken;


    /**
     * Commands should only ever execute static methods on classes.
     * This ensures that we dont' have issues
     *
     *
     * @param className - the name of the facade class that will call the method (full name--e.g. "String" ==> "java.lang.String"
     * @param methodName - the name method to be called on the facade class
     * @param paramTypesStringNames - String names of parameter types for the method-->converted to Class<?>[]
     * @param paramValues -- array of actual objects for parameter values passed into the method
     *
     *
     * @throws ClassNotFoundException - thrown if improper class name is passed in
     */
    public Command(String authToken, String className, String methodName,
                   String[] paramTypesStringNames, Object[] paramValues) {
        _authToken = authToken;

        _className = className;
        _methodName = methodName;
        _paramTypesStringNames = paramTypesStringNames;
        _paramValues = paramValues;
    }

    public String get_authToken() {
        return _authToken;
    }

    public void set_authToken(String _authToken) {
        this._authToken = _authToken;
    }

    public String get_className(){
        return _className;
    }

    public String[] get_paramTypesStringNames() {
        return _paramTypesStringNames;
    }

    public Object[] get_paramValues() {
        return _paramValues;
    }

    /**
     *
     * @throws Exception - thrown if any errors occur in trying to execute this command (e.g. ClassNotFoundException)
     */
    @Override
    public void execute() throws Exception {
        Class<?> receiver;

        System.out.println("execute received");
        switch (_className) {
            case "java.lang.String":
                receiver = String.class;
                break;

            case "int":
                receiver = int.class;
                break;

            case "boolean":
                receiver = boolean.class;
                break;

            default:
                receiver = Class.forName(_className);
                break;
        }

        if (_paramTypesStringNames != null && _paramTypesStringNames.length > 0) {
            Class<?>[] paramTypes = new Class<?>[_paramTypesStringNames.length];

            for (int i = 0; i < _paramTypesStringNames.length; i++) {
                String classStringName = _paramTypesStringNames[i];
                String className = classStringName.replace("class ", "");

                Class paramClass;
                switch (className) {
                    case "int":
                        paramClass = int.class;
                        break;

                    case "boolean":
                        paramClass = boolean.class;
                        break;

                    default:
                        paramClass = Class.forName(className);
                        break;
                }
                paramTypes[i] = paramClass;
            }

            Method method = receiver.getMethod(_methodName, paramTypes);
            method.invoke(null, _paramValues);
        } else {
            Method method = receiver.getMethod(_methodName, null);
            method.invoke(null, null);
        }
    }
}

