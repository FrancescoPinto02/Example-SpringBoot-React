package com.example.backend.service.gestionePet;

import com.example.backend.dao.PetDAO;
import com.example.backend.dao.UserDAO;
import com.example.backend.dto.gestionePet.NewPetFormDTO;
import com.example.backend.dto.gestionePet.PetResponseDTO;
import com.example.backend.model.Pet;
import com.example.backend.model.User;
import com.example.backend.security.AuthContext;
import com.example.backend.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GestionePetService {

    @Autowired
    private PetDAO petDAO;

    @Autowired
    private UserDAO userDAO;

    public PetResponseDTO creaPet(NewPetFormDTO dto) throws IOException {
        // 1. Recupera l’utente autenticato dal contesto
        AuthenticatedUser currentUser = AuthContext.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("Accesso non autorizzato");
        }

        // 2. Controllo ruolo
        if (!"PROPRIETARIO".equals(currentUser.getRole())) {
            throw new RuntimeException("Accesso negato: solo i proprietari possono creare un animale");
        }

        // 3. Recupera il proprietario dal DB
        User owner = userDAO.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Proprietario non trovato"));

        // 4. Crea il nuovo Pet
        Pet pet = new Pet();
        pet.setNome(dto.getNome());
        pet.setRazza(dto.getRazza());
        pet.setMicrochip(dto.getMicrochip());
        pet.setSesso(dto.getSesso());

        // 5. Validazione e salvataggio foto
        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            String contentType = dto.getFoto().getContentType();
            if (contentType == null ||
                    !(contentType.equalsIgnoreCase("image/jpeg") ||
                            contentType.equalsIgnoreCase("image/png"))) {
                throw new RuntimeException("Formato immagine non valido: sono ammessi solo JPEG o PNG");
            }
            pet.setFoto(dto.getFoto().getBytes());
        }

        pet.setOwner(owner);
        Pet savedPet = petDAO.save(pet);

        // 6. Conversione in DTO con immagine base64
        return new PetResponseDTO(
                savedPet.getId(),
                savedPet.getRazza(),
                savedPet.getNome(),
                savedPet.getMicrochip(),
                savedPet.getSesso(),
                savedPet.getFoto()
        );
    }


    public List<PetResponseDTO> visualizzaMieiPet() {
        // 1. Recupera l’utente autenticato
        AuthenticatedUser currentUser = AuthContext.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("Accesso non autorizzato: nessun utente autenticato");
        }

        // 2. Controllo ruolo
        if (!"PROPRIETARIO".equals(currentUser.getRole())) {
            throw new RuntimeException("Accesso negato: solo i proprietari possono visualizzare i propri animali");
        }

        // 3. Recupera i pet del proprietario
        return petDAO.findByOwnerId(currentUser.getId())
                .stream()
                .map(p -> new PetResponseDTO(
                        p.getId(),
                        p.getRazza(),
                        p.getNome(),
                        p.getMicrochip(),
                        p.getSesso(),
                        p.getFoto()
                ))
                .toList();
    }
}
