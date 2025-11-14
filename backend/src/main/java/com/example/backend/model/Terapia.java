package com.example.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "terapie")
@DiscriminatorValue("TERAPIA")
public class Terapia extends RecordMedico {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Column(name = "data_inizio", nullable = false)
    private Date dataInizio;

    @Column(name = "data_fine")
    private Date dataFine;

    public Terapia() {
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
}