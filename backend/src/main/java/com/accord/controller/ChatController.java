package com.accord.controller;

import com.accord.model.ChatMessage;
import com.accord.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
public class ChatController {

    @Value("${app.message.max-length}")
    private int maxMessageLength;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(Map<String, String> payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload must not be null");
        }

        String username = payload.get("username");
        String content = payload.get("content");

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must be provided and non-empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content must be provided and non-empty");
        }
        if (content.length() > maxMessageLength) {
            throw new IllegalArgumentException("Content exceeds maximum length of " + maxMessageLength);
        }
        
        return chatService.saveMessage(username, content);
    }

    @MessageMapping("/chat.join")
    @SendTo("/topic/messages")
    public ChatMessage userJoin(Map<String, String> payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload must not be null");
        }
        
        String username = payload.get("username");
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("The 'username' field must be provided and non-empty");
        }
        
        return chatService.saveMessage("System", username + " has joined the chat");
    }
}

@RestController
@CrossOrigin(origins = "${app.cors.allowed-origins}")
class MessageRestController {
    
    private static final int MAX_LIMIT = 500;

    @Autowired
    private ChatService chatService;

    @GetMapping("/api/messages")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @RequestParam(defaultValue = "50") int limit) {
        
        if (limit < 1) {
            limit = 1;
        } else if (limit > MAX_LIMIT) {
            limit = MAX_LIMIT;
        }
        
        List<ChatMessage> messages = chatService.getRecentMessages(limit);
        return ResponseEntity.ok(messages);
    }
}
