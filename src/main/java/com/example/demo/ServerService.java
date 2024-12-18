package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.List;   // Для списка
import java.util.Set;    // Для множества
import java.util.Map;    // Для отображения
import java.util.ArrayList;  // Для конкретной реализации списка
import java.util.HashSet;    // Для конкретной реализации множества
import java.util.HashMap;    // Для конкретной реализации отображения

@Service
public class ServerService {

    private final ServerRepository serverRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Autowired
    public ServerService(ServerRepository serverRepository,
                         UserRepository userRepository,
                         ChannelRepository channelRepository) {
        this.serverRepository = serverRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    // Получение сервера по ID
    public Optional<Server> getServerById(Long id) {
        return serverRepository.findById(id);
    }

    // Получение всех серверов
    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    // Создание нового сервера
    public Server createServer(Server server) {
        if (server.getOwner() != null && userRepository.existsById(server.getOwner().getId())) {
            return serverRepository.save(server);
        } else {
            throw new IllegalArgumentException("Owner not found or invalid");
        }
    }

    // Обновление сервера
    public Optional<Server> updateServer(Long id, Server updatedServer) {
        return serverRepository.findById(id).map(server -> {
            server.setName(updatedServer.getName());
            return serverRepository.save(server);
        });
    }

    // Удаление сервера
    public boolean deleteServer(Long id) {
        if (serverRepository.existsById(id)) {
            serverRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // **Добавление участника на сервер**
    public boolean addMemberToServer(Long serverId, Long userId) {
        Optional<Server> serverOptional = serverRepository.findById(serverId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (serverOptional.isPresent() && userOptional.isPresent()) {
            Server server = serverOptional.get();
            User user = userOptional.get();

            server.getMembers().add(user); // Добавляем участника в коллекцию
            serverRepository.save(server);
            return true;
        }
        return false;
    }

    // **Удаление участника с сервера**
    public boolean removeMemberFromServer(Long serverId, Long userId) {
        Optional<Server> serverOptional = serverRepository.findById(serverId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (serverOptional.isPresent() && userOptional.isPresent()) {
            Server server = serverOptional.get();
            User user = userOptional.get();

            if (server.getMembers().remove(user)) { // Удаляем участника из коллекции
                serverRepository.save(server);
                return true;
            }
        }
        return false;
    }

    // **Добавление канала на сервер**
    public boolean addChannelToServer(Long serverId, Channel channel) {
        Optional<Server> serverOptional = serverRepository.findById(serverId);

        if (serverOptional.isPresent()) {
            Server server = serverOptional.get();
            channel.setServer(server); // Устанавливаем сервер для канала
            channelRepository.save(channel); // Сохраняем канал в базе
            return true;
        }
        return false;
    }

    // **Удаление канала с сервера**
    public boolean removeChannelFromServer(Long serverId, Long channelId) {
        Optional<Server> serverOptional = serverRepository.findById(serverId);
        Optional<Channel> channelOptional = channelRepository.findById(channelId);

        if (serverOptional.isPresent() && channelOptional.isPresent()) {
            Channel channel = channelOptional.get();

            if (channel.getServer().getId().equals(serverId)) {
                channelRepository.delete(channel); // Удаляем канал из базы
                return true;
            }
        }
        return false;
    }
}
