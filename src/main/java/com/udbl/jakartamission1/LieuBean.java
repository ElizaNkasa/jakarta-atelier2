import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable {

    private String nom;
    private String description;
    private Double latitude;
    private Double longitude;

    public LieuBean() {
    }

    // Méthode pour simuler l'enregistrement
    public String ajouterLieu() {
        // Ici, vous pourriez plus tard ajouter une logique de base de données
        System.out.println("Nouveau lieu ajouté : " + nom);
        return "index?faces-redirect=true"; // Retour à l'accueil après l'ajout
    }

    // Getters et Setters (Obligatoires pour JSF)
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}