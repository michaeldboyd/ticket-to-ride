package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.ChatMessage;

import java.util.ArrayList;

public interface IChatClientFacade {
    void chatMessageReceived(ChatMessage chatMessage, String gameID);
    void isTypingReceived(String playerName, Boolean isTyping);
    void chatHistoryReceived(ArrayList<ChatMessage> cm, String gameID);
}
