package com.example.backend.gestioneUtente.service;

import com.example.backend.gestioneUtente.repository.UserRepository;
import com.example.backend.gestioneUtente.dto.AuthResponseDTO;
import com.example.backend.gestioneUtente.dto.LoginRequestDTO;
import com.example.backend.gestioneUtente.dto.RegistrationRequestDTO;
import com.example.backend.gestioneUtente.entity.User;
import com.example.backend.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestioneUtenteServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtil jwtUtil;

    @InjectMocks
    private GestioneUtenteServiceImpl gestioneUtenteService;

    private RegistrationRequestDTO validRegistration;
    private LoginRequestDTO validLogin;
    private User mockUser;

    @BeforeEach
    void setup() {
        validRegistration = new RegistrationRequestDTO();
        validRegistration.setEmail("test@example.com");
        validRegistration.setPassword("Password123");
        validRegistration.setNome("Mario");
        validRegistration.setCognome("Rossi");

        validLogin = new LoginRequestDTO("test@example.com", "Password123");

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedpass");
        mockUser.setNome("Mario");
        mockUser.setCognome("Rossi");
    }

    // ✅ Caso di successo: registrazione
    @Test
    void registraUtente_success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Password123")).thenReturn("encodedpass");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> {
            User saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });
        when(jwtUtil.generateToken(1L, "test@example.com", "PROPRIETARIO"))
                .thenReturn("mock-token");

        AuthResponseDTO result = gestioneUtenteService.registraUtente(validRegistration);

        assertNotNull(result);
        assertEquals("mock-token", result.getToken());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    // ❌ Email nulla
    @Test
    void registraUtente_emailNulla() {
        validRegistration.setEmail(null);
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> gestioneUtenteService.registraUtente(validRegistration));
        assertEquals("Email obbligatoria", ex.getMessage());
    }

    // ❌ Email vuota
    @Test
    void registraUtente_emailVuota() {
        validRegistration.setEmail(" ");
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> gestioneUtenteService.registraUtente(validRegistration));
        assertEquals("Email obbligatoria", ex.getMessage());
    }

    // ❌ Email con formato errato
    @Test
    void registraUtente_emailFormatoErrato() {
        validRegistration.setEmail("invalid-email");
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> gestioneUtenteService.registraUtente(validRegistration));
        assertEquals("Formato email non valido", ex.getMessage());
    }

    // ❌ Email già esistente
    @Test
    void registraUtente_emailDuplicata() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> gestioneUtenteService.registraUtente(validRegistration));
        assertEquals("Esiste già un utente con questa email", ex.getMessage());
    }

    // ✅ Login riuscito
    @Test
    void login_success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("Password123", "encodedpass")).thenReturn(true);
        when(jwtUtil.generateToken(1L, "test@example.com", "PROPRIETARIO"))
                .thenReturn("mock-token");

        AuthResponseDTO result = gestioneUtenteService.login(validLogin);

        assertNotNull(result);
        assertEquals("mock-token", result.getToken());
        assertEquals("test@example.com", result.getEmail());
    }

    // ❌ Login fallito: utente non trovato
    @Test
    void login_utenteNonTrovato() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> gestioneUtenteService.login(validLogin));

        assertEquals("Utente non trovato", ex.getMessage());
    }

    // ❌ Login fallito: password errata
    @Test
    void login_passwordErrata() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("Password123", "encodedpass")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> gestioneUtenteService.login(validLogin));

        assertEquals("Password errata", ex.getMessage());
    }
}

