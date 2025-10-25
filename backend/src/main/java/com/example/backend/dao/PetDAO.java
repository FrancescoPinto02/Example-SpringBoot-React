package com.example.backend.dao;

import com.example.backend.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetDAO extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerId(Long ownerId);
}
