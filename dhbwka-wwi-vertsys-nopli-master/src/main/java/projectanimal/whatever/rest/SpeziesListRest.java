package projectanimal.whatever.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import projectanimal.whatever.ejb.SpeziesBean;
import projectanimal.whatever.jpa.Spezies;

/**
 *
 * @author simon
 */
@Stateless
@Path("speziesliste")
public class SpeziesListRest {

    @EJB
    private SpeziesBean speziesBean;

    @GET
    public String doGet(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        // Anzuzeigende Spezies suchen
        System.out.println("");

        System.out.println(username + " " + password);

        List<Spezies> spezies = this.speziesBean.findAll();
        Gson gson = new Gson();
        String json = gson.toJson(spezies);
        return json;
    }

}
