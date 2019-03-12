/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package projectanimal.whatever.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import projectanimal.whatever.ejb.SpeziesBean;
import projectanimal.whatever.ejb.TierartBean;
import projectanimal.whatever.jpa.Spezies;
import projectanimal.whatever.jpa.Tierart;

/**
 *
 * @author simon
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
