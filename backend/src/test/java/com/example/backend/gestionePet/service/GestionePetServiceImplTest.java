package com.example.backend.gestionePet.service;

import com.example.backend.gestionePet.repository.PetRepository;
import com.example.backend.gestioneUtente.repository.UserRepository;
import com.example.backend.gestionePet.dto.NewPetFormDTO;
import com.example.backend.gestionePet.dto.PetResponseDTO;
import com.example.backend.gestionePet.entity.Pet;
import com.example.backend.gestioneUtente.entity.User;
import com.example.backend.security.AuthContext;
import com.example.backend.security.AuthenticatedUser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionePetServiceImplTest {

    @Mock private PetRepository petRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private GestionePetServiceImpl gestionePetService;

    private Validator validator;

    @BeforeEach
    void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private AuthenticatedUser buildProprietario() {
        return new AuthenticatedUser(1L, "owner@example.com", "PROPRIETARIO");
    }

    private User mockOwner() {
        User u = new User();
        u.setId(1L);
        return u;
    }

    private NewPetFormDTO buildBaseDto() {
        return new NewPetFormDTO(
                "Fido",
                "Labrador",
                "MICRO12345",
                "MASCHIO",
                null
        );
    }

    // ✅ Caso base corretto
    @Test
    void creaPet_success() throws IOException {
        try (MockedStatic<AuthContext> authContext = mockStatic(AuthContext.class)) {
            authContext.when(AuthContext::getCurrentUser).thenReturn(buildProprietario());
            when(userRepository.findById(1L)).thenReturn(Optional.of(mockOwner()));

            Pet savedPet = new Pet();
            savedPet.setId(100L);
            savedPet.setNome("Fido");
            savedPet.setRazza("Labrador");
            savedPet.setMicrochip("MICRO12345");
            savedPet.setSesso("MASCHIO");
            when(petRepository.save(any())).thenReturn(savedPet);

            PetResponseDTO result = gestionePetService.creaPet(buildBaseDto());

            assertNotNull(result);
            assertEquals(100L, result.getId());
            verify(petRepository).save(any(Pet.class));
        }
    }

    // ❌ Nessun utente loggato
    @Test
    void creaPet_accessoNonAutorizzato() {
        try (MockedStatic<AuthContext> authContext = mockStatic(AuthContext.class)) {
            authContext.when(AuthContext::getCurrentUser).thenReturn(null);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> gestionePetService.creaPet(buildBaseDto()));
            assertTrue(ex.getMessage().contains("Accesso non autorizzato"));
        }
    }

    // ❌ Proprietario non trovato
    @Test
    void creaPet_proprietarioNonTrovato() {
        try (MockedStatic<AuthContext> authContext = mockStatic(AuthContext.class)) {
            authContext.when(AuthContext::getCurrentUser).thenReturn(buildProprietario());
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> gestionePetService.creaPet(buildBaseDto()));
            assertEquals("Proprietario non trovato", ex.getMessage());
        }
    }

    // ❌ Foto non valida (GIF)
    @Test
    void creaPet_fotoFormatoNonValido() {
        MockMultipartFile foto = new MockMultipartFile(
                "foto", "file.gif", "image/gif", "fakeimg".getBytes());
        NewPetFormDTO dto = new NewPetFormDTO(
                "Fido", "Labrador", "MICRO12345", "MASCHIO", foto);

        try (MockedStatic<AuthContext> authContext = mockStatic(AuthContext.class)) {
            authContext.when(AuthContext::getCurrentUser).thenReturn(buildProprietario());
            when(userRepository.findById(1L)).thenReturn(Optional.of(mockOwner()));

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> gestionePetService.creaPet(dto));
            assertTrue(ex.getMessage().contains("Formato immagine non valido"));
        }
    }

    // ✅ Foto valida (PNG)
    @Test
    void creaPet_conFotoValida() throws IOException {
        MockMultipartFile foto = new MockMultipartFile(
                "foto", "foto.png", "image/png", "fakeimage".getBytes());
        NewPetFormDTO dto = new NewPetFormDTO(
                "Fido", "Labrador", "MICRO12345", "MASCHIO", foto);

        try (MockedStatic<AuthContext> authContext = mockStatic(AuthContext.class)) {
            authContext.when(AuthContext::getCurrentUser).thenReturn(buildProprietario());
            when(userRepository.findById(1L)).thenReturn(Optional.of(mockOwner()));

            Pet savedPet = new Pet();
            savedPet.setId(10L);
            savedPet.setNome("Fido");
            savedPet.setRazza("Labrador");
            savedPet.setMicrochip("MICRO12345");
            savedPet.setSesso("MASCHIO");
            savedPet.setFoto("fakeimage".getBytes());
            when(petRepository.save(any())).thenReturn(savedPet);

            PetResponseDTO result = gestionePetService.creaPet(dto);

            assertEquals(10L, result.getId());
            assertNotNull(result.getFotoBase64());
        }
    }

    // ❌ Validazione DTO: nome mancante
    @Test
    void validazione_nomeMancante_messaggioCorretto() {
        NewPetFormDTO dto = new NewPetFormDTO(
                "", "Labrador", "MICRO12345", "MASCHIO", null
        );

        Set<ConstraintViolation<NewPetFormDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Dovrebbero esserci errori di validazione");
        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("nome") &&
                                v.getMessage().equals("Il nome del pet è obbligatorio")
                ),
                "Messaggio di errore per 'nome' non corretto"
        );
    }

    // ❌ Validazione DTO: microchip errato
    @Test
    void validazione_microchipErrato_messaggioCorretto() {
        NewPetFormDTO dto = new NewPetFormDTO(
                "Fido", "Labrador", "####", "MASCHIO", null
        );

        Set<ConstraintViolation<NewPetFormDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Dovrebbero esserci errori di validazione");
        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("microchip") &&
                                v.getMessage().equals("Il microchip deve contenere tra 10 e 15 caratteri alfanumerici")
                ),
                "Messaggio di errore per 'microchip' non corretto"
        );
    }

    // ❌ Validazione DTO: sesso non corretto
    @Test
    void validazione_sessoErrato_messaggioCorretto() {
        NewPetFormDTO dto = new NewPetFormDTO(
                "Fido", "Labrador", "MICRO12345", "ALTRO", null
        );

        Set<ConstraintViolation<NewPetFormDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Dovrebbero esserci errori di validazione");
        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("sesso") &&
                                v.getMessage().equals("Il sesso deve essere 'MASCHIO' o 'FEMMINA'")
                ),
                "Messaggio di errore per 'sesso' non corretto"
        );
    }

    // ✅ DTO valido → nessuna violazione
    @Test
    void validazione_dtoValido() {
        MockMultipartFile foto = new MockMultipartFile(
                "foto", "file.jpg", "image/jpeg", "bytes".getBytes());

        NewPetFormDTO dto = new NewPetFormDTO(
                "Fido", "Labrador", "MICRO12345", "MASCHIO", foto
        );

        Set<ConstraintViolation<NewPetFormDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Non dovrebbero esserci errori di validazione");
    }
}