package com.example.backend.dto.gestionePet;

import org.springframework.web.multipart.MultipartFile;

public class NewPetFormDTO {
    private String nome;
    private String razza;
    private String microchip;
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
