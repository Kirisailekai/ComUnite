package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.List;   // Для списка
import java.util.Set;    // Для множества
import java.util.Map;    // Для отображения
import java.util.ArrayList;  // Для конкретной реализации списка
import java.util.HashSet;    // Для конкретной реализации множества
import java.util.HashMap;    // Для конкретной реализации отображения

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    private final ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Server> getServerById(@PathVariable Long id) {
        return serverService.getServerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Server>> getAllServers() {
        return ResponseEntity.ok(serverService.getAllServers());
    }

    @PostMapping
    public ResponseEntity<Server> createServer(@RequestBody Server server) {
        Server createdServer = serverService.createServer(server);
        return ResponseEntity.ok(createdServer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Server> updateServer(@PathVariable Long id, @RequestBody Server server) {
        return serverService.updateServer(id, server)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable Long id) {
        if (serverService.deleteServer(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // **Управление участниками**

    @PostMapping("/{serverId}/members/{userId}")
    public ResponseEntity<String> addMember(@PathVariable Long serverId, @PathVariable Long userId) {
        if (serverService.addMemberToServer(serverId, userId)) {
            return ResponseEntity.ok("Member added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add member");
        }
    }

    @DeleteMapping("/{serverId}/members/{userId}")
    public ResponseEntity<String> removeMember(@PathVariable Long serverId, @PathVariable Long userId) {
        if (serverService.removeMemberFromServer(serverId, userId)) {
            return ResponseEntity.ok("Member removed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to remove member");
        }
    }

    // **Управление каналами**

    @PostMapping("/{serverId}/channels")
    public ResponseEntity<String> addChannel(@PathVariable Long serverId, @RequestBody Channel channel) {
        if (serverService.addChannelToServer(serverId, channel)) {
            return ResponseEntity.ok("Channel added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add channel");
        }
    }

    @DeleteMapping("/{serverId}/channels/{channelId}")
    public ResponseEntity<String> removeChannel(@PathVariable Long serverId, @PathVariable Long channelId) {
        if (serverService.removeChannelFromServer(serverId, channelId)) {
            return ResponseEntity.ok("Channel removed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to remove channel");
        }
    }
}
