package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Optional<Server> getServerById(Long id) {
        return serverRepository.findById(id);
    }

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    public Server createServer(Server server) {
        if (server.getOwner() != null && userRepository.existsById(server.getOwner().getId())) {
            return serverRepository.save(server);
        } else {
            throw new IllegalArgumentException("Owner not found or invalid");
        }
    }

    public Optional<Server> updateServer(Long id, Server updatedServer) {
        return serverRepository.findById(id).map(server -> {
            server.setName(updatedServer.getName());
            return serverRepository.save(server);
        });
    }

    public boolean deleteServer(Long id) {
        if (serverRepository.existsById(id)) {
            serverRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addMemberToServer(Long serverId, Long userId) {
        Optional<Server> serverOptional = serverRepository.findById(serverId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (serverOptional.isPresent() && userOptional.isPresent()) {
            Server server = serverOptional.get();
            User user = userOptional.get();

            server.getMembers().add(user);
            serverRepository.save(server);
            return true;
        }
        return false;
    }

    public boolean removeMemberFromServer(Long serverId, Long userId) {
        Optional<Server> serverOptional = serverRepository.findById(serverId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (serverOptional.isPresent() && userOptional.isPresent()) {
            Server server = serverOptional.get();
            User user = userOptional.get();

            if (server.getMembers().remove(user)) {
                serverRepository.save(server);
                return true;
            }
        }
        return false;
    }

    public boolean addChannelToServer(Long serverId, Channel channel) {
        Optional<Server> serverOptional = serverRepository.findById(serverId);

        if (serverOptional.isPresent()) {
            Server server = serverOptional.get();
            channel.setServer(server);
            channelRepository.save(channel);
            return true;
        }
        return false;
    }

    public boolean removeChannelFromServer(Long serverId, Long channelId) {
        Optional<Server> serverOptional = serverRepository.findById(serverId);
        Optional<Channel> channelOptional = channelRepository.findById(channelId);

        if (serverOptional.isPresent() && channelOptional.isPresent()) {
            Channel channel = channelOptional.get();

            if (channel.getServer().getId().equals(serverId)) {
                channelRepository.delete(channel);
                return true;
            }
        }
        return false;
    }
}
