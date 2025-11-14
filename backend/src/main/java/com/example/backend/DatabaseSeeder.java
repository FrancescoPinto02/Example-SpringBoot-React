package com.example.backend;

import com.example.backend.model.*;
import com.example.backend.repository.PetRepository;
import com.example.backend.repository.ProprietarioRepository;
import com.example.backend.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ProprietarioRepository proprietarioRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final PetRepository petRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.db.reset:false}")
    private boolean resetDatabase;

    public DatabaseSeeder(ProprietarioRepository proprietarioRepository, VeterinarioRepository veterinarioRepository, PetRepository petRepository, PasswordEncoder passwordEncoder) {
        this.proprietarioRepository = proprietarioRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.petRepository = petRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (resetDatabase) {
            System.out.println("⚠️ Pulizia del database in corso... ⚠️");
            proprietarioRepository.deleteAll();
            veterinarioRepository.deleteAll();
        }

        if (proprietarioRepository.count() == 0) {
            System.out.println("🚀 Popolamento iniziale del database...");

            // --- UTENTI ---
            Proprietario mario = new Proprietario();
            mario.setEmail("mario.rossi@example.com");
            mario.setPassword(passwordEncoder.encode("Password123"));
            mario.setNome("Mario");
            mario.setCognome("Rossi");
            proprietarioRepository.save(mario);

            // --- PETS ---
            Pet dog = new Pet();
            dog.setNome("Fido");
            dog.setnMicrochip("123ABC456A");
            dog.setFoto(loadImage("images/dog1.jpg"));
            dog.setProprietario(mario);
            mario.getPets().add(dog);
            petRepository.save(dog);

            Veterinario vet = new Veterinario();
            vet.setNome("Giovanni");
            vet.setCognome("Bianchi");
            vet.setEmail("giovanni.vet@example.com");
            vet.setPassword(passwordEncoder.encode("Password123"));
            vet.getPetsAssociati().add(dog);
            dog.getVeterinariAssociati().add(vet);
            veterinarioRepository.save(vet);

            // ============================================================
            //          AGGIUNTA RECORD MEDICI DI TEST
            // ============================================================

            // 1️⃣ Visita medica
            VisitaMedica visita = new VisitaMedica();
            visita.setNome("Controllo annuale");
            visita.setDescrizione("Visita di controllo generale.");
            visita.setData(new Date());
            visita.setPet(dog);
            visita.setVeterinario(vet);
            dog.getRecordMedici().add(visita);

            // 2️⃣ Vaccinazione
            Vaccinazione vaccino = new Vaccinazione();
            vaccino.setNome("Vaccino Rabbia");
            vaccino.setTipologia("Antirabbica");
            vaccino.setDataDiSomministrazione(new Date());
            vaccino.setDoseSomministrata(1.0f);
            vaccino.setViaDiSomministrazione(Vaccinazione.Somministrazione.INTRAMUSCOLARE);
            vaccino.setEffettiCollaterali("Nessuno");
            vaccino.setRichiamoPrevisto(new Date(System.currentTimeMillis() + 31536000000L)); // +1 anno
            vaccino.setPet(dog);
            vaccino.setVeterinario(vet);
            dog.getRecordMedici().add(vaccino);

            // 3️⃣ Patologia
            Patologia patologia = new Patologia();
            patologia.setNome("Dermatite");
            patologia.setDataDiDiagnosi(new Date());
            patologia.setSintomiOsservati("Arrossamento e prurito");
            patologia.setDiagnosi("Dermatite atopica");
            patologia.setTerapiaAssociata("Shampoo medicato e antistaminici");
            patologia.setPet(dog);
            patologia.setVeterinario(vet);
            dog.getRecordMedici().add(patologia);

            // 4️⃣ Terapia
            Terapia terapia = new Terapia();
            terapia.setNome("Terapia antibiotica");
            terapia.setDescrizione("Amoxicillina 250mg");
            terapia.setDataInizio(new Date());
            terapia.setDataFine(new Date(System.currentTimeMillis() + 7 * 86400000L)); // +7 giorni
            terapia.setPet(dog);
            terapia.setVeterinario(vet);
            dog.getRecordMedici().add(terapia);

            // Salva tutto
            petRepository.save(dog);


            System.out.println("✅ Database popolato");
        } else {
            System.out.println("ℹ️ Database già popolato, nessuna azione necessaria.");
        }
    }

    /**
     * Carica un'immagine dalla cartella resources in un array di byte
     */
    private byte[] loadImage(String path) {
        try {
            ClassPathResource imgFile = new ClassPathResource(path);
            return Files.readAllBytes(imgFile.getFile().toPath());
        } catch (IOException e) {
            System.err.println("⚠️  Impossibile caricare immagine: " + path + " → " + e.getMessage());
            return null;
        }
    }
}
