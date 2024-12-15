package com.example.gestimmob.business.services;

import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.entities.Bien;
import com.example.gestimmob.dao.enums.TypeAnnonce;
import com.example.gestimmob.dao.enums.TypeBien;

import java.util.List;

public interface AnnonceService {

    List<Annonce> getAllAnnonces();

    Annonce getAnnonceById(Long id);

    Annonce addAnnonce(Annonce annonce);

    Annonce updateAnnonce(Annonce annonce);

    void deleteAnnonce(Long id);

    List<Annonce> getAnnoncesByBien(Bien bien);

    List<Annonce> getAnnoncesByTypeBien(TypeBien typeBien);
    
    List<Annonce> getAnnoncesByTypeAnnonce(TypeAnnonce typeAnnonce);

    List<Annonce> getAnnoncesByTypeBienAndTypeAnnonce(TypeBien typeBien, TypeAnnonce typeAnnonce);
}