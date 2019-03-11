package dhbwka.wwi.vertsys.javaee.projectanimal.tasks.web;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb.SpeziesBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb.TierartBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Spezies;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Tierart;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anzeigen und Bearbeiten der Spezies. Die Seite besitzt ein
 * Formular, mit dem ein neue Spezies angelegt werden kann, sowie eine Liste,
 * die zum Löschen der Spezies verwendet werden kann.
 */
@WebServlet(urlPatterns = {"/app/tierarten/spezies/"})
public class SpeziesListServlet extends HttpServlet {

    @EJB
    SpeziesBean speziesBean;

    @EJB
    TierartBean tierartBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Spezies ermitteln
        request.setAttribute("categories", this.speziesBean.findAllSorted());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/animals/spezies_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("categories_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen        
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createSpezies(request, response);
                break;
            case "delete":
                this.deleteSpezies(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Spezies anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createSpezies(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");

        Spezies spezies = new Spezies(name);
        List<String> errors = this.validationBean.validate(spezies);

        // Neue Spezies anlegen
        if (errors.isEmpty()) {
            this.speziesBean.saveNew(spezies);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("categories_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Spezies löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteSpezies(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Spezies IDs auslesen
        String[] speziesIds = request.getParameterValues("category");

        if (speziesIds == null) {
            speziesIds = new String[0];
        }

        // Spezies löschen
        for (String speziesId : speziesIds) {
            // Zu löschende Spezies ermitteln
            Spezies spezies;

            try {
                spezies = this.speziesBean.findById(Long.parseLong(speziesId));
            } catch (NumberFormatException ex) {
                continue;
            }

            if (spezies == null) {
                continue;
            }

            // Bei allen betroffenen Aufgaben, den Bezug zur Spezies aufheben
            List<Tierart> tierarten = spezies.getTierarten();

            if (tierarten != null) {
                tierarten.forEach((Tierart tierart) -> {
                    tierart.setCategory(null);
                    this.tierartBean.update(tierart);
                });
            }

            // Und weg damit
            this.speziesBean.delete(spezies);
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }

}
