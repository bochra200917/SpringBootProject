package com.example.gestimmob.dao.entities;

import com.example.gestimmob.dao.enums.TypeAnnonce;
import jakarta.persistence.*;
import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate echeance;

    @OneToOne
    @JoinColumn(name = "bien_id", referencedColumnName = "idIm")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bien bien;

    @Enumerated(EnumType.STRING)
    private TypeAnnonce typeAnnonce;
}