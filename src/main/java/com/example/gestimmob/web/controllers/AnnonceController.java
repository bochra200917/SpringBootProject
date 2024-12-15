package com.example.gestimmob.web.controllers;

import com.example.gestimmob.business.services.AnnonceService;
import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.enums.TypeAnnonce;
import com.example.gestimmob.dao.enums.TypeBien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @GetMapping("/annonces")
    public String listAnnonces(Model model) {
        List<Annonce> annonces = annonceService.getAllAnnonces();
        model.addAttribute("annonces", annonces);
        return "annonces";
    }

    @GetMapping("/filter")
    public String filterAnnonces(
            @RequestParam(required = false) String typeBien,
            @RequestParam(required = false) String typeAnnonce,
            Model model) {
        List<Annonce> annonces;
        try {
            TypeBien typeBienEnum = (typeBien != null && !typeBien.isEmpty()) ? TypeBien.valueOf(typeBien) : null;
            TypeAnnonce typeAnnonceEnum = (typeAnnonce != null && !typeAnnonce.isEmpty())
                    ? TypeAnnonce.valueOf(typeAnnonce)
                    : null;

            if (typeBienEnum != null && typeAnnonceEnum != null) {
                annonces = annonceService.getAnnoncesByTypeBienAndTypeAnnonce(typeBienEnum, typeAnnonceEnum);
            } else if (typeBienEnum != null) {
                annonces = annonceService.getAnnoncesByTypeBien(typeBienEnum);
            } else if (typeAnnonceEnum != null) {
                annonces = annonceService.getAnnoncesByTypeAnnonce(typeAnnonceEnum);
            } else {
                annonces = annonceService.getAllAnnonces();
            }
        } catch (IllegalArgumentException e) {
            annonces = annonceService.getAllAnnonces();
        }
        model.addAttribute("annonces", annonces);
        model.addAttribute("typeBien", typeBien);
        model.addAttribute("typeAnnonce", typeAnnonce);
        return "annonces";
    }
}