package com.example.gestimmob.business.services;

import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.entities.Bien;
import com.example.gestimmob.dao.enums.TypeBien;

import java.util.List;

public interface AnnonceService {

    List<Annonce> getAllAnnonces();

    Annonce getAnnonceById(Long id);

    Annonce addAnnonce(Annonce annonce);

    Annonce updateAnnonce(Annonce annonce);

    void deleteAnnonce(Long id);

    List<Annonce> getAnnoncesByTypeBien(TypeBien typeBien);

    List<Annonce> getAnnoncesByBien(Bien bien);
}