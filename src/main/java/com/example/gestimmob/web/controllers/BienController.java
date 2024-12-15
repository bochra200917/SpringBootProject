package com.example.gestimmob.web.controllers;

import com.example.gestimmob.business.services.AnnonceService;
import com.example.gestimmob.business.services.BienService;
import com.example.gestimmob.dao.entities.Annonce;
import com.example.gestimmob.dao.entities.Bien;
import com.example.gestimmob.dao.enums.TypeAnnonce;
import com.example.gestimmob.dao.enums.TypeBien;
import com.example.gestimmob.web.models.BienForm;

import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/immobilier")
public class BienController {

    @Autowired
    private BienService immobilierService;

    @Autowired
    private AnnonceService annonceService;

    @GetMapping("/list")
    public String getImmobilierList(Model model) {
        model.addAttribute("immobiliers", immobilierService.getAllImmobiliers());
        return "immobilier-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("immobilierForm", new BienForm());
        model.addAttribute("typeBien", TypeBien.values());
        model.addAttribute("typeAnnonce", TypeAnnonce.values());
        return "add-immobilier";
    }

    @PostMapping("/create")
    public String createImmobilier(
            @Valid @ModelAttribute BienForm immobilierForm,
            BindingResult bindingResult,
            @RequestParam MultipartFile photo,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeBien", TypeBien.values());
            model.addAttribute("typeAnnonce", TypeAnnonce.values());
            return "add-immobilier";
        }
        Bien immobilier = new Bien();
        immobilier.setTitre(immobilierForm.getTitre());
        immobilier.setSuperficie(immobilierForm.getSuperficie());
        immobilier.setPrix(immobilierForm.getPrix());
        immobilier.setLocalisation(immobilierForm.getLocalisation());
        immobilier.setDescription(immobilierForm.getDescription());
        immobilier.setContact(immobilierForm.getContact());
        immobilier.setTypeBien(immobilierForm.getTypeBien());
        immobilier.setTypeAnnonce(immobilierForm.getTypeAnnonce());
        if (!photo.isEmpty()) {
            String fileName = photo.getOriginalFilename();
            Path imagePath = Paths.get("src/main/resources/static/images/" + fileName);
            if (!Files.exists(imagePath)) {
                try {
                    Files.write(imagePath, photo.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("errorMessage", "Erreur lors du téléchargement de la photo.");
                    model.addAttribute("typeBien", TypeBien.values());
                    model.addAttribute("typeAnnonce", TypeAnnonce.values());
                    return "add-immobilier";
                }
            }
            immobilier.setPhoto("images/" + fileName);
        }
        immobilierService.addImmobilier(immobilier);
        Annonce annonce = new Annonce();
        annonce.setBien(immobilier);
        annonce.setTypeAnnonce(immobilierForm.getTypeAnnonce()); // TypeAnnonce récupéré du formulaire
        annonce.setEcheance(LocalDate.now().plusMonths(6));
        annonceService.addAnnonce(annonce);
        return "redirect:/immobilier/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Bien immobilier = immobilierService.getImmobilierById(id);
        if (immobilier == null) {
            return "redirect:/immobilier/list";
        }
        BienForm immobilierForm = new BienForm();
        immobilierForm.setTitre(immobilier.getTitre());
        immobilierForm.setSuperficie(immobilier.getSuperficie());
        immobilierForm.setPrix(immobilier.getPrix());
        immobilierForm.setLocalisation(immobilier.getLocalisation());
        immobilierForm.setDescription(immobilier.getDescription());
        immobilierForm.setContact(immobilier.getContact());
        immobilierForm.setTypeBien(immobilier.getTypeBien());
        immobilierForm.setTypeAnnonce(immobilier.getTypeAnnonce()); // Ajout du typeAnnonce

        model.addAttribute("immobilierForm", immobilierForm);
        model.addAttribute("immobilierId", id);
        model.addAttribute("typeBien", TypeBien.values());
        model.addAttribute("typeAnnonce", TypeAnnonce.values()); // Ajout des types d'annonces
        return "edit-immobilier";
    }

    @PostMapping("/update/{id}")
    public String updateImmobilier(
            @PathVariable Long id,
            @Valid @ModelAttribute BienForm immobilierForm,
            BindingResult bindingResult,
            @RequestParam MultipartFile photo,
            @RequestParam Long immobilierId,
            Model model) {
        if (!id.equals(immobilierId)) {
            model.addAttribute("errorMessage", "Les IDs ne correspondent pas.");
            model.addAttribute("typeBien", TypeBien.values());
            model.addAttribute("typeAnnonce", TypeAnnonce.values());
            return "edit-immobilier";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeBien", TypeBien.values());
            model.addAttribute("typeAnnonce", TypeAnnonce.values());
            return "edit-immobilier";
        }

        Bien immobilier = immobilierService.getImmobilierById(immobilierId);
        if (immobilier == null) {
            return "redirect:/immobilier/list";
        }

        // Mise à jour des champs de Bien
        immobilier.setTitre(immobilierForm.getTitre());
        immobilier.setSuperficie(immobilierForm.getSuperficie());
        immobilier.setPrix(immobilierForm.getPrix());
        immobilier.setLocalisation(immobilierForm.getLocalisation());
        immobilier.setDescription(immobilierForm.getDescription());
        immobilier.setContact(immobilierForm.getContact());
        immobilier.setTypeBien(immobilierForm.getTypeBien());
        immobilier.setTypeAnnonce(immobilierForm.getTypeAnnonce());

        // Gestion de l'image
        if (!photo.isEmpty()) {
            String fileName = photo.getOriginalFilename();
            Path imagePath = Paths.get("src/main/resources/static/images/" + fileName);
            if (!Files.exists(imagePath)) {
                try {
                    Files.write(imagePath, photo.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("errorMessage", "Erreur lors du téléchargement de la photo.");
                    model.addAttribute("typeBien", TypeBien.values());
                    model.addAttribute("typeAnnonce", TypeAnnonce.values());
                    return "edit-immobilier";
                }
            }
            immobilier.setPhoto("images/" + fileName);
        }

        // Mettre à jour le bien
        immobilierService.updateImmobilier(immobilier);

        // Mise à jour de l'annonce associée si elle existe
        if (immobilier.getAnnonce() != null) {
            // Propagation du typeAnnonce de Bien vers l'Annonce
            Annonce annonce = immobilier.getAnnonce();
            annonce.setTypeAnnonce(immobilier.getTypeAnnonce()); // Synchronisation du typeAnnonce
            annonceService.updateAnnonce(annonce); // Mise à jour de l'annonce
        }

        return "redirect:/immobilier/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteImmobilier(@PathVariable Long id) {
        Bien bien = immobilierService.getImmobilierById(id);
        if (bien != null) {
            List<Annonce> annonces = annonceService.getAnnoncesByBien(bien);
            annonces.forEach(annonce -> annonceService.deleteAnnonce(annonce.getId()));
        }
        immobilierService.deleteImmobilierById(id);
        return "redirect:/immobilier/list";
    }
}