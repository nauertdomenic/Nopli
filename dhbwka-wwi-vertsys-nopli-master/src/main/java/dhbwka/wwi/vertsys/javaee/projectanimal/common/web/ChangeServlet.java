package dhbwka.wwi.vertsys.javaee.projectanimal.common.web;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.jpa.User;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author phoenix
 /**
 * Servlet für die Registrierungsseite. Hier kann sich ein neuer Benutzer
 * registrieren. Anschließend wird der auf die Startseite weitergeleitet.
 */
@WebServlet(urlPatterns = {"/app/change/"})
public class ChangeServlet extends HttpServlet {
    
    @EJB
    ValidationBean validationBean;
            
    @EJB
    UserBean userBean;
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String fehler = "";
        String vorname = request.getParameter("change_vorname");
        String nachname = request.getParameter("change_nachname");
        String passwort = request.getParameter("change_password");
        
        if (vorname == null || vorname.trim().isEmpty()) {
            fehler = "Bitte gib erst deinen Namen ein.";
            session.setAttribute("fehler", fehler);
            session.setAttribute("change_vorname", vorname);
            session.setAttribute("change_nachname", nachname);
            session.setAttribute("change_password", passwort);
        }
        
        // Neuen Eintrag speichern
        if (fehler.isEmpty()) {
            User user = userBean.getCurrentUser();
            user.setVorname(vorname);
            user.setNachname(nachname);
                       
            User.Password altesPasswort = user.getPassword();
            
            try {
                this.userBean.changePassword(user, altesPasswort.password, passwort);
            } catch (UserBean.InvalidCredentialsException ex) {
                Logger.getLogger(ChangeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.userBean.update(user);
        }
        
        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getContextPath());
    }
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = userBean.getCurrentUser();
        
        // Verfügbare Spezies und Stati für die Suchfelder ermitteln
        request.setAttribute("current_user", user);
       
        // Anzuzeigende Aufgaben suchen
        request.setAttribute("change_vorname", user.getVorname());
        request.setAttribute("change_nachname", user.getNachname());

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/login/change.jsp").forward(request, response);
    }
}

