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
    private final UserService userService;

    @Autowired
    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable Long id) {
        return channelService.getChannelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Channel>> getAllChannels() {
        List<Channel> channels = channelService.getAllChannels();
        return ResponseEntity.ok(channels);
    }

    @GetMapping("/server/{serverId}")
    public ResponseEntity<List<Channel>> getChannelsByServerId(@PathVariable Long serverId) {
        List<Channel> channels = channelService.getChannelsByServerId(serverId);
        return ResponseEntity.ok(channels);
    }

    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        Channel createdChannel = channelService.createChannel(channel);
        return ResponseEntity.ok(createdChannel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Channel> updateChannel(@PathVariable Long id, @RequestBody Channel channel) {
        return channelService.updateChannel(id, channel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        if (channelService.deleteChannel(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{channelId}/participants/{userId}")
    public ResponseEntity<Void> addParticipantToChannel(@PathVariable Long channelId, @PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            channelService.addParticipantToChannel(channelId, user.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{channelId}/participants/{userId}")
    public ResponseEntity<Void> removeParticipantFromChannel(@PathVariable Long channelId, @PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            channelService.removeParticipantFromChannel(channelId, user.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
