package com.example.project3;

import java.util.ArrayList;
import java.util.List;

public class ChatHistory {
    private static ChatHistory instance;
    private final List<ChatMessage> messages = new ArrayList<>();

    private ChatHistory() {}

    public static ChatHistory getInstance() {
        if (instance == null) {
            instance = new ChatHistory();
        }
        return instance;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void clear() {
        messages.clear();
    }
}
