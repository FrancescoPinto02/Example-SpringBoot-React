package com.example.backend.repository;

import com.example.backend.model.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository <Pet, Long> {

    Optional<Pet> findById(Long id);

    @EntityGraph(attributePaths = {
            "recordMedici",
            "recordMedici.veterinario"
    })
    Optional<Pet> findPetDetailsdById(Long id);
}
