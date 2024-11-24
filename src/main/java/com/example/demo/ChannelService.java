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
    private final MessageRepository messageRepository;  // Репозиторий сообщений

    @Autowired
    public ChannelService(ChannelRepository channelRepository, ServerRepository serverRepository, MessageRepository messageRepository) {
        this.channelRepository = channelRepository;
        this.serverRepository = serverRepository;
        this.messageRepository = messageRepository;  // Инициализация репозитория сообщений
    }

    // Получить канал по ID
    public Optional<Channel> getChannelById(Long channelId) {
        return channelRepository.findById(channelId);
    }

    // Получить все каналы
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();  // Извлекаем все каналы
    }

    // Получить каналы для конкретного сервера
    public List<Channel> getChannelsByServerId(Long serverId) {
        return channelRepository.findByServerId(serverId);  // Извлекаем каналы по серверу
    }

    // Получить все сообщения для канала
    public List<Message> getMessagesByChannel(Long channelId) {
        return messageRepository.findByChannelId(channelId);  // Извлекаем все сообщения для канала
    }

    // Получить сообщение по ID
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);  // Извлекаем сообщение по ID
    }

    // Создать новое сообщение
    public Message createMessage(Message message) {
        return messageRepository.save(message);  // Сохраняем сообщение
    }

    // Удалить сообщение
    public boolean deleteMessage(Long messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    // Создать новый канал
    public Channel createChannel(Channel channel) {
        // Проверка существования сервера перед созданием канала
        Optional<Server> server = serverRepository.findById(channel.getServer().getId());
        if (server.isPresent()) {
            channel.setServer(server.get());  // Привязываем канал к серверу
            return channelRepository.save(channel);  // Сохраняем канал в базе данных
        } else {
            throw new RuntimeException("Server not found");  // Если сервер не найден
        }
    }

    // Обновить канал
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

    // Удалить канал
    public boolean deleteChannel(Long channelId) {
        if (channelRepository.existsById(channelId)) {
            channelRepository.deleteById(channelId);
            return true;
        }
        return false;
    }

    // Добавить участника в канал
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

    // Удалить участника из канала
    public void removeParticipantFromChannel(Long channelId, User user) {
        Optional<Channel> channel = channelRepository.findById(channelId);
        if (channel.isPresent()) {
            Channel existingChannel = channel.get();
            existingChannel.getParticipants().remove(user);  // Убираем участника
            channelRepository.save(existingChannel);  // Сохраняем изменения
        } else {
            throw new RuntimeException("Channel not found");
        }
    }
}
