package dhbwka.wwi.vertsys.javaee.projectanimal.tasks.web;

import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb.SpeziesBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb.TierartBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Spezies;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Tierart;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die tabellarische Auflisten der Tierarten.
 */
@WebServlet(urlPatterns = {"/app/tierarten/list/"})
public class TierartListServlet extends HttpServlet {

    @EJB
    private SpeziesBean speziesBean;
    
    @EJB
    private TierartBean tierartBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Spezies und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.speziesBean.findAllSorted());
       
        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchSpezies = request.getParameter("search_spezies");
       
        // Anzuzeigende Aufgaben suchen
        Spezies spezies = null;
       
        if (searchSpezies != null) {
            try {
                spezies = this.speziesBean.findById(Long.parseLong(searchSpezies));
            } catch (NumberFormatException ex) {
                spezies = null;
            }
        }

        List<Tierart> tierarten = this.tierartBean.search(searchText, spezies);
        request.setAttribute("tierarten", tierarten);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/animals/tierart_list.jsp").forward(request, response);
    }
}
