package com.example.backend.service.gestioneUtente;

import com.example.backend.dao.UserDAO;
import com.example.backend.dto.gestioneUtente.AuthResponseDTO;
import com.example.backend.dto.gestioneUtente.LoginRequestDTO;
import com.example.backend.dto.gestioneUtente.RegistrationRequestDTO;
import com.example.backend.model.User;
import com.example.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GestioneUtenteService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponseDTO registraUtente(RegistrationRequestDTO registrationRequestDTO) {
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
        if (userDAO.findByEmail(email).isPresent()) {
            throw new RuntimeException("Esiste già un utente con questa email");
        }

        // Crea nuovo utente
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        user.setNome(registrationRequestDTO.getNome());
        user.setCognome(registrationRequestDTO.getCognome());

        // Salva nel DB
        User saved = userDAO.save(user);

        // Genera token JWT (includendo id, email, e ruolo)
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), "PROPRIETARIO");

        // Ritorna la risposta
        return new AuthResponseDTO(token, saved.getId(), saved.getEmail());
    }


    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // Trova utente per email
        User user = userDAO.findByEmail(loginRequestDTO.getEmail())
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
