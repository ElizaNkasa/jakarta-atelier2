package com.udbl.jakartamission1;

import java.util.Objects;

public class Lieu {
    private Long id; // Nouvel identifiant
    private String nom;
    private String description;
    private double latitude;
    private double longitude;

    public Lieu() {}

    public Lieu(Long id, String nom, String description, double latitude, double longitude) {
        this.id = id; // On stocke l'ID
        this.nom = nom;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    
    // Utile pour que la liste détecte bien les objets (equals/hashCode)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lieu lieu = (Lieu) o;
        return Objects.equals(id, lieu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}