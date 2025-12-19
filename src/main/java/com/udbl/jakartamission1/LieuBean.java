package com.udbl.jakartamission1;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped; // ATTENTION : Changement d'import !
import java.io.Serializable;
import java.util.List;

@Named(value = "lieuBean")
@ViewScoped // Permet de garder les données en mémoire tant qu'on reste sur la page
public class LieuBean implements Serializable {

    private Long id; // Pour savoir si on modifie ou si on ajoute
    private String nom;
    private String description;
    private double longitude;
    private double latitude;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    // --- Actions ---

    /**
     * Appelé par le bouton "Sauvegarder"
     */
    public void sauvegarder() {
        if (nom != null && !nom.isEmpty()) {
            // On passe l'ID (null si nouveau, rempli si modif)
            lieuEntrepriseBean.sauvegarderLieu(id, nom, description, latitude, longitude);
            reinitialiserFormulaire();
        }
    }

    /**
     * Appelé par le bouton "Modifier" dans le tableau.
     * Remplit le formulaire avec les infos du lieu choisi.
     */
    public void chargerLieuPourModification(Lieu l) {
        this.id = l.getId();
        this.nom = l.getNom();
        this.description = l.getDescription();
        this.latitude = l.getLatitude();
        this.longitude = l.getLongitude();
    }

    /**
     * Appelé par le bouton "Supprimer" dans le tableau.
     */
    public void supprimer(Lieu l) {
        lieuEntrepriseBean.supprimerLieu(l.getId());
    }
    
    /**
     * Vide le formulaire
     */
    public void reinitialiserFormulaire() {
        this.id = null;
        this.nom = "";
        this.description = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    // --- Getters & Setters ---
    public List<Lieu> getLieux() { return lieuEntrepriseBean.listerTousLesLieux(); }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
}