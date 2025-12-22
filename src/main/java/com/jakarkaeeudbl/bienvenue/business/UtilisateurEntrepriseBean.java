package com.jakarkaeeudbl.bienvenue.business;

import com.jakarkaeeudbl.bienvenue.entities.Utilisateur;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author elisee
 */
@Stateless
@LocalBean
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    /**
     * Ajouter un utilisateur entreprise
     * @param username
     * @param email
     * @param password
     * @param description
     */
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
        em.persist(utilisateur);
    }

    /**
     * Vérifier le mot de passe
     * @param password
     * @param hashedPassword
     * @return 
     */
    public boolean verifierMotDePasse(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    /**
     * Lister tous les utilisateurs
     * @return 
     */
    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery(
                "SELECT u FROM Utilisateur u",
                Utilisateur.class
        ).getResultList();
    }

    /**
     * Supprimer un utilisateur par ID
     * @param id
     */
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    /**
     * Trouver un utilisateur par ID
     * @param id
     * @return 
     */
    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    /**
     * Trouver un utilisateur par email
     * @param email
     * @return 
     */
    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email",
                    Utilisateur.class
            )
            .setParameter("email", email)
            .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean emailExiste(String email) {
    return !em.createQuery(
        "SELECT u FROM Utilisateur u WHERE u.email = :email"
    )
    .setParameter("email", email)
    .getResultList()
    .isEmpty();
}

}
