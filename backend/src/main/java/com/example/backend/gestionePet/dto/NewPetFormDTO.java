package com.example.backend.gestionePet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class NewPetFormDTO {
    @NotBlank(message = "Il nome del pet è obbligatorio")
    @Size(max = 50, message = "Il nome non può superare i 50 caratteri")
    private String nome;

    @NotBlank(message = "La razza è obbligatoria")
    @Size(max = 50, message = "La razza non può superare i 50 caratteri")
    private String razza;

    @NotBlank(message = "Il microchip è obbligatorio")
    @Pattern(
            regexp = "^[A-Za-z0-9]{10,15}$",
            message = "Il microchip deve contenere tra 10 e 15 caratteri alfanumerici"
    )
    private String microchip;

    @NotBlank(message = "Il sesso è obbligatorio")
    @Pattern(
            regexp = "^(MASCHIO|FEMMINA)$",
            message = "Il sesso deve essere 'MASCHIO' o 'FEMMINA'"
    )
    private String sesso;

    private MultipartFile foto;

    public NewPetFormDTO(String nome, String razza, String microchip, String sesso, MultipartFile foto) {
        this.nome = nome;
        this.razza = razza;
        this.microchip = microchip;
        this.sesso = sesso;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazza() {
        return razza;
    }

    public void setRazza(String razza) {
        this.razza = razza;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public MultipartFile getFoto() {
        return foto;
    }

    public void setFoto(MultipartFile foto) {
        this.foto = foto;
    }
}
