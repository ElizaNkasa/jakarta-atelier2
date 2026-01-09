import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;

@Named(value = "navigationController")
@RequestScoped
public class NavigationBean {

    public void voirApropos() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("pages/a_propos.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}