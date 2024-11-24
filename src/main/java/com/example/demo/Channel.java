package com.example.demo;

import jakarta.persistence.*; // Для новых версий Spring Boot (>=3.0)
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ChannelType type;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> messages = new HashSet<>(); // Инициализация коллекции сообщений

    @ManyToMany
    @JoinTable(
            name = "channel_participants",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();  // Инициализация коллекции участников канала

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    // Дополнительные методы для удобства работы с участниками

    public void addParticipant(User user) {
        this.participants.add(user);  // Добавление участника в канал
    }

    public void removeParticipant(User user) {
        this.participants.remove(user);  // Удаление участника из канала
    }

    // Переопределение equals и hashCode для корректного сравнения объектов Channel (по id)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return id != null && id.equals(channel.id);  // Сравниваем по id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Используем Objects.hash для корректного хеширования по id
    }
}

enum ChannelType {
    TEXT,
    VOICE
}
