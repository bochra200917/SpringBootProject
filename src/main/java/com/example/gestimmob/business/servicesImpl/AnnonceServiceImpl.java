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

import java.util.Collections;
import java.util.List;

@Service
public class AnnonceServiceImpl implements AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private BienRepository bienRepository;

    @Override
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @Override
    public Annonce getAnnonceById(Long id) {
        return annonceRepository.findById(id).orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
    }

    @Override
    public Annonce addAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    @Override
    public Annonce updateAnnonce(Annonce annonce) {
        // Récupérer l'annonce existante
        Annonce existingAnnonce = annonceRepository.findById(annonce.getId())
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));

        // Mettre à jour les propriétés de l'annonce
        existingAnnonce.setEcheance(annonce.getEcheance());

        // Mettre à jour le bien et ses propriétés (ici, typeAnnonce)
        Bien bien = existingAnnonce.getBien();
        if (bien != null) {
            bien.setTypeAnnonce(annonce.getBien().getTypeAnnonce()); // Met à jour le typeAnnonce du bien
            bienRepository.save(bien); // Sauvegarde du bien si nécessaire
        }

        // Sauvegarder l'annonce mise à jour
        return annonceRepository.save(existingAnnonce);
    }

    @Override
    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }

    @Override
    public List<Annonce> getAnnoncesByBien(Bien bien) {
        return annonceRepository.findByBien(bien);
    }

    @Override
    public List<Annonce> getAnnoncesByTypeBien(TypeBien typeBien) {
        try {
            return typeBien != null
                    ? annonceRepository.findByBien_TypeBien(typeBien)
                    : annonceRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Annonce> getAnnoncesByTypeAnnonce(TypeAnnonce typeAnnonce) {
        try {
            return typeAnnonce != null
                    ? annonceRepository.findByTypeAnnonce(typeAnnonce)
                    : annonceRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Annonce> getAnnoncesByTypeBienAndTypeAnnonce(TypeBien typeBien, TypeAnnonce typeAnnonce) {
        try {
            return annonceRepository.findByBien_TypeBienAndTypeAnnonce(typeBien, typeAnnonce);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}