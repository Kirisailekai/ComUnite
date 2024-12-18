package com.example.demo;

import jakarta.persistence.*;
import java.util.Set;
import java.util.List;   // Для списка
import java.util.Set;    // Для множества
import java.util.Map;    // Для отображения
import java.util.ArrayList;  // Для конкретной реализации списка
import java.util.HashSet;    // Для конкретной реализации множества
import java.util.HashMap;    // Для конкретной реализации отображения

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    // Getters and Setters
}
