package com.example.backend.model;

import jakarta.persistence.*;

import java.util.Date;

//TODO: Qui c`era import di java.sql.Date

@Entity
@Table(name = "visite_mediche")
@DiscriminatorValue("VISITA_MEDICA")
public class VisitaMedica extends RecordMedico {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "data", nullable = false)
    private Date data;

    @Lob
    @Column(name = "referto")
    private byte[] referto;

    public VisitaMedica() {
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public byte[] getReferto() {
        return referto;
    }

    public void setReferto(byte[] referto) {
        this.referto = referto;
    }

}