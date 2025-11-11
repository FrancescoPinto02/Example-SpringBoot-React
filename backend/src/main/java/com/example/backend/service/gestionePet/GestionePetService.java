package com.example.backend.service.gestionePet;

import com.example.backend.dto.gestionePet.NewPetFormDTO;
import com.example.backend.dto.gestionePet.PetResponseDTO;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

public interface GestionePetService {

    /**
     * Crea un nuovo animale domestico per l’utente autenticato.
     *
     * @param dto Dati del nuovo pet (nome, razza, microchip, sesso, foto)
     * @return DTO di risposta con i dati del pet salvato
     * @throws IOException se si verifica un errore nella gestione dell’immagine
     */
    PetResponseDTO creaPet(@Valid NewPetFormDTO dto) throws IOException;

    /**
     * Restituisce la lista degli animali associati all’utente autenticato.
     *
     * @return lista di PetResponseDTO
     */
    List<PetResponseDTO> visualizzaMieiPet();
}
