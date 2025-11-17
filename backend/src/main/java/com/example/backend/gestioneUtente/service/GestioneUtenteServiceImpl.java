package com.example.backend.gestioneUtente.service;

import com.example.backend.gestioneUtente.repository.UserRepository;
import com.example.backend.gestioneUtente.dto.AuthResponseDTO;
import com.example.backend.gestioneUtente.dto.LoginRequestDTO;
import com.example.backend.gestioneUtente.dto.RegistrationRequestDTO;
import com.example.backend.gestioneUtente.entity.User;
import com.example.backend.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class GestioneUtenteServiceImpl implements  GestioneUtenteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO registraUtente(@Valid RegistrationRequestDTO registrationRequestDTO) {
        // Controllo email non nulla o vuota
        String email = registrationRequestDTO.getEmail();
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email obbligatoria");
        }

        // Controllo formato email con regex semplice
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new RuntimeException("Formato email non valido");
        }

        // Controllo se esiste già nel DB
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Esiste già un utente con questa email");
        }

        // Crea nuovo utente
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        user.setNome(registrationRequestDTO.getNome());
        user.setCognome(registrationRequestDTO.getCognome());

        // Salva nel DB
        User saved = userRepository.save(user);

        // Genera token JWT (includendo id, email, e ruolo)
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), "PROPRIETARIO");

        // Ritorna la risposta
        return new AuthResponseDTO(token, saved.getId(), saved.getEmail());
    }

    @Override
    public AuthResponseDTO login(@Valid LoginRequestDTO loginRequestDTO) {
        // Trova utente per email
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Verifica password
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password errata");
        }

        // Genera token JWT
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), "PROPRIETARIO");

        // Restituisce risposta
        return new AuthResponseDTO(token, user.getId(), user.getEmail());
    }
}
