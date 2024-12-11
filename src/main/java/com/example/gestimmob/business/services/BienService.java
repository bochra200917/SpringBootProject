package com.example.gestimmob.business.services;

import com.example.gestimmob.dao.entities.Bien;

import java.util.List;

public interface BienService {

    List<Bien> getAllImmobiliers();

    Bien getImmobilierById(Long idIm);

    Bien addImmobilier(Bien immobilier);

    Bien updateImmobilier(Bien immobilier);

    void deleteImmobilierById(Long idIm);
}