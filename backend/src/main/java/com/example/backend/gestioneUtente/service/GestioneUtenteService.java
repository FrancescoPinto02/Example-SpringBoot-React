package com.example.backend.gestioneUtente.service;

import com.example.backend.gestioneUtente.dto.AuthResponseDTO;
import com.example.backend.gestioneUtente.dto.LoginRequestDTO;
import com.example.backend.gestioneUtente.dto.RegistrationRequestDTO;
import jakarta.validation.Valid;

public interface GestioneUtenteService {

    /**
     * Registra un nuovo utente, validando email, password e dati anagrafici.
     *
     * @param registrationRequestDTO DTO contenente i dati di registrazione
     * @return AuthResponseDTO con il token JWT e i dati dell’utente registrato
     * @throws RuntimeException se l’email è invalida, duplicata o mancante
     */
    AuthResponseDTO registraUtente(@Valid RegistrationRequestDTO registrationRequestDTO);

    /**
     * Effettua il login di un utente esistente, validando credenziali e generando il token JWT.
     *
     * @param loginRequestDTO DTO contenente email e password
     * @return AuthResponseDTO con token JWT e dati dell’utente autenticato
     * @throws RuntimeException se l’utente non esiste o la password è errata
     */
    AuthResponseDTO login(@Valid LoginRequestDTO loginRequestDTO);
}
