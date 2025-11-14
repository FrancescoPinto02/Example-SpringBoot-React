package com.example.backend.autenticazione.service;

import com.example.backend.dto.gestioneUtente.AuthResponseDTO;
import com.example.backend.dto.gestioneUtente.LoginRequestDTO;
import com.example.backend.exception.UnauthorizedException;
import com.example.backend.repository.ProprietarioRepository;
import com.example.backend.model.Proprietario;
import com.example.backend.repository.VeterinarioRepository;
import com.example.backend.security.JwtUtil;
import com.example.backend.security.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AutenticazioneServiceImpl implements AutenticazioneService {
    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO login(@Valid LoginRequestDTO loginRequestDTO) {
        // Cerca tra i proprietari
        var proprietarioOpt = proprietarioRepository.findByEmail(loginRequestDTO.getEmail());
        if (proprietarioOpt.isPresent()) {
            var proprietario = proprietarioOpt.get();
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), proprietario.getPassword())) {
                throw new UnauthorizedException("Credenziali non valide: password errata.");
            }
            String token = jwtUtil.generateToken(proprietario.getId(), proprietario.getEmail(), Role.PROPRIETARIO);
            return new AuthResponseDTO(token, proprietario.getId(), proprietario.getEmail());
        }

        // Cerca tra i veterinari
        var veterinarioOpt = veterinarioRepository.findByEmail(loginRequestDTO.getEmail());
        if (veterinarioOpt.isPresent()) {
            var veterinario = veterinarioOpt.get();
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), veterinario.getPassword())) {
                throw new UnauthorizedException("Credenziali non valide: password errata.");
            }
            String token = jwtUtil.generateToken(veterinario.getId(), veterinario.getEmail(), Role.VETERINARIO);
            return new AuthResponseDTO(token, veterinario.getId(), veterinario.getEmail());
        }

        // Se non trovato in nessuno dei due
        throw new EntityNotFoundException("Utente non trovato con email: " + loginRequestDTO.getEmail());
    }
}
