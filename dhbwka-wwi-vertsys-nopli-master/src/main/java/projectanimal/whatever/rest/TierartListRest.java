/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package projectanimal.whatever.rest;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import projectanimal.whatever.ejb.SpeziesBean;
import projectanimal.whatever.ejb.TierartBean;
import projectanimal.whatever.jpa.Spezies;
import projectanimal.whatever.jpa.Tierart;

/**
 *
 * @author simon
 */
@Stateless
@Path("tierliste")
public class TierartListRest {
    @EJB
    private SpeziesBean speziesBean;
    
    @EJB
    private TierartBean tierartBean;

    @GET
    public String doGet(){
        // Anzuzeigende Aufgaben suchen
        Spezies spezies = null;
        
        List<Tierart> tierarten = this.tierartBean.search("asd", spezies);
        Gson gson = new Gson();
        String json = gson.toJson(tierarten);
        System.out.println(json);
        return json;
}
}
