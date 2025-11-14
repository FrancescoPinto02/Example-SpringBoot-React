package com.example.backend.gestioneCartellaClinica.dto;

import com.example.backend.model.Vaccinazione;

import java.util.Date;

public class VaccinazioneDTO {
    private Long id;
    private String nome;
    private String tipologia;
    private Float dose;
    private String via;
    private String effettiCollaterali;
    private java.util.Date dataSomministrazione;
    private java.util.Date richiamoPrevisto;
    private String veterinario;

    public static VaccinazioneDTO from (Vaccinazione vaccinazione) {
        VaccinazioneDTO dto = new VaccinazioneDTO();
        dto.setId(vaccinazione.getId());
        dto.setNome(vaccinazione.getNome());
        dto.setTipologia(vaccinazione.getTipologia());
        dto.setDose(vaccinazione.getDoseSomministrata());
        dto.setVia(vaccinazione.getViaDiSomministrazione().toString());
        dto.setEffettiCollaterali(vaccinazione.getEffettiCollaterali());
        dto.setDataSomministrazione(vaccinazione.getDataDiSomministrazione());
        dto.setRichiamoPrevisto(vaccinazione.getRichiamoPrevisto());
        dto.setVeterinario("Dott. " + vaccinazione.getVeterinario().getNome() + " " + vaccinazione.getVeterinario().getCognome());
        return dto;
    };

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

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Float getDose() {
        return dose;
    }

    public void setDose(Float dose) {
        this.dose = dose;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getEffettiCollaterali() {
        return effettiCollaterali;
    }

    public void setEffettiCollaterali(String effettiCollaterali) {
        this.effettiCollaterali = effettiCollaterali;
    }

    public Date getDataSomministrazione() {
        return dataSomministrazione;
    }

    public void setDataSomministrazione(Date dataSomministrazione) {
        this.dataSomministrazione = dataSomministrazione;
    }

    public Date getRichiamoPrevisto() {
        return richiamoPrevisto;
    }

    public void setRichiamoPrevisto(Date richiamoPrevisto) {
        this.richiamoPrevisto = richiamoPrevisto;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }
}

