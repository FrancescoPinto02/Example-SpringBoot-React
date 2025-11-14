package com.example.backend.gestionePet.dto;

import com.example.backend.gestioneCartellaClinica.dto.PatologiaDTO;
import com.example.backend.gestioneCartellaClinica.dto.TerapiaDTO;
import com.example.backend.gestioneCartellaClinica.dto.VaccinazioneDTO;
import com.example.backend.gestioneCartellaClinica.dto.VisitaMedicaDTO;
import com.example.backend.model.*;

import java.util.List;

public class PetDetailsDTO {

    private Long id;
    private String nome;
    private String nMicrochip;

    private List<VaccinazioneDTO> vaccinazioni;
    private List<VisitaMedicaDTO> visiteMediche;
    private List<PatologiaDTO> patologie;
    private List<TerapiaDTO> terapie;

    public static PetDetailsDTO from(Pet pet) {
        PetDetailsDTO dto = new PetDetailsDTO();
        dto.setId(pet.getId());
        dto.setNome(pet.getNome());
        dto.setnMicrochip(pet.getnMicrochip());

        dto.setVaccinazioni(pet.getRecordMedici().stream()
                .filter(r -> r instanceof Vaccinazione)
                .map(r -> VaccinazioneDTO.from((Vaccinazione) r))
                .toList());

        dto.setPatologie(pet.getRecordMedici().stream()
                .filter(p -> p instanceof Patologia)
                .map(p -> PatologiaDTO.from((Patologia) p))
                .toList());

        dto.setTerapie(pet.getRecordMedici().stream()
                .filter(t -> t instanceof Terapia)
                .map(t -> TerapiaDTO.from((Terapia) t))
                .toList());

        dto.setVisiteMediche(pet.getRecordMedici().stream()
                .filter(vm -> vm instanceof VisitaMedica)
                .map(vm -> VisitaMedicaDTO.from((VisitaMedica) vm))
                .toList());

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

    public String getnMicrochip() {
        return nMicrochip;
    }

    public void setnMicrochip(String nMicrochip) {
        this.nMicrochip = nMicrochip;
    }

    public List<VaccinazioneDTO> getVaccinazioni() {
        return vaccinazioni;
    }

    public void setVaccinazioni(List<VaccinazioneDTO> vaccinazioni) {
        this.vaccinazioni = vaccinazioni;
    }

    public List<VisitaMedicaDTO> getVisiteMediche() {
        return visiteMediche;
    }

    public void setVisiteMediche(List<VisitaMedicaDTO> visiteMediche) {
        this.visiteMediche = visiteMediche;
    }

    public List<PatologiaDTO> getPatologie() {
        return patologie;
    }

    public void setPatologie(List<PatologiaDTO> patologie) {
        this.patologie = patologie;
    }

    public List<TerapiaDTO> getTerapie() {
        return terapie;
    }

    public void setTerapie(List<TerapiaDTO> terapie) {
        this.terapie = terapie;
    }
}
