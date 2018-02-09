package com.example.sharedcode.interfaces;

import com.example.sharedcode.communication.CommandResult;

/**
 * Created by eric on 2/7/18.
 */

public interface IClientLoginFacade {

    void login(String authToken, String message);
    void register(String authToken, String message);
}
