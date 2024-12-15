package com.example.gestimmob.dao.repositories;

import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.entities.Bien;
import com.example.gestimmob.dao.enums.TypeAnnonce;
import com.example.gestimmob.dao.enums.TypeBien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

    List<Annonce> findByBien(Bien bien); 

    List<Annonce> findByBien_TypeBien(TypeBien typeBien);

    List<Annonce> findByTypeAnnonce(TypeAnnonce typeAnnonce); 

    List<Annonce> findByBien_TypeBienAndTypeAnnonce(TypeBien typeBien, TypeAnnonce typeAnnonce);
}