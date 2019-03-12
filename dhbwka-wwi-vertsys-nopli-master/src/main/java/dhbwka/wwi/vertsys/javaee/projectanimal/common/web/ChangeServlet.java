package dhbwka.wwi.vertsys.javaee.projectanimal.common.web;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.jpa.User;
import java.io.IOException;
import java.util.List;
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
        
        // Formulareingaben auslesen        
        String username = request.getParameter("change_username");
        String password1 = request.getParameter("change_password1");
        String password2 = request.getParameter("change_password2");
        String vorname = request.getParameter("change_vorname");
        String nachname = request.getParameter("change_nachname");
        
        // Eingaben prüfen
        User user = new User(username, password1, vorname, nachname);
        List<String> errors = this.validationBean.validate(user);
        this.validationBean.validate(user.getPassword(), errors);
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        // Neuen Benutzer anlegen
        if (errors.isEmpty()) {
            try {
                this.userBean.signup(username, password1, vorname, nachname);
            } catch (UserBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
        }
        
        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            request.login(username, password1);
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("change_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Spezies und Stati für die Suchfelder ermitteln
        request.setAttribute("current_user", this.userBean.getCurrentUser());
       
        // Anzuzeigende Aufgaben suchen
        User user;
        user = this.userBean.getCurrentUser();
        request.setAttribute("user", user);
        request.setAttribute("change_vorname", user.getVorname());
        request.setAttribute("change_nachname", user.getNachname());
        request.setAttribute("change_username", user.getUsername());
        

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/login/change.jsp").forward(request, response);
    }
}

