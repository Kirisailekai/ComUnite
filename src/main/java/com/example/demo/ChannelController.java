package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;
    private final UserService userService;  // Инжектируем UserService

    @Autowired
    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;  // Инициализация через конструктор
    }

    // Получить канал по ID
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable Long id) {
        return channelService.getChannelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Получить все каналы
    @GetMapping
    public ResponseEntity<List<Channel>> getAllChannels() {
        List<Channel> channels = channelService.getAllChannels();
        return ResponseEntity.ok(channels);
    }

    // Получить каналы для конкретного сервера
    @GetMapping("/server/{serverId}")
    public ResponseEntity<List<Channel>> getChannelsByServerId(@PathVariable Long serverId) {
        List<Channel> channels = channelService.getChannelsByServerId(serverId);
        return ResponseEntity.ok(channels);
    }

    // Создать новый канал
    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        Channel createdChannel = channelService.createChannel(channel);
        return ResponseEntity.ok(createdChannel);
    }

    // Обновить канал
    @PutMapping("/{id}")
    public ResponseEntity<Channel> updateChannel(@PathVariable Long id, @RequestBody Channel channel) {
        return channelService.updateChannel(id, channel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить канал
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        if (channelService.deleteChannel(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Добавить участника в канал
    @PostMapping("/{channelId}/participants/{userId}")
    public ResponseEntity<Void> addParticipantToChannel(@PathVariable Long channelId, @PathVariable Long userId) {
        // Получаем пользователя по ID
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            channelService.addParticipantToChannel(channelId, user.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить участника из канала
    @DeleteMapping("/{channelId}/participants/{userId}")
    public ResponseEntity<Void> removeParticipantFromChannel(@PathVariable Long channelId, @PathVariable Long userId) {
        // Получаем пользователя по ID
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            channelService.removeParticipantFromChannel(channelId, user.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
