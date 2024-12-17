package com.example.gestimmob.business.servicesImpl;

import com.example.gestimmob.business.services.AnnonceService;
import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.entities.Bien;
import com.example.gestimmob.dao.enums.TypeAnnonce;
import com.example.gestimmob.dao.enums.TypeBien;
import com.example.gestimmob.dao.repositories.AnnonceRepository;
import com.example.gestimmob.dao.repositories.BienRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class AnnonceServiceImpl implements AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private BienRepository bienRepository;

    @Override
    public List<Annonce> getAllAnnonces() {
        try {
            log.info("Récupération de toutes les annonces");
            return annonceRepository.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des annonces", e);
            throw new RuntimeException("Une erreur est survenue lors de la récupération des annonces");
        }
    }

    @Override
    public Annonce getAnnonceById(Long id) {
        log.info("Recherche de l'annonce par ID : {}", id);
        try {
            Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);
            if (optionalAnnonce.isPresent()) {
                return optionalAnnonce.get();
            } else {
                log.error("Annonce avec l'ID {} non trouvée", id);
                throw new RuntimeException("Annonce non trouvée");
            }
        } catch (Exception e) {
            log.error("Erreur lors de la recherche de l'annonce avec l'ID {}", id, e);
            throw new RuntimeException("Une erreur est survenue lors de la recherche de l'annonce", e);
        }
    }

    @Override
    public Annonce addAnnonce(Annonce annonce) {
        try {
            log.info("Ajout de l'annonce : {}", annonce);
            return annonceRepository.save(annonce);
        } catch (Exception e) {
            log.error("Erreur lors de l'ajout de l'annonce", e);
            throw new RuntimeException("Une erreur est survenue lors de l'ajout de l'annonce");
        }
    }

    @Override
    public Annonce updateAnnonce(Annonce annonce) {
        try {
            log.info("Recherche des annonces par ID : {}", annonce.getId());
            Annonce existingAnnonce = annonceRepository.findById(annonce.getId())
                    .orElseThrow(() -> {
                        log.error("Annonce avec l'ID {} non trouvée pour la mise à jour", annonce.getId());
                        return new RuntimeException("Annonce non trouvée");
                    });

            // Mettre à jour les propriétés de l'annonce
            existingAnnonce.setEcheance(annonce.getEcheance());

            // Mettre à jour le bien et ses propriétés (ici, typeAnnonce)
            Bien bien = existingAnnonce.getBien();
            if (bien != null) {
                bien.setTypeAnnonce(annonce.getBien().getTypeAnnonce()); // Met à jour le typeAnnonce du bien
                bienRepository.save(bien); // Sauvegarde du bien si nécessaire
            }

            // Sauvegarder l'annonce mise à jour
            log.info("Annonce avec l'ID {} mise à jour avec succès", annonce.getId());
            return annonceRepository.save(existingAnnonce);
        } catch (RuntimeException e) {
            log.error("Annonce avec l'ID {} non trouvée pour la mise à jour", annonce.getId(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'annonce avec l'ID {}", annonce.getId(), e);
            throw new RuntimeException("Une erreur est survenue lors de la mise à jour de l'annonce", e);
        }
    }

    @Override
    public void deleteAnnonce(Long id) {
        try {
            log.info("Suppression de l'annonce avec l'ID : {}", id);
            if (!annonceRepository.existsById(id)) {
                log.error("Annonce avec l'ID {} non trouvée pour la suppression", id);
                throw new RuntimeException("Annonce non trouvée");
            }
            annonceRepository.deleteById(id);
            log.info("Annonce avec l'ID {} supprimée avec succès", id);
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'annonce avec l'ID {}", id, e);
            throw new RuntimeException("Une erreur est survenue lors de la suppression de l'annonce", e);
        }
    }

    @Override
    public List<Annonce> getAnnoncesByBien(Bien bien) {
        try {
            log.info("Recherche des annonces par bien : {}", bien);
            return annonceRepository.findByBien(bien);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des annonces pour le bien {}", bien, e);
            throw new RuntimeException("Une erreur est survenue lors de la récupération des annonces par bien");
        }
    }

    @Override
    public List<Annonce> getAnnoncesByTypeBien(TypeBien typeBien) {
        try {
            log.info("Recherche des annonces par type de bien : {}", typeBien);
            return typeBien != null
                    ? annonceRepository.findByBien_TypeBien(typeBien)
                    : annonceRepository.findAll();
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("Erreur lors de la recherche des annonces par type de bien : {}", typeBien, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Annonce> getAnnoncesByTypeAnnonce(TypeAnnonce typeAnnonce) {
        try {
            log.info("Recherche des annonces par type d'annonce : {}", typeAnnonce);
            return typeAnnonce != null
                    ? annonceRepository.findByTypeAnnonce(typeAnnonce)
                    : annonceRepository.findAll();
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("Erreur lors de la recherche des annonces par type d'annonce : {}", typeAnnonce, e);
            //return Collections.emptyList();
            throw new RuntimeException("Une erreur est survenue lors de la recherche des annonces par type d'annonce", e);
        }
    }

    @Override
    public List<Annonce> getAnnoncesByTypeBienAndTypeAnnonce(TypeBien typeBien, TypeAnnonce typeAnnonce) {
        try {
            log.info("Recherche des annonces par type de bien et type d'annonce : {} - {}", typeBien, typeAnnonce);
            return annonceRepository.findByBien_TypeBienAndTypeAnnonce(typeBien, typeAnnonce);
        } catch (Exception e) {
            // e.printStackTrace();
            //return Collections.emptyList();
            throw new RuntimeException("Une erreur est survenue lors de la recherche des annonces par type de bien et type d'annonce", e);
        }
    }

}