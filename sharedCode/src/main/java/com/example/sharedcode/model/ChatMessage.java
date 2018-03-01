package com.example.sharedcode.model;



/**
 * Created by Ali on 2/26/2018.
 */

public class ChatMessage {

    public String message;
    public String sender;
    public String timestamp;


    public ChatMessage(String _message, String _sender, String time) {
        message = _message;
        sender = _sender;
        timestamp = time;

    }
}
