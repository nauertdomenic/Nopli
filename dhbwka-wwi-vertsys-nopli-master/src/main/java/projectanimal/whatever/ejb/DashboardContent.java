/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package projectanimal.whatever.ejb;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.projectanimal.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.projectanimal.dashboard.ejb.DashboardSection;
import dhbwka.wwi.vertsys.javaee.projectanimal.dashboard.ejb.DashboardTile;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import projectanimal.whatever.jpa.Spezies;

/**
 * EJB zur Definition der Dashboard-Kacheln für Tierarten.
 */
@Stateless(name = "tierarten")
public class DashboardContent implements DashboardContentProvider {

    @EJB
    private SpeziesBean speziesBean;

    @EJB
    private TierartBean tierartBean;

    /**
     * Vom Dashboard aufgerufenen Methode, um die anzuzeigenden Rubriken und
     * Kacheln zu ermitteln.
     *
     * @param sections Liste der Dashboard-Rubriken, an die die neuen Rubriken
     * angehängt werden müssen
     */
    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        // Zunächst einen Abschnitt mit einer Gesamtübersicht aller Aufgaben
        // in allen Spezies erzeugen
        DashboardSection section = this.createSection(null);
        sections.add(section);

        // Anschließend je Spezies einen weiteren Abschnitt erzeugen
        List<Spezies> spezien = this.speziesBean.findAllSorted();

        for (Spezies spezies : spezien) {
            section = this.createSection(spezies);
            sections.add(section);
        }
    }

    /**
     * Hilfsmethode, die für die übergebene Tierarten-Spezies eine neue Rubrik
     * mit Kacheln im Dashboard erzeugt. Zusätzlich eine Kachel für alle Tierarten innerhalb der
     * jeweiligen Spezies.
     *
     * Ist die Spezies null, bedeutet dass, dass eine Rubrik für alle Tierarten
     * aus allen Spezies erzeugt werden soll.
     *
     * @param spezies Tierarten-Spezies, für die Kacheln erzeugt werden sollen
     * @return Neue Dashboard-Rubrik mit den Kacheln
     */
    private DashboardSection createSection(Spezies spezies) {
        // Neue Rubrik im Dashboard erzeugen
        DashboardSection section = new DashboardSection();
        String cssClass = "";

        if (spezies != null) {
            section.setLabel(spezies.getName());
        } else {
            section.setLabel("Alle Spezies");
            cssClass = "overview";
        }

        // Eine Kachel für alle Tierarten in dieser Rubrik erzeugen
        DashboardTile tile = this.createTile(spezies, "Alle", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);
 
        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }

    /**
     * Hilfsmethode zum Erzeugen einer einzelnen Dashboard-Kachel. In dieser
     * Methode werden auch die in der Kachel angezeigte Anzahl sowie der Link,
     * auf den die Kachel zeigt, ermittelt.
     *
     * @param spezies
     * @param status
     * @param label
     * @param cssClass
     * @param icon
     * @return
     */
    private DashboardTile createTile(Spezies spezies, String label, String cssClass, String icon) {
        int amount = tierartBean.search(null, spezies).size();
        String href = "/app/tierarten/list/";

        if (spezies != null) {
            href = WebUtils.addQueryParameter(href, "search_spezies", "" + spezies.getId());
        }

        DashboardTile tile = new DashboardTile();
        tile.setLabel(label);
        tile.setCssClass(cssClass);
        tile.setHref(href);
        tile.setIcon(icon);
        tile.setAmount(amount);
        tile.setShowDecimals(false);
        return tile;
    }

}