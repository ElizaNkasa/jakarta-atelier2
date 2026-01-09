package com.udbl.jakartamission1;

import com.jakarkaeeudbl.bienvenue.business.UtilisateurEntrepriseBean;
import com.jakarkaeeudbl.bienvenue.entities.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Managed Bean de session pour l'authentification
 * et la gestion du profil utilisateur
 *
 * @author elisee
 */
@Named("welcomeBean")
@SessionScoped
public class WelcomeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String password;

    // Utilisateur connecté
    private Utilisateur utilisateurConnecte;

    // Nouveau mot de passe (profil)
    private String nouveauMotDePasse;

    @EJB
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    /* =======================
       GETTERS & SETTERS
       ======================= */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    /* =======================
       AUTHENTIFICATION
       ======================= */

    public String sAuthentifier() {

        utilisateurConnecte =
                utilisateurEntrepriseBean.authentifier(email, password);

        if (utilisateurConnecte != null) {

            // Nettoyage des champs sensibles
            password = null;

            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Connexion réussie",
                        "Bienvenue " + utilisateurConnecte.getUsername())
            );

            return "/home?faces-redirect=true";

        } else {

            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Erreur",
                        "Email ou mot de passe incorrect")
            );
            return null;
        }
    }

    /* =======================
       VÉRIFICATION DE SESSION
       ======================= */

    public boolean estConnecte() {
        return utilisateurConnecte != null;
    }

    /* =======================
       MODIFICATION MOT DE PASSE
       ======================= */

    public String modifierMotDePasse() {

        if (utilisateurConnecte == null) {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Session expirée",
                        "Veuillez vous reconnecter")
            );
            return null;
        }

        if (nouveauMotDePasse == null || nouveauMotDePasse.trim().length() < 6) {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Mot de passe invalide",
                        "Le mot de passe doit contenir au moins 6 caractères")
            );
            return null;
        }

        utilisateurEntrepriseBean.updatePassword(
                utilisateurConnecte,
                nouveauMotDePasse
        );

        FacesContext.getCurrentInstance().addMessage(
            null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Succès",
                    "Mot de passe mis à jour avec succès")
        );

        nouveauMotDePasse = null;
        return null;
    }

    /* =======================
       DÉCONNEXION
       ======================= */

    public String logout() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }
}
