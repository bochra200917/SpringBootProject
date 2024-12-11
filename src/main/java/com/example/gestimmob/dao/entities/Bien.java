package com.example.gestimmob.dao.entities;

import com.example.gestimmob.dao.enums.TypeBien;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Bien {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIm;

    private String titre;
    private String description;
    private float prix;
    private float superficie;
    private int nbPieces;
    private String localisation;
    private String contact;

    private String photo;

    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;

    @OneToOne(mappedBy = "bien")
    private Annonce annonce;
}