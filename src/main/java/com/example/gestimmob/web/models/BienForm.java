package com.example.gestimmob.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import com.example.gestimmob.dao.enums.TypeBien;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BienForm {

    @NotEmpty(message = "Le titre est obligatoire")
    private String titre;

    @Min(value = 1, message = "La superficie doit être positive")
    private float superficie;

    @Min(value = 0, message = "Le prix doit être positif")
    private float prix;

    @NotEmpty(message = "La localisation est obligatoire")
    private String localisation;

    @NotEmpty(message = "La description est obligatoire")
    private String description;

    @NotEmpty(message = "Le contact est obligatoire")
    private String contact;

    private MultipartFile photo;

    @NotNull(message = "Le type de bien est obligatoire.")
    private TypeBien typeBien;
}