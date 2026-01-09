package com.jakarkaeeudbl.bienvenue.business;

import com.jakarkaeeudbl.bienvenue.entities.Utilisateur;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
@LocalBean
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    /* =======================
       AJOUT UTILISATEUR
       ======================= */
    public void ajouterUtilisateurEntreprise(String username, String email,
                                              String password, String description) {

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur =
                new Utilisateur(username, email, hashedPassword, description);

        em.persist(utilisateur);
    }

    /* =======================
       VÉRIFICATION MOT DE PASSE
       ======================= */
    public boolean verifierMotDePasse(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    /* =======================
       LISTE DES UTILISATEURS
       ======================= */
    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery(
                "SELECT u FROM Utilisateur u", Utilisateur.class)
                .getResultList();
    }

    /* =======================
       SUPPRESSION UTILISATEUR
       ======================= */
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    /* =======================
       RECHERCHE PAR ID
       ======================= */
    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    /* =======================
       RECHERCHE PAR EMAIL
       ======================= */
    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email",
                    Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /* =======================
       VÉRIFIER EXISTENCE EMAIL
       ======================= */
    public boolean emailExiste(String email) {
        return !em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.email = :email")
                .setParameter("email", email)
                .getResultList()
                .isEmpty();
    }

    /* =======================
       AUTHENTIFICATION
       ======================= */
    public Utilisateur authentifier(String email, String password) {

        Utilisateur utilisateur = trouverUtilisateurParEmail(email);

        if (utilisateur != null &&
            verifierMotDePasse(password, utilisateur.getPassword())) {
            return utilisateur;
        }
        return null;
    }

    /* =======================
       ✅ MISE À JOUR MOT DE PASSE
       ======================= */
    public void updatePassword(Utilisateur utilisateurConnecte,
                               String nouveauMotDePasse) {

        // Récupérer une entité MANAGED
        Utilisateur utilisateur = em.find(
                Utilisateur.class,
                utilisateurConnecte.getId()
        );

        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        // Hasher le nouveau mot de passe
        String hashedPassword = BCrypt.hashpw(
                nouveauMotDePasse,
                BCrypt.gensalt()
        );

        utilisateur.setPassword(hashedPassword);

        // merge facultatif mais recommandé
        em.merge(utilisateur);
    }
}
