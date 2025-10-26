package com.example.backend.controller.gestionePet;

import com.example.backend.dto.gestionePet.NewPetFormDTO;
import com.example.backend.dto.gestionePet.PetResponseDTO;
import com.example.backend.model.Pet;
import com.example.backend.service.gestionePet.GestionePetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gestionePet")
public class GestionePetController {

    @Autowired
    private GestionePetService gestionePetService;

    /**
     * Crea un nuovo animale domestico per l’utente autenticato.
     *
     * <p><b>Metodo:</b> POST<br>
     * <b>Endpoint:</b> /gestionePet/creaPet<br>
     * <b>Content-Type:</b> multipart/form-data</p>
     *
     * <p><b>Parametri richiesti:</b></p>
     * <ul>
     *     <li><b>Authorization</b> (header): token JWT in formato <code>Bearer &lt;token&gt;</code></li>
     *     <li><b>nome</b> (form-data): nome del pet</li>
     *     <li><b>razza</b> (form-data): razza dell’animale</li>
     *     <li><b>microchip</b> (form-data): codice microchip univoco</li>
     *     <li><b>sesso</b> (form-data): M o F</li>
     *     <li><b>foto</b> (form-data): immagine JPEG o PNG</li>
     * </ul>
     *
     * <p><b>Esempio richiesta:</b></p>
     * <pre>
     * POST /gestionePet/creaPet
     * Authorization: Bearer eyJhbGciOi...
     * Content-Type: multipart/form-data
     *
     * nome=Luna
     * razza=Labrador
     * microchip=ABC123
     * sesso=F
     * foto=luna.jpg
     * </pre>
     *
     * <p><b>Esempio risposta:</b></p>
     * <pre>
     * {
     *   "id": 5,
     *   "nome": "Luna",
     *   "razza": "Labrador",
     *   "microchip": "ABC123",
     *   "sesso": "F",
     *   "fotoBase64": "/9j/4AAQSkZJRgABAQAAAQABAAD..."
     * }
     * </pre>
     *
     * @param newPetFormDTO dati del pet (nome, razza, sesso, microchip, foto)
     * @return un oggetto {@link PetResponseDTO} con le informazioni del pet creato
     * @throws IOException se si verifica un errore nella lettura del file immagine
     * @see PetResponseDTO
     */
    @PostMapping(value = "/creaPet", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetResponseDTO> creaPet(@ModelAttribute NewPetFormDTO newPetFormDTO) throws IOException {
        PetResponseDTO nuovoPet = gestionePetService.creaPet(newPetFormDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoPet);
    }

    /**
     * Restituisce la lista dei pet associati all’utente autenticato.
     *
     * <p><b>Metodo:</b> GET<br>
     * <b>Endpoint:</b> /gestionePet/visualizzaPet</p>
     *
     * <p><b>Parametri richiesti:</b></p>
     * <ul>
     *     <li><b>Authorization</b> (header): token JWT in formato <code>Bearer &lt;token&gt;</code></li>
     * </ul>
     *
     * <p><b>Esempio richiesta:</b></p>
     * <pre>
     * GET /gestionePet/visualizzaPet
     * Authorization: Bearer eyJhbGciOi...
     * </pre>
     *
     * <p><b>Esempio risposta:</b></p>
     * <pre>
     * [
     *   {
     *     "id": 5,
     *     "nome": "Luna",
     *     "razza": "Labrador",
     *     "microchip": "ABC123",
     *     "sesso": "F",
     *     "fotoBase64": "/9j/4AAQSkZJRgABAQAAAQABAAD..."
     *   },
     *   {
     *     "id": 6,
     *     "nome": "Rocky",
     *     "razza": "Bulldog",
     *     "microchip": "XYZ987",
     *     "sesso": "M",
     *     "fotoBase64": "/9j/4AAQSkZJRgABAQEASABIAAD..."
     *   }
     * ]
     * </pre>
     *
     * @return una lista di {@link PetResponseDTO} relativi al proprietario autenticato
     * @see PetResponseDTO
     */
    @GetMapping("/visualizzaPet")
    public ResponseEntity<List<PetResponseDTO>> visualizzaMieiPet() {
        List<PetResponseDTO> pets = gestionePetService.visualizzaMieiPet();
        return ResponseEntity.ok(pets);
    }
}
