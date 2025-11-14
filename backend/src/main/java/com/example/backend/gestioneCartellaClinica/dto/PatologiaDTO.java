package com.example.backend.gestioneCartellaClinica.dto;

import com.example.backend.model.Patologia;

import java.util.Date;

public class PatologiaDTO {
    private Long id;
    private String nome;
    private java.util.Date dataDiagnosi;
    private String sintomi;
    private String diagnosi;
    private String terapiaAssociata;
    private String veterinario;

    public static PatologiaDTO from(Patologia p) {
        PatologiaDTO dto = new PatologiaDTO();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setDataDiagnosi(p.getDataDiDiagnosi());
        dto.setSintomi(p.getSintomiOsservati());
        dto.setDiagnosi(p.getDiagnosi());
        dto.setTerapiaAssociata(p.getTerapiaAssociata());
        dto.setVeterinario("Dott. " + p.getVeterinario().getNome() + " " + p.getVeterinario().getCognome());
        return dto;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataDiagnosi() {
        return dataDiagnosi;
    }

    public void setDataDiagnosi(Date dataDiagnosi) {
        this.dataDiagnosi = dataDiagnosi;
    }

    public String getSintomi() {
        return sintomi;
    }

    public void setSintomi(String sintomi) {
        this.sintomi = sintomi;
    }

    public String getDiagnosi() {
        return diagnosi;
    }

    public void setDiagnosi(String diagnosi) {
        this.diagnosi = diagnosi;
    }

    public String getTerapiaAssociata() {
        return terapiaAssociata;
    }

    public void setTerapiaAssociata(String terapiaAssociata) {
        this.terapiaAssociata = terapiaAssociata;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }
}
