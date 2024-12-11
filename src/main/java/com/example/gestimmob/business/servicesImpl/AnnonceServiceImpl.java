package com.example.gestimmob.business.servicesImpl;

import com.example.gestimmob.business.services.AnnonceService;
import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.entities.Bien;
import com.example.gestimmob.dao.enums.TypeBien;
import com.example.gestimmob.dao.repositories.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AnnonceServiceImpl implements AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

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
        return annonceRepository.save(annonce);
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
}