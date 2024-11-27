package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final ChannelService channelService;

    @Autowired
    public MessageController(ChannelService channelService) {
        this.channelService = channelService;
    }

    // Получить сообщение по ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = channelService.getMessageById(id);
        if (message.isPresent()) {
            return ResponseEntity.ok(message.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessagesByChannel(@RequestParam Long channelId) {
        List<Message> messages = channelService.getMessagesByChannel(channelId);
        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = channelService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (channelService.deleteMessage(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
