package com.example.demo;

import jakarta.persistence.*;
import java.util.List;   // Для списка
import java.util.Set;    // Для множества
import java.util.Map;    // Для отображения
import java.util.ArrayList;  // Для конкретной реализации списка
import java.util.HashSet;    // Для конкретной реализации множества
import java.util.HashMap;    // Для конкретной реализации отображения

@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Getters and Setters
}
