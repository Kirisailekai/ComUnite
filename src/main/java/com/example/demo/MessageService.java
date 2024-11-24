package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Получить сообщение по ID
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    // Получить все сообщения
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Получить все сообщения для канала
    public List<Message> getMessagesByChannel(Long channelId) {
        return messageRepository.findByChannelId(channelId);  // Метод должен быть реализован в репозитории
    }

    // Создать новое сообщение
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    // Обновить сообщение
    public Optional<Message> updateMessage(Long id, Message messageDetails) {
        Optional<Message> existingMessage = messageRepository.findById(id);
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setContent(messageDetails.getContent());
            return Optional.of(messageRepository.save(message));
        }
        return Optional.empty();
    }

    // Удалить сообщение
    public boolean deleteMessage(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
