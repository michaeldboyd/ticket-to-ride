package com.example.sharedcode.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ali on 2/26/2018.
 */

public class ChatMessage implements Serializable {

    private static int idTracker = 0;

    public int id;
    public String message;
    public String senderName;
    public String timestamp;


    public ChatMessage(String _message, String _senderName, String _timestamp) {
        id = idTracker;
        message = _message;
        senderName = _senderName;
        timestamp = _timestamp;

        idTracker += 1;
    }
}
