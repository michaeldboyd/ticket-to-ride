package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.ChatMessage;

public interface IChatClientFacade {
    void chatMessageReceived(ChatMessage chatMessage, String gameID);
    void isTypingReceived(String playerName, Boolean isTyping);
}