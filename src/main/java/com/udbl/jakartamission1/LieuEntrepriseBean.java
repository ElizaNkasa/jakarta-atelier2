package com.udbl.jakartamission1;

import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Stateless
public class LieuEntrepriseBean {

    private static List<Lieu> databaseSimulee = new ArrayList<>();
    // Pour générer des ID uniques (1, 2, 3...)
    private static AtomicLong idGenerator = new AtomicLong(1);

    public List<Lieu> listerTousLesLieux() {
        return databaseSimulee;
    }

    // Gère l'AJOUT et la MODIFICATION
    public void sauvegarderLieu(Long id, String nom, String description, double lat, double lon) {
        if (id == null) {
            // C'est un NOUVEAU lieu
            Lieu nouveau = new Lieu(idGenerator.getAndIncrement(), nom, description, lat, lon);
            databaseSimulee.add(nouveau);
        } else {
            // C'est une MODIFICATION
            // On cherche le lieu existant et on remplace ses données
            for (Lieu l : databaseSimulee) {
                if (l.getId().equals(id)) {
                    l.setNom(nom);
                    l.setDescription(description);
                    l.setLatitude(lat);
                    l.setLongitude(lon);
                    break;
                }
            }
        }
    }

    public void supprimerLieu(Long id) {
        // On retire le lieu qui a cet ID
        databaseSimulee.removeIf(l -> l.getId().equals(id));
    }
}