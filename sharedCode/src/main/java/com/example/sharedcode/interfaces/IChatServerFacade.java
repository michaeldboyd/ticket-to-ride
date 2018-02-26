package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.ChatMessage;

public interface IChatServerFacade {
    void sendChatMessage(String message, String playerName);
    void sendIsTyping(String playerName, Boolean isTyping);
}
