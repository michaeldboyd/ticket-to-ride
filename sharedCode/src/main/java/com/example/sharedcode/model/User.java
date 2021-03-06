package com.example.sharedcode.model;

import java.io.Serializable;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class User implements Serializable {

    private String username;
    private String password;
    private String authtoken;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}