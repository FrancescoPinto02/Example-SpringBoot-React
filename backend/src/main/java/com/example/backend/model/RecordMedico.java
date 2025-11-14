package com.example.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "record_medico")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_record")
public abstract class RecordMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_creazione", nullable = false)
    private Date dataCreazione = new Date();

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Veterinario veterinario;

    public Long getId() { return id; }
    public Date getDataCreazione() { return dataCreazione; }
    public Pet getPet() { return pet; }
    public Veterinario getVeterinario() { return veterinario; }

    public void setPet(Pet pet) { this.pet = pet; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }
}

