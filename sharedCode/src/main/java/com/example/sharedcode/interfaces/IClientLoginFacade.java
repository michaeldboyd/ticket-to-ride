package com.example.sharedcode.interfaces;

/**
 * Created by eric on 2/7/18.
 */

public interface IClientLoginFacade {

    void login(String authToken, String message);
    void register(String authToken, String message);
    void logout(String message);

    void initSocket(String id);
}
