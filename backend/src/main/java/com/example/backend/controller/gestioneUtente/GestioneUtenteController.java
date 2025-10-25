package com.example.backend.controller.gestioneUtente;

import com.example.backend.dto.gestioneUtente.AuthResponseDTO;
import com.example.backend.dto.gestioneUtente.LoginRequestDTO;
import com.example.backend.dto.gestioneUtente.RegistrationRequestDTO;
import com.example.backend.service.gestioneUtente.GestioneUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestioneUtente")
public class GestioneUtenteController {
    @Autowired
    private GestioneUtenteService gestioneUtenteService;

    /**
     * Registra un nuovo utente nel sistema e restituisce un token JWT valido.
     *
     * <p><b>Metodo:</b> POST<br>
     * <b>Endpoint:</b> /gestioneUtente/registraUtente<br>
     * <b>Content-Type:</b> application/json</p>
     *
     * <p><b>Corpo richiesta (JSON):</b></p>
     * <pre>
     * {
     *   "email": "luca.rossi@mail.com",
     *   "password": "password123",
     *   "nome": "Luca",
     *   "cognome": "Rossi"
     * }
     * </pre>
     *
     * <p><b>Esempio risposta (201 Created):</b></p>
     * <pre>
     * {
     *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     *   "id": 1,
     *   "email": "luca.rossi@mail.com"
     * }
     * </pre>
     *
     * @param registrationRequestDTO dati dell’utente da registrare
     * @return {@link AuthResponseDTO} contenente il token JWT, l’id e l’email dell’utente
     * @see AuthResponseDTO
     */
    @PostMapping("/registraUtente")
    public ResponseEntity<AuthResponseDTO> registraUtente (@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        AuthResponseDTO responseDTO = gestioneUtenteService.registraUtente(registrationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Autentica un utente esistente e restituisce un token JWT valido.
     *
     * <p><b>Metodo:</b> POST<br>
     * <b>Endpoint:</b> /gestioneUtente/login<br>
     * <b>Content-Type:</b> application/json</p>
     *
     * <p><b>Corpo richiesta (JSON):</b></p>
     * <pre>
     * {
     *   "email": "luca.rossi@mail.com",
     *   "password": "password123"
     * }
     * </pre>
     *
     * <p><b>Esempio risposta (200 OK):</b></p>
     * <pre>
     * {
     *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     *   "id": 1,
     *   "email": "luca.rossi@mail.com"
     * }
     * </pre>
     *
     *
     * @param loginRequestDTO credenziali dell’utente (email e password)
     * @return {@link AuthResponseDTO} contenente il token JWT e i dati dell’utente autenticato
     * @see AuthResponseDTO
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO responseDTO = gestioneUtenteService.login(loginRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

}
