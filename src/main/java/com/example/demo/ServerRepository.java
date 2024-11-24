package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.List;   // Для списка
import java.util.Set;    // Для множества
import java.util.Map;    // Для отображения
import java.util.ArrayList;  // Для конкретной реализации списка
import java.util.HashSet;    // Для конкретной реализации множества
import java.util.HashMap;    // Для конкретной реализации отображения

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    List<Server> findByOwnerId(Long ownerId);
}
