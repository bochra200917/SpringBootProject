package com.example.gestimmob.business.servicesImpl;

import com.example.gestimmob.business.services.BienService;
import com.example.gestimmob.business.services.AnnonceService;
import com.example.gestimmob.dao.entities.Bien;
import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.enums.TypeAnnonce;
import com.example.gestimmob.dao.repositories.BienRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class BienServiceImpl implements BienService {

    @Autowired
    private BienRepository bienRepository;

    @Autowired
    private AnnonceService annonceService;

    @Override
    public List<Bien> getAllImmobiliers() {
        log.info("Récupération de tous les biens immobiliers.");
        try {
            return bienRepository.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des biens immobiliers : {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la récupération des biens immobiliers", e);
        }
       
    }

    @Override
    public Bien getImmobilierById(Long idIm) {
        log.info("Tentative de récupération du bien immobilier avec ID : {}", idIm);
        try {
            Optional<Bien> immobilierOpt = bienRepository.findById(idIm);
            Bien immobilier = immobilierOpt.orElseThrow(() -> new RuntimeException("Bien immobilier introuvable avec ID : " + idIm));
            log.info("Bien immobilier récupéré avec succès : ID = {}", idIm);
            return immobilier;
        } catch (RuntimeException e) {
            log.error("Erreur lors de la récupération du bien immobilier avec ID : {} : {}", idIm, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Bien addImmobilier(Bien immobilier) {
        log.info("Ajout d'un nouveau bien immobilier : {}", immobilier);
        try {
            Bien savedBien = bienRepository.save(immobilier);

            // Création d'une annonce associée
            Annonce annonce = new Annonce();
            annonce.setBien(savedBien);
            annonce.setTypeAnnonce(TypeAnnonce.location);
            annonce.setEcheance(LocalDate.now().plusMonths(3));
            annonceService.addAnnonce(annonce);

            log.info("Bien immobilier et annonce associée créés avec succès. ID du bien : {}", savedBien.getIdIm());
            return savedBien;
        } catch (Exception e) {
            log.error("Erreur lors de l'ajout d'un bien immobilier : {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'ajout du bien immobilier", e);
        }
    }
  
    @Override
    public Bien updateImmobilier(Bien immobilier) {
        log.info("Mise à jour du bien immobilier avec ID : {}", immobilier.getIdIm());
        try {
            Bien updatedBien = bienRepository.save(immobilier);
            log.info("Bien immobilier mis à jour avec succès : ID = {}", immobilier.getIdIm());
            return updatedBien;
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour du bien immobilier avec ID : {} : {}", immobilier.getIdIm(), e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la mise à jour du bien immobilier", e);
        }
    }
    @Override
    public void deleteImmobilierById(Long idIm) {
        log.info("Suppression du bien immobilier avec ID : {}", idIm);
        try {
            bienRepository.deleteById(idIm);
            log.info("Bien immobilier supprimé avec succès : ID = {}", idIm);
        } catch (Exception e) {
            log.error("Erreur lors de la suppression du bien immobilier avec ID : {} : {}", idIm, e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la suppression du bien immobilier", e);
        }
    }
}