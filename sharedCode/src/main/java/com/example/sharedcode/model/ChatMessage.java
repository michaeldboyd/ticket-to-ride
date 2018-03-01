package com.example.sharedcode.model;


import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ali on 2/26/2018.
 */

public class ChatMessage {

    public String message;
    public String sender;
    public Timestamp timestamp;


    public ChatMessage(String _message, String _sender) {
        message = _message;
        sender = _sender;

        timestamp = new Timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toInstant().toEpochMilli());
    }
}
