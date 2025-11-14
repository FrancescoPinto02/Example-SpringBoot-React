package com.example.backend.gestionePet.controller;

import com.example.backend.gestionePet.dto.PetDetailsDTO;
import com.example.backend.gestionePet.service.GestionePetService;
import com.example.backend.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestionePet")
public class GestionePetController {

    @Autowired
    private GestionePetService gestionePetService;

    @GetMapping("/{id}")
    public PetDetailsDTO getPetDetails(@PathVariable Long id) {
        return gestionePetService.getPetDetailsById(id);
    }
}
