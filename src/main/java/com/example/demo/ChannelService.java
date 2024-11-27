package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository, ServerRepository serverRepository, MessageRepository messageRepository) {
        this.channelRepository = channelRepository;
        this.serverRepository = serverRepository;
        this.messageRepository = messageRepository;
    }

    public Optional<Channel> getChannelById(Long channelId) {
        return channelRepository.findById(channelId);
    }

    // Получить все каналы
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public List<Channel> getChannelsByServerId(Long serverId) {
        return channelRepository.findByServerId(serverId);
    }

    public List<Message> getMessagesByChannel(Long channelId) {
        return messageRepository.findByChannelId(channelId);
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public boolean deleteMessage(Long messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    public Channel createChannel(Channel channel) {
        Optional<Server> server = serverRepository.findById(channel.getServer().getId());
        if (server.isPresent()) {
            channel.setServer(server.get());
            return channelRepository.save(channel);
        } else {
            throw new RuntimeException("Server not found");
        }
    }

    public Optional<Channel> updateChannel(Long channelId, Channel channelDetails) {
        Optional<Channel> existingChannel = channelRepository.findById(channelId);
        if (existingChannel.isPresent()) {
            Channel channel = existingChannel.get();
            channel.setName(channelDetails.getName());
            channel.setType(channelDetails.getType());
            return Optional.of(channelRepository.save(channel));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteChannel(Long channelId) {
        if (channelRepository.existsById(channelId)) {
            channelRepository.deleteById(channelId);
            return true;
        }
        return false;
    }

    public void addParticipantToChannel(Long channelId, User user) {
        Optional<Channel> channel = channelRepository.findById(channelId);
        if (channel.isPresent()) {
            Channel existingChannel = channel.get();
            existingChannel.getParticipants().add(user);  // Добавляем участника
            channelRepository.save(existingChannel);  // Сохраняем изменения
        } else {
            throw new RuntimeException("Channel not found");
        }
    }

    public void removeParticipantFromChannel(Long channelId, User user) {
        Optional<Channel> channel = channelRepository.findById(channelId);
        if (channel.isPresent()) {
            Channel existingChannel = channel.get();
            existingChannel.getParticipants().remove(user);
            channelRepository.save(existingChannel);
        } else {
            throw new RuntimeException("Channel not found");
        }
    }
}
