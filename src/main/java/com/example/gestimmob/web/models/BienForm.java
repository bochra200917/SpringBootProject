package com.example.gestimmob.web.models;

import org.springframework.web.multipart.MultipartFile;

import com.example.gestimmob.dao.enums.TypeAnnonce;
import com.example.gestimmob.dao.enums.TypeBien;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BienForm {

    @NotEmpty(message = "Le titre est obligatoire")
    private String titre;

    @DecimalMin(value = "0.1", message = "La superficie doit être strictement positive")
    private float superficie;

    @DecimalMin(value = "0.1", message = "Le prix doit être strictement positif")
    private float prix;

    @NotEmpty(message = "La localisation est obligatoire")
    private String localisation;

    @NotEmpty(message = "La description est obligatoire")
    private String description;

    @Pattern(regexp = "^[0-9]{8}$", message = "Le contact doit comporter 8 chiffres")
    @NotEmpty(message = "Le contact est obligatoire")
    private String contact;

    @NotNull(message = "La photo est obligatoire")
    private MultipartFile photo;

    @NotNull(message = "Le type de bien est obligatoire")
    private TypeBien typeBien;

    @NotNull(message = "Le type de l'annonce est obligatoire")
    private TypeAnnonce typeAnnonce;
}