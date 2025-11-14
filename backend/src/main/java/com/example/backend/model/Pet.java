package com.example.backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name="n_microchip", nullable = false, unique = true)
    private String nMicrochip;

    @Lob
    @Column(name = "foto", columnDefinition = "LONGBLOB")
    private byte[] foto;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Proprietario proprietario;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecordMedico> recordMedici = new ArrayList<>();

    @ManyToMany(mappedBy = "petsAssociati")
    private List<Veterinario> veterinariAssociati = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteProprietario> note = new ArrayList<>();

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
    private LinkingCode linkingCode;


    public Pet() {
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

    public String getnMicrochip() {
        return nMicrochip;
    }

    public void setnMicrochip(String nMicrochip) {
        this.nMicrochip = nMicrochip;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public List<Veterinario> getVeterinariAssociati() {
        return veterinariAssociati;
    }

    public void setVeterinariAssociati(List<Veterinario> veterinariAssociati) {
        this.veterinariAssociati = veterinariAssociati;
    }

    public List<NoteProprietario> getNote() {
        return note;
    }

    public void setNote(List<NoteProprietario> note) {
        this.note = note;
    }

    public LinkingCode getLinkingCode() {
        return linkingCode;
    }

    public void setLinkingCode(LinkingCode linkingCode) {
        this.linkingCode = linkingCode;
    }

    public List<RecordMedico> getRecordMedici() {
        return recordMedici;
    }

    public void setRecordMedici(List<RecordMedico> recordMedici) {
        this.recordMedici = recordMedici;
    }
}
