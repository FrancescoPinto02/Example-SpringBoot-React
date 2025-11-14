package com.example.backend.gestioneCartellaClinica.dto;

import com.example.backend.model.Terapia;

import java.util.Date;

public class TerapiaDTO {
    private Long id;
    private String nome;
    private String descrizione;
    private java.util.Date dataInizio;
    private java.util.Date dataFine;
    private String veterinario;

    public static TerapiaDTO from(Terapia terapia) {
        TerapiaDTO dto = new TerapiaDTO();
        dto.setId(terapia.getId());
        dto.setNome(terapia.getNome());
        dto.setDescrizione(terapia.getDescrizione());
        dto.setDataInizio(terapia.getDataInizio());
        dto.setDataFine(terapia.getDataFine());
        dto.setVeterinario("Dott. " + terapia.getVeterinario().getNome() + " " + terapia.getVeterinario().getCognome());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }
}

