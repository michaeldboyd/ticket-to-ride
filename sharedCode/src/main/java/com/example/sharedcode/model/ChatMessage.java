package com.example.sharedcode.model;


import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ali on 2/26/2018.
 */

public class ChatMessage {

    private String message;
    private String sender;
    private Timestamp timestamp;


    public ChatMessage(String _message, String _sender) {
        message = _message;
        sender = _sender;

        timestamp = new Timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toInstant().toEpochMilli());
    }
}
