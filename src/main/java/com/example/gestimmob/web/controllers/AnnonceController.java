package com.example.gestimmob.web.controllers;

import com.example.gestimmob.business.services.AnnonceService;
import com.example.gestimmob.dao.entities.Annonce;
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

    @GetMapping("/annonces/filter")
    public String filterAnnoncesByType(@RequestParam(required = false) String typeBien,
            Model model) {
        List<Annonce> annonces;
        if (typeBien == null || typeBien.isEmpty()) {
            annonces = annonceService.getAllAnnonces();
        } else {
            try {
                TypeBien type = TypeBien.valueOf(typeBien);
                annonces = annonceService.getAnnoncesByTypeBien(type);
            } catch (IllegalArgumentException e) {
                annonces = annonceService.getAllAnnonces();
            }
        }
        model.addAttribute("annonces", annonces);
        model.addAttribute("typeBien", typeBien);
        return "annonces";
    }
}