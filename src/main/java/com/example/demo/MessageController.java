package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final ChannelService channelService;  // Используем ChannelService

    @Autowired
    public MessageController(ChannelService channelService) {
        this.channelService = channelService;  // Инициализируем сервис
    }

    // Получить сообщение по ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = channelService.getMessageById(id);  // Используем ChannelService
        if (message.isPresent()) {
            return ResponseEntity.ok(message.get());  // Возвращаем сообщение, если оно найдено
        } else {
            return ResponseEntity.notFound().build();  // Возвращаем 404, если сообщение не найдено
        }
    }

    // Получить все сообщения для конкретного канала
    @GetMapping
    public ResponseEntity<List<Message>> getMessagesByChannel(@RequestParam Long channelId) {
        List<Message> messages = channelService.getMessagesByChannel(channelId);  // Используем ChannelService
        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build();  // Возвращаем 404, если сообщений нет
        }
        return ResponseEntity.ok(messages);  // Возвращаем найденные сообщения
    }

    // Создать новое сообщение
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = channelService.createMessage(message);  // Используем ChannelService
        return ResponseEntity.ok(createdMessage);  // Возвращаем созданное сообщение
    }

    // Удалить сообщение
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (channelService.deleteMessage(id)) {
            return ResponseEntity.ok().build();  // Если удалено, возвращаем 200 OK
        } else {
            return ResponseEntity.notFound().build();  // Если не найдено, возвращаем 404
        }
    }
}
