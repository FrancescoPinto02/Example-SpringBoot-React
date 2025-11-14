package com.example.backend.gestionePet.service;

import com.example.backend.gestionePet.dto.PetDetailsDTO;
import com.example.backend.model.Pet;
import com.example.backend.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GestionePetService {

    @Autowired
    private PetRepository petRepository;

    public PetDetailsDTO getPetDetailsById(Long id) {
        Pet pet = petRepository.findPetDetailsdById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet non trovato"));

        return PetDetailsDTO.from(pet);
    }
}
