package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.ChatMessage;

public interface IChatServerFacade {
    void sendChatMessage(String authToken, String message, String playerName, String gameID);
    void sendIsTyping(String authToken, String gameID, String playerName, Boolean isTyping);
}
