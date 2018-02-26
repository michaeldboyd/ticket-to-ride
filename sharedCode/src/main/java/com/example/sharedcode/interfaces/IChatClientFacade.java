package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.ChatMessage;

public interface IChatClientFacade {
    void chatMessageReceived(ChatMessage chatMessage);
    void isTypingReceived(String playerName, Boolean isTyping);
}
