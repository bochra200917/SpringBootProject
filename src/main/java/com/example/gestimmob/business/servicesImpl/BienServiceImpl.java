package com.example.gestimmob.business.servicesImpl;

import com.example.gestimmob.business.services.BienService;
import com.example.gestimmob.business.services.AnnonceService;
import com.example.gestimmob.dao.entities.Bien;
import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.enums.TypeAnnonce;
import com.example.gestimmob.dao.repositories.BienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BienServiceImpl implements BienService {

    @Autowired
    private BienRepository bienRepository;

    @Autowired
    private AnnonceService annonceService;

    @Override
    public List<Bien> getAllImmobiliers() {
        return bienRepository.findAll();
    }

    @Override
    public Bien getImmobilierById(Long idIm) {
        Optional<Bien> immobilierOpt = bienRepository.findById(idIm);
        return immobilierOpt.orElseThrow(() -> new RuntimeException("Immobilier non trouvé avec l'ID: " + idIm));
    }

    @Override
    public Bien addImmobilier(Bien immobilier) {
        Bien savedBien = bienRepository.save(immobilier);
        Annonce annonce = new Annonce();
        annonce.setBien(savedBien);
        annonce.setTypeAnnonce(TypeAnnonce.location);
        annonce.setEcheance(LocalDate.now().plusMonths(3));
        annonceService.getAllAnnonces();
        return savedBien;
    }

    @Override
    public Bien updateImmobilier(Bien immobilier) {
        return bienRepository.save(immobilier);
    }

    @Override
    public void deleteImmobilierById(Long idIm) {
        bienRepository.deleteById(idIm);
    }
}