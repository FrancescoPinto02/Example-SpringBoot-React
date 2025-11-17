package com.example.backend.gestioneUtente.repository;

import com.example.backend.gestioneUtente.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
