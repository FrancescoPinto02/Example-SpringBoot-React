package com.example.backend.service.gestionePet;

import com.example.backend.dao.PetDAO;
import com.example.backend.dao.UserDAO;
import com.example.backend.dto.gestionePet.NewPetFormDTO;
import com.example.backend.dto.gestionePet.PetResponseDTO;
import com.example.backend.model.Pet;
import com.example.backend.model.User;
import com.example.backend.security.AuthHelper;
import com.example.backend.security.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthHelper authHelper;

    public PetResponseDTO creaPet(String token, NewPetFormDTO dto) throws IOException {
        // 1. Verifica autenticazione
        if (!authHelper.verificaAutenticazione(token)) {
            throw new RuntimeException("Accesso non autorizzato");
        }

        // 2. Estrai informazioni dal token
        Long ownerId = jwtUtil.getId(token);
        String role = jwtUtil.getRole(token);

        // 3. Controllo ruolo
        if (!"PROPRIETARIO".equals(role)) {
            throw new RuntimeException("Accesso negato: solo i proprietari possono creare un animale");
        }

        // 4. Recupera utente proprietario
        User owner = userDAO.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Proprietario non trovato"));

        Pet pet = new Pet();
        pet.setNome(dto.getNome());
        pet.setRazza(dto.getRazza());
        pet.setMicrochip(dto.getMicrochip());
        pet.setSesso(dto.getSesso());

        // 5. Validazione e salvataggio foto
        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            String contentType = dto.getFoto().getContentType();
            if (contentType == null ||
                    !(contentType.equalsIgnoreCase("image/jpeg") || contentType.equalsIgnoreCase("image/png"))) {
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


    public List<PetResponseDTO> visualizzaMieiPet(String token) {
        // 1. Verifica autenticazione
        if (!authHelper.verificaAutenticazione(token)) {
            throw new RuntimeException("Accesso non autorizzato: token mancante o non valido");
        }

        // 2. Estrai info utente
        Long ownerId = jwtUtil.getId(token);
        String role = jwtUtil.getRole(token);

        // 3. Controllo ruolo
        if (!"PROPRIETARIO".equals(role)) {
            throw new RuntimeException("Accesso negato: solo i proprietari possono visualizzare i propri animali");
        }

        // 4. Mappa in DTO con immagini Base64
        return petDAO.findByOwnerId(ownerId)
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
