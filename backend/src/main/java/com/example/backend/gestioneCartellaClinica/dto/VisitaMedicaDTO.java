package com.example.backend.gestioneCartellaClinica.dto;

import com.example.backend.model.VisitaMedica;

import java.util.Date;

public class VisitaMedicaDTO {
    private Long id;
    private String nome;
    private String descrizione;
    private java.util.Date data;
    private byte[] referto;
    private String veterinario;

    public static VisitaMedicaDTO from(VisitaMedica visitaMedica) {
        VisitaMedicaDTO dto = new VisitaMedicaDTO();
        dto.setId(visitaMedica.getId());
        dto.setNome(visitaMedica.getNome());
        dto.setDescrizione(visitaMedica.getDescrizione());
        dto.setData(visitaMedica.getData());
        dto.setReferto(visitaMedica.getReferto());
        dto.setVeterinario("Dott. " + visitaMedica.getVeterinario().getNome() + " " + visitaMedica.getVeterinario().getCognome());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getReferto() {
        return referto;
    }

    public void setReferto(byte[] referto) {
        this.referto = referto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }
}
